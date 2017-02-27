package jug.lodz.workshops.starter.generics.answers

import jug.lodz.workshops.starter.generics.GenericsDemo
import org.scalatest.{MustMatchers, WordSpec}

class GenericsAnswersSpec extends WordSpec with MustMatchers{

  "EXERCISE1" should {
    "create pair [String,Int]" in {
      val pair:Pair[String,Int] = new Pair("aaa",2)

      pair.getA mustBe "aaa"
      pair.getB mustBe 2
    }

    "create pair [List[String], Set[Int]]" in {
      val pair= new Pair(List("aaa","bbb"),Set(1,2,3))

      pair.getA mustBe List("aaa","bbb")
      pair.getB mustBe Set(1,2,3)
    }
  }

  "EXERCISE2" should {
    "find path of String class" in {
      val path=Exercise2.getPath(classOf[String])

      path mustBe "java.lang.String"
    }

    "find path of scala List class" in {
      Exercise2.getPath(classOf[List[String]]) mustBe "scala.collection.immutable.List"
    }

    "find path of GenericsDemo class" in {
      Exercise2.getPath(GenericsDemo.getClass) mustBe "jug.lodz.workshops.starter.generics.GenericsDemo$"
    }
  }

  "EXERCISE3" should {
    "create invariant Some" in {
      val option:InvariantOption[Int]=InvariantSome(15)

      option.getOrElse(0) mustBe 15
    }

    "create invariant None" in {
      val option:InvariantOption[Int]=InvariantNone()

      option.getOrElse(0) mustBe 0
    }
  }

  "EXERCISE4 " should {
    "create covariant Some" in {
      val option:CovariantOption[AnyVal]=CovariantSome(15)

      option.isEmpty mustBe false
    }

    "create invariant None" in {
      val option:CovariantOption[Any]=CovariantNone

      option.isEmpty mustBe true
    }
  }

  "EXERCISE5 - super hard - use Upper bounds" should {
    "create covariant Some with value on contravariant position" in {
      val option:CovariantOption[AnyVal]=CovariantSome(15)

      option.getOrElse(0) mustBe 15
    }

    "create invariant None" in {
      val option:CovariantOption[Any]=CovariantNone

      option.getOrElse(0) mustBe 0
    }
  }

}

//EXERCISE1

class Pair[A,B](a:A,b:B){
  def getA:A = a
  def getB:B = b
}


//EXERCISE2

object Exercise2{
  def getPath[A](c: Class[A]):String = c.getCanonicalName
}

//EXERCISE3
sealed trait InvariantOption[A]{
  def getOrElse(other:A):A
}
case class InvariantSome[A](a:A) extends InvariantOption[A]{
  def getOrElse(other:A):A = a
}

case class InvariantNone[A]() extends InvariantOption[A]{
  override def getOrElse(other: A): A = other
}


//EXERCISE4

sealed trait CovariantOption[+A]{
  def isEmpty : Boolean
  //EXERCISE5 - super hard - use Upper bounds
  def getOrElse[B >: A](other:B):B
}

case object CovariantNone extends CovariantOption[Nothing]{

  override def isEmpty: Boolean = true
  //EXERCISE5
  override def getOrElse[A](other: A): A = other
}

case class CovariantSome[A](a:A) extends CovariantOption[A]{
  override def isEmpty: Boolean = false
  //EXERCISE5
  override def getOrElse[B >: A](other: B): B = a
}