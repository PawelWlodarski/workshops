package jug.lodz.workshops.starter.patternmatching1.exercises

import org.scalatest.{MustMatchers, WordSpec}

class PatternMatching1Exercises extends WordSpec with MustMatchers {

  "Exercise 1" should {
    "perform pattern matching on simple case class" in {
      import Exercise1._
      Exercise1.exercise(Multiply(2, 3)) mustBe 6
      Exercise1.exercise(Multiply(8, 3)) mustBe 24
      Exercise1.exercise(Add(2, 3)) mustBe 5
      Exercise1.exercise(Add(8, 3)) mustBe 11
    }
  }

  "Exercise 2" should {
    "check type of passed expression" in {
      //short string -> length less than 5

      Exercise2.exercise(1) mustBe "positive int"
      Exercise2.exercise(6) mustBe "positive int"
      Exercise2.exercise(0) mustBe "zero"
      Exercise2.exercise(-1) mustBe "negative int"
      Exercise2.exercise(-6) mustBe "negative int"
      Exercise2.exercise("aaa") mustBe "short string"
      Exercise2.exercise("aaaaaaaaaaaaaa") mustBe "long string"
      Exercise2.exercise("aaaaaaaaaaaaaabbbb") mustBe "long string"
      Exercise2.exercise(BigDecimal(1)) mustBe "big decimal"
      Exercise2.exercise(BigDecimal(9)) mustBe "big decimal"
      Exercise2.exercise(BigDecimal(-100)) mustBe "big decimal"
    }
  }

  "Exercise 3" should {
    "pattern match on complex expression" in {
      import Exercise3._
      exercise(Sum(Variable(2), Multiply(Variable(3), Variable(4)))) mustBe 14 //(2 + 3*4) == 14
      exercise(
        Multiply(
          Sum(Variable(2), Sum(Variable(2), Variable(2))),
          Multiply(Sum(Variable(1), Variable(1)), Variable(2))
        )
      ) mustBe 24 // (2+(2+2)) * ((1+1)*2) = 24

    }
  }

}

//EXERCISE1

object Exercise1 {

  case class Multiply(a: Int, b: Int)

  case class Add(a: Int, b: Int)


  def exercise(expression: Any): Int = ???
}

//EXERCISE2

object Exercise2 {

  def exercise(expression: Any): String = ???
}

//EXERCISE3

object Exercise3 {

  sealed trait Expression

  case class Variable(value: Int) extends Expression

  case class Sum(e1: Expression, e2: Expression) extends Expression

  case class Multiply(e1: Expression, e2: Expression) extends Expression

  def exercise(expression: Expression): Int = ???
}