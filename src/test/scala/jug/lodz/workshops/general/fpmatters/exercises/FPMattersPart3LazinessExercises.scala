package jug.lodz.workshops.general.fpmatters.exercises

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by pawel on 25.09.16.
  */
class FPMattersPart3LazinessExercises extends FunSuite with Matchers {

  test("sum of even numbers") {
    def sumNFirstEvenNumbers(n: Int): Int = ???

    sumNFirstEvenNumbers(5) shouldBe 30
    sumNFirstEvenNumbers(6) shouldBe 42
  }


  //if divided by 3 -> Fizz, if divided by 5 -> Buzz , if divided by 3 and 5 -> Fizz Buzz
  test("fizz buzz") {
    def fizzBuzz: Stream[Any] = ???

    fizzBuzz.take(26).toList shouldBe List(
      1, 2, "Fizz", 4, "Buzz", "Fizz", 7, 8, "Fizz", "Buzz",
      11, "Fizz", 13, 14, "Fizz Buzz", 16, 17, "Fizz", 19, "Buzz", "Fizz", 22, 23, "Fizz", "Buzz", 26
    )
  }


  test("isPrime") {
    //use Stream.range and Stream.forAll
    def isPrime(n: Int): Boolean = ???

    List(3, 8, 11, 17, 20).map(isPrime) shouldBe List(true, false, true, true, false)
  }


}
