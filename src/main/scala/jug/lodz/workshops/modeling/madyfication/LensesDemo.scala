package jug.lodz.workshops.modeling.madyfication

import jug.lodz.workshops.WorkshopDisplayer
import monocle.macros.GenLens
import monocle.{Iso, Lens}

object LensesDemo extends WorkshopDisplayer{

  //SHOW also in WORKSHEET
  def main(args: Array[String]): Unit = {
    header("Complex data manipulation with Lenses")

    title("Domain : Purchase")
    //Explain domain on a picture
    val a=Address(new City("Lodz"),"Piotrkowska",7)
    val c=Customer("Roman", new PhoneNumber("123456"),a)

    val p1 = Product("tv",new Price(BigDecimal("200")))
    val p2 = Product("pc",new Price(BigDecimal("300")))


    //MAIN AGGREGATE
    val purchase = Purchase(
      customer = c,
      ps=Seq(p1,p2),
      delivery=Address(new City("Lodz"),"Piotrkowska",7),
      invoice=Address(new City("Lodz"),"Piotrkowska",7))


    title("Lack of composition when using standard case clas copy")
    //WE WANT TO CHANGE CUSTOMERS CITY
    val newAddress=a.copy(city = new City("Zgierz"))
    val newCustomer=c.copy(address = c.address.copy(city = new City("Zgierz")))
    val newPurchase=purchase.copy(customer = purchase.customer.copy(address = purchase.customer.address.copy(city = new City("Zgierz"))))

    section("newAddress",newAddress)
    section("newCustomer",newCustomer)
    section("newPurchase",newPurchase)

    title("Lenses")
    //Types In Lens : External Type , Internal Type
    //First fucntion is a getter
    //Second function is a setter internal=>external=>change_external
    val purchaseCustomerLens=Lens[Purchase,Customer](_.customer)(c => p => p.copy(customer=c))

    //and now similarly customer address
    val customerAddressLens=Lens[Customer,Address](_.address)(a => c => c.copy(address = a))

    //looks very similar and mundane - use macro!
    val addressCityLens=GenLens[Address](_.city)

    //invoking single lenses - explain set
    section("getCustomer",purchaseCustomerLens.get(purchase))
    section("setCustomer",purchaseCustomerLens.set(newCustomer)(purchase)) //notice that city changed

    section("getAddress",customerAddressLens.get(c))
    section("setAddress",customerAddressLens.set(newAddress)(c))

    section("getCity",addressCityLens.get(a))
    section("setCity",addressCityLens.set(new City("LensCity"))(a))

    title("Lenses Composition")
    //!!!!COMPOSITION!!!!
    val cityInPurchase=purchaseCustomerLens.composeLens(customerAddressLens).composeLens(addressCityLens)

    section("getCityDirectly",cityInPurchase.get(purchase))
    section("setCityDirectly",cityInPurchase.set(new City("DirectCity"))(purchase))


    section("modifyCity",cityInPurchase.modify(old=>new City(old.name+":modified"))(purchase))

    //Iso
    val cityJsonIso = Iso[City,String](c => s"{city:${c.name}}"){json =>
      val cityName=json.substring(6,json.length)
      new City(cityName)
    }

    section("json from city",cityJsonIso.get(new City("Lodz")))
    section("city from json",cityJsonIso.reverseGet("{city:zakopane}"))


    val isoComposed=cityInPurchase.composeIso(cityJsonIso)

    section("iso purchase set",isoComposed.set("{city:zakopane}")(purchase))

    title("Traversable")
    case class Class(students:List[String])
    case class School(classes:List[Class])

    val c1=Class(List("student1","student2"))
    val c2=Class(List("student3","student4"))

    val school=School(List(c1,c2))


    val studentsLens=GenLens[Class](_.students)
    val classesLens=GenLens[School](_.classes)

//    val eachStudent=classesLens.composeTraversal(Each.each).composeLens(studentsLens).composeTraversal(Each.each)
    //or
    import monocle.function.Each._
    val eachStudent=classesLens composeTraversal each composeLens studentsLens composeTraversal each

    section("each student",eachStudent.getAll(school))


  }

  //Remind what AnyVal means
  class City(val name:String) extends AnyVal{
    override def toString: String = s"City($name)"
  }
  class PhoneNumber(val number:String) extends AnyVal{
    override def toString: String = s"PhoneNumber($number)"
  }
  class Price(val value:BigDecimal) extends AnyVal{
    override def toString: String = s"Price($value)"
  }
  case class Address(city:City,street:String,number:Int)
  case class Customer(name:String,phone:PhoneNumber,address: Address)
  case class Product(name:String,price:Price)
  case class Purchase(customer:Customer,ps:Seq[Product],delivery:Address,invoice:Address)
}

