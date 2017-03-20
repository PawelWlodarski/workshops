package jug.lodz.workshops.starter.functions1.exercises

import org.scalatest.{MustMatchers, WordSpec}

class Functions1Exercises extends WordSpec with MustMatchers{

  "EXERCISE1" should {
    "create simple functions" in {
      val f1:Int=>Int = ???  //multiply by 16
      val f2:Int=>Int = ??? // add one

      val toHexString: Int => String = ???  //convert Method Integer.toHexString from java std lib


      val calculateToHex: Int => String =f1 andThen f2 andThen toHexString

      calculateToHex(10) mustBe  "a1"
      calculateToHex(11) mustBe  "b1"
      calculateToHex(12) mustBe  "c1"

    }
  }

  // complete Only methods object at the bottom of the file
  "EXERCISE2" should {
    "convert methods into functions" in {
      val trimF: String => String = OnlyMethods.trim _
      val removeDots: String => String = OnlyMethods.removeDots _
      val toUpper: String => String = OnlyMethods.toUpper _

      val function=toUpper compose (trimF andThen removeDots)

      function("  o.n.e tw.o    ") mustBe "ONE TWO"
    }
  }

  //complete partial functions at the bottom of the file
  "EXERCISE3" should {
    "Find email in big purchases" in {
      import PartialShop._
      val shopFlow= bigPurchases orElse defaultHandler

      val p1=Purchase(1,"tv",BigDecimal(100),Customer("customer1",Email("c1@google.com")))
      val p2=Purchase(1,"fork",BigDecimal(5),Customer("customer2",Email("c2@google.com")))

      shopFlow(p1) mustBe Email("c1@google.com")
      shopFlow(p2) mustBe DEV_NULL_EMAIL
    }
  }

}

//EXERCISE2
object OnlyMethods{
  def trim(s:String): String = ???
  def removeDots(s:String): String = ???
  def toUpper(s:String): String = ???
}

//EXERCISE3
case class Email(address:String)
case class Customer(name:String,email:Email)
case class Purchase(id:Int,name:String,price:BigDecimal,c:Customer)

object PartialShop{
  val DEV_NULL_EMAIL=Email("")

  //extract email if price is larger than 50
  val bigPurchases:PartialFunction[Purchase,Email] = ???

  //this is default handler which should return DEV_NULL_EMAIL
  val defaultHandler:PartialFunction[Purchase,Email] = ???
}
