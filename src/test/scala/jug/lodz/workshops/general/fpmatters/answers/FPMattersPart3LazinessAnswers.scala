package jug.lodz.workshops.general.fpmatters.answers

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by pawel on 25.09.16.
  */
class FPMattersPart3LazinessAnswers extends FunSuite with Matchers {

  test("sum of even numbers") {
    def sumNFirstEvenNumbers(n: Int): Int = Stream.from(1).filter(_ % 2 == 0).take(n).foldRight(0)(_ + _)

    sumNFirstEvenNumbers(5) shouldBe 30
    sumNFirstEvenNumbers(6) shouldBe 42
  }

  test("fizz buzz") {
    def fizzBuzz: Stream[Any] =
      Stream.from(1).map {
        case i if (i % 3 == 0) && (i % 5 == 0) => "Fizz Buzz"
        case i if (i % 3 == 0) => "Fizz"
        case i if (i % 5 == 0) => "Buzz"
        case i => i
      }

    fizzBuzz.take(26).toList shouldBe List(
      1, 2, "Fizz", 4, "Buzz", "Fizz", 7, 8, "Fizz", "Buzz",
      11, "Fizz", 13, 14, "Fizz Buzz", 16, 17, "Fizz", 19, "Buzz", "Fizz", 22, 23, "Fizz", "Buzz", 26
    )
  }


  test("isPrime") {
    def isPrime(n: Int): Boolean = Stream.range(2, Math.sqrt(n).ceil.toInt).forall(n % _ != 0)

    List(3, 8, 11, 17, 20).map(isPrime) shouldBe List(true, false, true, true, false)
  }


}
