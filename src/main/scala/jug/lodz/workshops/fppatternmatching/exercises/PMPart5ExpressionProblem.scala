package jug.lodz.workshops.fppatternmatching.exercises

import jug.lodz.workshops.Workshops
import jug.lodz.workshops.Workshops.check

/**
  * Created by pawel on 24.04.16.
  */
object PMPart5ExpressionProblem {

  //******** 1 ******** OOP library where operations are part of objects
  object ObjectLibrary {

      trait Shape {
        def area(): Double
      }

      class Circle(r: Int) extends Shape {
        override def area(): Double = Math.PI * r * r
      }

  }

  //******** 2 ******** Data library where operations are outside objects
  object DataLibrary {

    trait Shape

    case class Circle(r: Int) extends Shape

    def area(s: Shape): Double = s match {
      case Circle(r) => Math.PI * r * r
    }
  }

  //******** 3 ******** ADT library where subtypes are limited and operations may be in abstract type or outside all types
  object ADTLibrary {

    sealed trait Shape {
      def area: Double = this match {
        case Circle(r) => Math.PI * r * r
        case Square(a) => a * a
      }
    }

    case class Circle(r: Int) extends Shape

    case class Square(a: Int) extends Shape

  }


  object Demonstration {

    /**
      *  x  How we can use OOP library
      *  x  List of shapes is declared as really list of shapes
      *  x  how to add a new type
      *  x how to add a new operation
      */

    def demo1() = {
      println("* EXAMPLE1 : OBJECTS")
      import ObjectLibrary._

      class Square(a: Int) extends Shape {
        override def area(): Double = a * a
      }

      val shapes: List[Shape] = List(new Circle(3), new Square(2))

      println("* --> SHAPES AREAS")
      shapes.map(_.area()).foreach(println)

      println("\n* --> NOW HOW TO ADD OTHER METHODS? (circumference)")
    }

    /**
      *  x  How we can use Data library
      *  x  We can add a polymorphic function
      *  x  how to add a new type
      *  x how to add a new operation
      */
    def demo2() = {
      println("\n* EXAMPLE2 : DATA MATCHING")
      import DataLibrary._

      def circumference(s: Shape): Double = s match {
        case Circle(r) => 2 * Math.PI * r
      }

      val circle = Circle(3)
      println("* --> CIRCLE FORMULAS")
      println(s"* --> AREA  : ${area(circle)}")
      println(s"* --> CIRCUMFERENCE : ${circumference(circle)}")

      println("\n* --> NOW HOW TO ADD OTHER DATA TYPES? (square)")
    }

    /**
      *  x  How we can use ADT library
      *  x  adding function is simple
      *  x  adding function inside type with implicits
      *  x  how to add a new type (by definition you don't want to do it)
      *  x how to add a new operation
      */
    def demo3() = {
      println("\n* EXAMPLE3 : ADTs")
      import ADTLibrary._

      def circumference(s: Shape): Double = s match {
        case Circle(r) => 2 * Math.PI * r
        case Square(a) => 4 * a
      }

      implicit class ShapeUpgraded(s: Shape) {
        def implicitCircumference: Double = s match {
          case Circle(r) => 2 * Math.PI * r
          case Square(a) => 4 * a
        }
      }

      val square = Square(2)
      println(s"* --> SQUARE CIRCUMFERENCE ${circumference(square)}")
      println(s"* --> SQUARE CIRCUMFERENCE IMPLICIT : ${square.implicitCircumference}")


    }


  }

  /**
    * FINAL BOSS
    *
    *
    *
    * |_O   / \  O_\
    *   |'-/   \-'\
    *   |\       / |
    *  /  |      |  \
    *
    *  RIGHT BIASED EITHER  : ENERGY : [===========]
    *
    *
    */
  object ExerciseLevelBoss {

    sealed trait RightBiasedEither[+A, +B] {
      def map[C](f: B => C): RightBiasedEither[A, C] = ???

      def flatMap[AA >: A, C](f: B => RightBiasedEither[AA, C]): RightBiasedEither[AA, C] = ???
    }

    case class RBLeft[A](a: A) extends RightBiasedEither[A, Nothing]

    case class RBRight[B](b: B) extends RightBiasedEither[Nothing, B]

    def exercise() = {
      val result1 = for {
        v1 <- RBRight(1)
        v2 <- RBRight(2)
        v3 <- RBRight(3)
      } yield v1 + v2 + v3

      val result2 = for {
        v1 <- RBRight(1)
        v2 <- RBLeft("There is an error")
        v3 <- RBRight(3)
      } yield v1 + v2 + v3

      check("RESULT ONE -- ALL RIGHT")(result1 , RBRight(6))
      check("RESULT TWO -- ONE LEFT")(result2 , RBLeft("There is an error"))
    }
  }

  def main(args: Array[String]) {
    println("***[DEMONSTRATION]***")
    Demonstration.demo1()
    Demonstration.demo2()
    Demonstration.demo3()

    //    println("[------EXERCISES------]")
    //    println("\n[------EXERCISE LEVEL MONAD------]")
    //
    //    ExerciseLevelBoss.exercise()
  }

}
