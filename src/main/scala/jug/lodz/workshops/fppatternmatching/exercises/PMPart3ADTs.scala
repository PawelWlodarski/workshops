package jug.lodz.workshops.fppatternmatching.exercises

import jug.lodz.workshops.Workshops
import jug.lodz.workshops.Workshops.check

import scala.util.{Failure, Success, Try}

/**
  * Created by pawel on 23.04.16.
  */
object PMPart3ADTs {

  def main(args: Array[String]) {
    Demonstration.demo()

    //    println("[------EXERCISES------]")
    //    println("\n[------EXERCISE LEVEL 1------]")
    //    ExerciseLevel1.exercise11()
    //
    //    println("\n[------EXERCISE LEVEL 2------]")
    //    ExerciseLevel2.exercise21()
    //    ExerciseLevel2.exercise22()
    //
    //    println("\n[------EXERCISE LEVEL 3------]")
    //    ExerciseLevel3.exercise31()
    //
    //    println("\n[------EXERCISE LEVEL 4 :  BOSS------]")
    //    ExerciseLevel4.exercise41()
  }

  sealed trait BOOLEAN

  case class TRUE() extends BOOLEAN

  case class FALSE() extends BOOLEAN

  object Demonstration {
    def demo() = {
      println("[DEMONSTRATION]")
      println("\n * WHAT IS a ADT? ABSTRACT CONCEPT WITH LIMITED NUMBER OF POSSIBLE VALUES")
      println(" -- BOOLEAN : TRUE or FALSE")
      println(" -- DAY : Monday or Tuesday or Wednesday or Thursday or Friday or Saturday or Sunday  ")
      println(" -- REQUEST METHOD : GET or POST or PUT or DELETE")
      println(" -- USER ROLE : ADMIN or CUSTOMER or CONSULTANT")

      println("\n * A VALUE CAN BE PARAMETRIZED ITSELF (a new perspective on a constructor?)")
      println(" -- OPTION : SOME(value) or NONE")
      println(" -- TRY : SUCCESS(value) or FAILURE(error)")
      println(" -- SHAPE : CIRCLE(x,y,radius) or RECTANGLE(x1,y1,x2,y2)")

      println("\n * A VALUE CAN BE COMPLEX AND RECURSIVE")
      println(" -- EXPRESSION : NUMBER(value) or ADD(EXPRESSION,EXPRESSION) or MULTIPLY(EXPRESSION,EXPRESSION)")

      println("\n\n *HOW TO IMPLEMENT ADT")
      println("\n * 1) WHAT IS TRAIT")

      //show how trait is working
      trait SomeTrait {
        def someMethod: String
      }

      class SomeClass extends SomeTrait {
        override def someMethod: String = "someMethod concrete implementation"
      }

//      val traitImplementation: SomeTrait = new SomeClass()
//      println(s"  --> executing overriden method : '${traitImplementation.someMethod}'")

      println("\n * 2) HOW TO LIMIT NUMBER OF POSSIBLE VALUES? 'SEALED' word")
      //already defined in the main object scope
      //      sealed trait BOOLEAN
      //      case class TRUE() extends BOOLEAN
      //      case class FALSE() extends BOOLEAN

      println("\n * 3) Now it's easy to detect if your functions are TOTAL or PARTIAL")

      object BooleanAlgebra {
//        def negateTotalFunction(b: BOOLEAN): BOOLEAN = b match {
//          case TRUE() => FALSE()
//          case FALSE() => TRUE()
//        }

        //uncomment
        //        def negatePartialFunction(b:BOOLEAN):BOOLEAN=b match {
        //          case TRUE() => FALSE()
        //        }
      }

//      println("  --> negate true : " + BooleanAlgebra.negateTotalFunction(TRUE()))
//      println("  --> negate false : " + BooleanAlgebra.negateTotalFunction(FALSE()))

    }

  }

