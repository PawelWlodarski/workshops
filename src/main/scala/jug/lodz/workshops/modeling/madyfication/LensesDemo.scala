package jug.lodz.workshops.modeling.madyfication

import jug.lodz.workshops.WorkshopDisplayer

object LensesDemo extends WorkshopDisplayer{

  //SHOW also in WORKSHEET
  def main(args: Array[String]): Unit = {
    header("Complex data manipulation with Lenses")

    title("Domain : Purchase")
    val a=Address(new City("Lodz"),"Piotrkowska",7)
    val c=Customer("Roman", new PhoneNumber("123456"),a)

    val p1 = Product("tv",new Price(BigDecimal("200")))
    val p2 = Product("pc",new Price(BigDecimal("300")))


    val purchase = Purchase(
      customer = c,
      ps=Seq(p1,p2),
      delivery=Address(new City("Lodz"),"Piotrkowska",7),
      invoice=Address(new City("Lodz"),"Piotrkowska",7))


    title("Lack of composition")
    val newAddress=a.copy(city = new City("Zgierz"))
    val newCustomer=c.copy(address = c.address.copy(city = new City("Zgierz")))
    val newPurchase=purchase.copy(customer = purchase.customer.copy(address = purchase.customer.address.copy(city = new City("Zgierz"))))

    section("newAddress",newAddress)
    section("newCustomer",newCustomer)
    section("newPurchase",newPurchase)

    title("Lenses")

    //PLens
    //Private state

    //e1 - university
    //e2 - security domain
    //e3 - smart constructors
    //e4 - custom lens implementation
    //e4b - compose
  }


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

