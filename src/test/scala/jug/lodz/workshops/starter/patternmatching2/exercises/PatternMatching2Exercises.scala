package jug.lodz.workshops.starter.patternmatching2.exercises

import org.scalatest.{MustMatchers, WordSpec}

class PatternMatching2Exercises extends WordSpec with MustMatchers {

  "EXERCISE1 " should {
    "extract email" in {

      val correctMail = "correct@gmail.com"
      val incorrectMail = "wrong"

      val (name, domain) = correctMail match {
        case EmailExtractor(n, d) => (n, d)
      }

      val result2 = incorrectMail match {
        case EmailExtractor(name2, _) => name2
        case _ => "wrongEmail"
      }

      name mustBe "correct"
      domain mustBe "gmail.com"
      result2 mustBe "wrongEmail"
    }
  }

  "EXERCISE2" should {
    "extract sum of all events from provate state" in {
      val state = new StateHistory()

      val sum0 = state match {
        case StateHistory(s) => s
        case _ => 0
      }

      sum0 mustBe 0  //check

      state.addEvent(3)
      state.addEvent(3)
      state.addEvent(2)
      state.addEvent(1)

      val sum1 = state match {
        case StateHistory(s) => s
      }

      sum1 mustBe 9  //check

      state.addEvent(10)

      val sum2 = state match {
        case StateHistory(s) => s
      }
      sum2 mustBe 19 //check
    }
  }

  "EXERCISE3" should {
    "implement pattern matching on non empty basket" in {
      val b=new Basket()

      b.purchase(Product("tv",200))
      b.purchase(Product("bottle",30))

      val result=b match {
        case Basket() => Basket.process(b)
        case _ =>  "basket empty"
      }

      result mustBe "processed 2 products"
    }
  }
}

//EXERCISE1
object EmailExtractor {
  def unapply(mail: String): Option[(String, String)] = ???
}

//EXERCISE2

class StateHistory {
  private var history: Vector[Int] = Vector()

  def addEvent(e: Int): Unit = history = history.+:(e)

}

object StateHistory {
  def unapply(state: StateHistory): Option[Int] = ???
}

//Exercise3
case class Product(name:String,price:Int)

class Basket{
  private var purchased:Seq[Product]=Seq()

  def purchase(p:Product) = purchased = purchased :+ p
}

object Basket{
  def unapply(b:Basket) : Boolean = ???
  def process(b:Basket) : String =  ???
}