  /**
  * x implement boolean algebra
   */
  object ExerciseLevel1 {
    def exercise11() = {
      def and(a: BOOLEAN, b: BOOLEAN): BOOLEAN = (a, b) match {
        case (b1,b2) => ???   //handle all 4 possibilities
      }

      def or(a: BOOLEAN, b: BOOLEAN): BOOLEAN = (a, b) match {
        case _ => ???   //handle all 4 possibilities
      }
      //Check without assertions
      println(s" *EXERCISE11 t&&t: ${and(TRUE(), TRUE())}")
      println(s" *EXERCISE11 t&&f: ${and(TRUE(), FALSE())}")
      println(s" *EXERCISE11 f&&t: ${and(FALSE(), TRUE())}")
      println(s" *EXERCISE11 f&&f: ${and(FALSE(), FALSE())}")

      println(s" *EXERCISE11 t||t: ${or(TRUE(), TRUE())}")
      println(s" *EXERCISE11 t||f: ${or(TRUE(), FALSE())}")
      println(s" *EXERCISE11 f||t: ${or(FALSE(), TRUE())}")
      println(s" *EXERCISE11 f||f: ${or(FALSE(), FALSE())}")

    }

  }

  object ExerciseLevel2 {

    //Match on Try - scala.util.Success, scala.util.Failure
    def exercise21() = {
      def parse(l: List[String]): List[Try[Int]] = l.map(s => Try(s.toInt))
      def tryToNumber(default: Int)(l: List[Try[Int]]): List[Int] = l.map {
        //Try has two types 1)Success(v) 2)Failure(e)
        case _ => ???
      }

      val parsed = parse(List("1", "2", "aa", "5", "ccc"))

      check("EXERCISE21")(tryToNumber(0)(parsed), List(1, 2, 0, 5, 0))
      check("EXERCISE21")(tryToNumber(1)(parsed), List(1, 2, 1, 5, 1))

    }


    def exercise22() = {

      //implement without recursion
      def sizeListAsADT[A](l: List[A]): Int = l match {
        case Nil => 0
        case ::(_, Nil) => 1
        // add case for 2,3,4 elements
        case _ => throw new RuntimeException("don't know how to handle this case")
      }

      val check22=check("EXERCISE22") _

      check22(sizeListAsADT(List()),0)
      check22(sizeListAsADT(List(1)),1)
      check22(sizeListAsADT(List(1, 2)),2)
      check22(sizeListAsADT(List(1, 2, 3)),3)
      check22(sizeListAsADT(List(1, 2, 3, 4)) , 4)

    }

  }


/**
*  x Natural Numbers as ADT
 * x How it can be used in recursion
 */
  object ExerciseLevel3 {

    sealed trait Natural

    case class Z() extends Natural

    case class N(n: Natural) extends Natural

    sealed trait Expression

    case class Add(n1: Natural, n2: Natural) extends Expression

    case class Multiply(n1: Natural, n2: Natural) extends Expression

    def evaluate(n: Natural): Int = n match {
      case _ => ??? //handle two cases Z and N , use recursion to calculate final value
    }

    def evaluate(e: Expression): Int = e match {
      case _ => ???
    }

    def exercise31() = {
      val n1: Natural = N(N(Z()))
      val n2: Natural = N(N(N(Z())))

      val check31=check("EXERCISE31") _

      check31(evaluate(n1),2)
      check31(evaluate(n2),3)
      check31(evaluate(Add(n1, n2)) , 5)
      check31(evaluate(Multiply(n1, n2)) ,6)
    }
  }

  //FP BOSS - Either Type - ENERGY : [=====]
  // [**** LEFT  ||| RIGHT *****]
  object ExerciseLevel4 {

    sealed trait CustomEither[+A, +B] {
      def fold[C](onError: A => C)(onSuccess: B => C) = this match {
        //can we use children types here? Answer in Part5
        case _ => ???
      }
    }

    case class CustomLeft[A](a: A) extends CustomEither[A, Nothing]

    case class CustomRight[B](b: B) extends CustomEither[Nothing, B]

    case class Request(url: String, method: String, param: String)

    case class Response(code: Int, content: String)

    def isNumber(s: String) = Try(s.toInt) match {
      case Success(v) => CustomRight(v)
      case Failure(e) => CustomLeft(e.toString)
    }

    def server(req: Request): Response = isNumber(req.param)
      .fold(error => Response(400, error))(v => Response(200, v.toString))

    def exercise41() = {
      check("EXERCISE41")(server(Request("someUrl", "GET", "1")).code,200)
      check("EXERCISE41")(server(Request("someUrl", "GET", "WRONG")).code,400)
    }

  }




}
