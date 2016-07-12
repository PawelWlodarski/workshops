package jug.lodz.workshops.old.fp1.exercises

/**
  * Created by pawel on 09.02.16.
  */
object ScalaFp1PracticalExample {

  import MathLib._
  import DomainLib._

  //Our custom domain lib
  object DomainLib{
    case class Product(name:String,price: BigDecimal)
    case class Purchase(id:Int,purchasedProducts:List[Product])
  }


  //Database emulation
  private val products:Map[String,Product]=Map(
    "tv"->Product("tv",BigDecimal("3000")),
    "keyboard"->Product("keyboard",BigDecimal("120")),
    "mouse"->Product("mouse",BigDecimal("70")),
    "headphones"->Product("head phones",BigDecimal("200"))

  )

  private val database:Map[Int,Purchase] =Map(
      1 -> Purchase(1,List(products("tv"),products("headphones"))),
      2 -> Purchase(2,List(products("keyboard"),products("headphones"),products("mouse")))
  )

  //Some very generic Math lib
  object MathLib {
    //lab   Function[Seq[BigDecimal],BigDecimal]
    val genericMathFunction:Seq[BigDecimal]=>BigDecimal = ???
  }

  //lab Function[Purchase, Seq[BigDecimal]]
  val domainFunction: Purchase => Seq[BigDecimal] = ???

  //lab
  def findPurchase(id:Int):Option[Purchase] = ???


  def main(args: Array[String]) {
    //Composition of two completely independent pure function
    val pureDomainFunction: (Purchase) => BigDecimal =domainFunction andThen genericMathFunction

    //CORRECT CASE SCENARIO - THERE IS A PURCHASE WITH ID=1 IN THE DATABASE
    findPurchase(1).map(pureDomainFunction).foreach(println)


    // FAILURE CASE SCENARIO - THERE IS NO PURCHASE WITH ID=3 IN THE DATABASE
    val purchase3Price: BigDecimal =findPurchase(3).map(pureDomainFunction)
      .getOrElse(throw new RuntimeException("there is no purchase with id 3"))

    println(s"somehow there is a purchase with id 3 : $purchase3Price")
  }

}


