package jug.lodz.workshops.general.fpmatters.exercises

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by pawel on 18.09.16.
  */
class FPMattersPart1CompositionExercises extends FunSuite with Matchers {


  test("basic compose - arrange function in proper order") {
    val f1: Int => Int = i => i + 10
    val f2: String => Int = input => input.toInt
    val f3: Int => Double = i => i / 2.0

    val composed: String => Double = ???

    composed("21") shouldBe 15.5
    composed("30") shouldBe 20.0
  }

  //you need to manipulate f2 so that it can be composed with f1
  test("compose curried") {
    val f1: String => Int = s => s.toInt
    val f2: (Int, Int) => Int = (x, y) => x * y

    val composed: String => Int => Int = ???

    composed("10")(3) shouldBe 30

  }

  test("sum and product with foldr") {
    val sum: List[Int] => Int = ???
    val product: List[Int] => Int = ???

    sum(List(1, 2, 3, 4, 5)) shouldBe (15)
    product(List(1, 2, 3, 4, 5)) shouldBe (120)
  }

  // To append at the end of list use :+ operator list :+ element
  test("reverse with foldr") {
    val reverse: List[Int] => List[Int] = input =>
      input.foldRight(List.empty[Int])((elem, acc) => acc :+ elem)

    reverse(List(1, 2, 3, 4, 5)) shouldBe List(5, 4, 3, 2, 1)
  }
}
