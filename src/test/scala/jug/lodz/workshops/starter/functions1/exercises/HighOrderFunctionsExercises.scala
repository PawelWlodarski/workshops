package jug.lodz.workshops.starter.functions1.exercises

import jug.lodz.workshops.starter.functions1.exercises.Exercise3.{DEBUG, ERROR, INFO}
import org.scalatest.{MustMatchers, WordSpec}

class HighOrderFunctionsExercises extends WordSpec with MustMatchers {

  //implement methods which are "factories" for functions
  "EXERCISE1" should {
    "return functions from methods" in {
      def createAddPrefix(prefix: String): String => String = ???

      //comparator checks if passed parameter is greater than constant
      def createComparatorToConstant(constant: Int): Int => Boolean = ???


      val f1 = createAddPrefix("prefix_")
      val f2 = createComparatorToConstant(7)


      f1("text") mustBe "prefix_text"
      f1("someOtherText") mustBe "prefix_someOtherText"
      f2(1) mustBe false
      f2(8) mustBe true

    }
  }


  "EXERCISE2" should {
    "custom andThen without using .andThen method on function!" in {
      //do without using andThen from function object!!!
      def customAndThen(f1:Int=>String,f2:String => Exercise2Record) : Int => Exercise2Record =
          ???  //<- exercise

      //generate n a letters
      val f1:Int=>String= n => (1 to n).map(_=>"a").mkString
      val f2:String => Exercise2Record = Exercise2Record.apply

      val composed=customAndThen(f1,f2)

      composed(7) mustBe Exercise2Record("aaaaaaa")
      composed(3) mustBe Exercise2Record("aaa")

    }
  }

  //Complete method in Exercise3 object
  "EXERCISE3" should {
    "create function-logger" in {
      val logDebug=Exercise3.createLogger(DEBUG)
      val logInfo=Exercise3.createLogger(INFO)
      val logError=Exercise3.createLogger(ERROR)

      logDebug("text") mustBe "[DEBUG] text"
      logInfo("text") mustBe "[INFO] text"
      logError("text") mustBe "[ERROR] text"
    }
  }

  //Complete method in Exercise4 object
  // comment about logic "density"
  "EXERCISE4" should {
    "created curried and ucurried" in {
      val addInts: (Int,Int) => Int = (i1,i2) => i1+i2
      val intsCurried: Int => Int => Int = Exercise4.curry(addInts)
      intsCurried(2)(3) mustBe 5
      Exercise4.unCurry(intsCurried)(2,3)  mustBe 5

      val repeatString : (String,Long) => String = (s,n) => (1L to n).map(_ => s).mkString
      val repeatStringCurried: String => Long => String = Exercise4.curryGeneric(repeatString)
      val repeatTest: Long => String =repeatStringCurried("TEST")
      repeatTest(1) mustBe "TEST"
      repeatTest(2) mustBe "TESTTEST"
      repeatTest(4) mustBe "TESTTESTTESTTEST"

      Exercise4.unCurryGeneric(repeatStringCurried)("TEST",3L) mustBe "TESTTESTTEST"
    }
  }

}

case class Exercise2Record(value:String)

object Exercise3{
  sealed trait LogLevel
  case object DEBUG extends LogLevel
  case object INFO extends LogLevel
  case object ERROR extends LogLevel

  def createLogger(level:LogLevel) : String => String = ???
}

object Exercise4 {
  def curry(f:(Int,Int)=>Int) : Int => Int => Int = ???
  def unCurry(f:Int => Int=>Int) : (Int,Int) => Int = ???

  def curryGeneric[A,B,C](f:(A,B)=>C) : A => B => C = ???
  def unCurryGeneric[A,B,C](f:A => B => C) : (A , B) => C = ???
}