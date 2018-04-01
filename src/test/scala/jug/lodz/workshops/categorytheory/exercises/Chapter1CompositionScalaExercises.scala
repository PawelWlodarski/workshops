package jug.lodz.workshops.categorytheory.exercises

import org.scalatest.{FunSuite, MustMatchers}

class Chapter1CompositionScalaExercises extends FunSuite with MustMatchers{

  test("identity function"){
    identity(1) mustBe 1
    identity("aaa") mustBe "aaa"
    identity(true) mustBe true
  }

  def identity[A]: A=>A = ???

  test("function composition"){
    case class User(name:String)

    val f:User=>String=u => u.name
    val g:String => Int = s=>s.length

    val composed: (User) => Int =compose(f,g)

    composed(User("Roman")) mustBe 5
    composed(User("Agnieszka")) mustBe 9

  }

  //DON'T use andThen or compose from scala Function type!!!
  def compose[A,B,C](f:A=>B, g:B=>C): A=>C = ???

}
