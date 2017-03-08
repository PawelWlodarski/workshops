package jug.lodz.workshops.starter.patternmatching2.answers

import org.scalatest.{MustMatchers, WordSpec}

class PatternMatching2Answers extends WordSpec with MustMatchers {

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
}

//EXERCISE1
object EmailExtractor {
  def unapply(mail: String): Option[(String, String)] = {
    val parts = mail.split("@")
    if (parts.length == 2) Some(parts(0), parts(1)) else None
  }
}

//EXERCISE2

class StateHistory {
  private var history: Vector[Int] = Vector()

  def addEvent(e: Int): Unit = history = history.+:(e)

}

object StateHistory {
  def unapply(state: StateHistory): Option[Int] =
    if (state.history.isEmpty) None
    else Some(state.history.reduce((a, b) => a + b))
}