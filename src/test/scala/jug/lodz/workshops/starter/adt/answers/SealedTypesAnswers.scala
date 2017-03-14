package jug.lodz.workshops.starter.adt.answers

import org.scalatest.{MustMatchers, WordSpecLike}

class SealedTypesAnswers extends WordSpecLike with MustMatchers{

  "EXERCISE1" should {
    "create selaed trait for http statuses" in {
      val first=Ok
      val second=Redirect("gugl.com")
      val third=NotFound

      first.status mustBe 200
      second.status mustBe 301
      third.status mustBe 404

      HttpResult.getRedirection(second) mustBe "gugl.com"
      HttpResult.getRedirection(first) mustBe ""
    }
  }

  "EXERCISE2" should {
    "Use ADT as natural number representation" in {
      val two = Next(Next(Zero))
      val four = Next(Next(Next(Next(Zero))))

      Number.intValue(two) mustBe 2
      Number.intValue(four) mustBe 4
    }
  }

  "EXERCISE3" should {
    "simulate custom Try with ADTs" in {
      val success=CustomSuccess(5)
      val failure:CustomTry[Int]=CustomFailure(new RuntimeException("just for tetsing"))

      success.filter(_ > 3) mustBe CustomSuccess(5)
      success.filter(_ > 10) mustBe a[CustomFailure]
      failure.filter(_ > 1)  mustBe a[CustomFailure]


    }
  }

}

//EXERCISE1
sealed trait HttpResult{
  def status:Int = this match{
    case Ok => 200
    case Redirect(_) => 301
    case NotFound => 404
  }
}
case object Ok extends  HttpResult

final case class Redirect(where:String) extends HttpResult

case object NotFound extends HttpResult

object HttpResult{
  def getRedirection(result:HttpResult) : String = result match {
    case Redirect(where) =>  where
    case _ => ""
  }
}

//EXERCISE2
sealed trait Number
object Zero extends Number
case class Next(prev:Number) extends Number

object Number {
  def intValue(n:Number) : Int = n match {
    case Zero => 0
    case Next(n) => 1+ intValue(n)
  }
}



//EXERCISE3
sealed abstract class CustomTry[+A]{
  def filter(predicate:A=>Boolean) : CustomTry[A] = this match {
    case CustomSuccess(a) if predicate(a) => this
    case CustomSuccess(_) => CustomFailure(new NoSuchElementException("predicated failed for value : $a"))
    case failure => failure
  }
}
case class CustomSuccess[A](a:A) extends CustomTry[A]
case class CustomFailure(e:Exception) extends CustomTry[Nothing]