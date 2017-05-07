package jug.lodz.workshops.starter.adt.exercises

import org.scalatest.{MustMatchers, WordSpecLike}

class SealedTypesExercises extends WordSpecLike with MustMatchers{
  //complete code at the bottom of the class and run test
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
  //complete code at the bottom of the class and run test
  "EXERCISE2" should {
    "Use ADT as natural number representation" in {
      val two = Next(Next(Zero))
      val four = Next(Next(Next(Next(Zero))))

      Number.intValue(two) mustBe 2
      Number.intValue(four) mustBe 4
    }
  }

  //uncomment and complete code at the bottom of this file then run test
  //You need to add subtypes to custom try
//  "EXERCISE3" should {
//    "simulate custom Try with ADTs" in {
//      val success=CustomSuccess(5)
//      val failure:CustomTry[Int]=CustomFailure(new RuntimeException("just for tetsing"))
//
//      success.filter(_ > 3) mustBe CustomSuccess(5)
//      success.filter(_ > 10) mustBe a[CustomFailure]
//      failure.filter(_ > 1)  mustBe a[CustomFailure]
//
//
//    }
//  }

}

//EXERCISE1
sealed trait HttpResult{
  def status:Int = ???   ///complete and run test
}
case object Ok extends  HttpResult

final case class Redirect(where:String) extends HttpResult

case object NotFound extends HttpResult

object HttpResult{
  def getRedirection(result:HttpResult) : String = ??? //complete and run test
}

//EXERCISE2
sealed trait Number
object Zero extends Number
case class Next(prev:Number) extends Number

object Number {
  def intValue(n:Number) : Int = ???  //complete and run test
}



//EXERCISE3
sealed abstract class CustomTry[+A]{
  def filter(predicate:A=>Boolean) : CustomTry[A] = ???
}