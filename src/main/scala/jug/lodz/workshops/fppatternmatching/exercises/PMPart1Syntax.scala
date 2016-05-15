package jug.lodz.workshops.fppatternmatching.exercises

import jug.lodz.workshops.Workshops
import jug.lodz.workshops.Workshops.check

import scala.annotation.tailrec

/**
  * Created by pawel on 22.04.16.
  */
object PMPart1Syntax {

  def main(args: Array[String]) {
    Demonstration.demo()
    //    println("[------EXERCISES------]")
    //    println("\n[------EXERCISE LEVEL 1------]")
    //        ExerciseLevel1.exercise11()
    //    ExerciseLevel1.exercise12()
    //
    //    println("\n[------EXERCISE LEVEL 2------]")
    //    ExerciseLevel2.exercise21()
    //    ExerciseLevel2.exercise22()
    //
    //    println("\n[------EXERCISE LEVEL 3------]")
    //    ExerciseLevel3.exercise31()
    //    ExerciseLevel3.exercise32()
  }


  object Demonstration {

    /**
      * x - patern matching on simple values
      * x - missing matches
      * x - pattern matching on lists
      */
    def demo(): Unit = {
      println("[DEMONSTRATION]")
      println("\n *MATCH BY VALUE")
      val someValue: Int = 5 //check what happens when given value is not in the pattern matching
//      someValue match {
//        case 1 => println("  -->value is one")
//        case 5 => println("  -->value is five")
//      }

      println("\n *DEFAULT VALUE")

      val someValue2: Int = 2
//      someValue2 match {
//        case 1 => println("  -->value is one")
//        case 5 => println("  -->value is five")
//        case _ => println("  -->some other value")
//      }

      println("\n *VARIABLE IN PM & GUARDS")
      val someValue3 = 3
//      someValue3 match {
//        case even if (even % 2 == 0) => println(s"  -->value is even : $even")
//        case odd => println(s"  -->value is odd : $odd")
//      }

      println("\n *MATCHING LIST")
      val someValues = List(1, 2, 3, 4, 5, 6)
//      someValues match {
//        case first :: second :: third :: tail => println(s"  --> $first , $second , $third, $tail")
//        case oneElement :: Nil => println(s"  --> this list has only one element  : $oneElement")
//        case _ => println("  --> list is empty") // comment this line and check warning , why compiler can notice that some combinations are missing?
//      }

    }
  }

  /**
    * Learning points :
    * x pattern matching on simple types
    * x handling missing values
    * x pattern matching on types
    */
  object ExerciseLevel1 {
    //result1 must equal 3
    def exercise11() = {
      val value1 = 1
      val result1 = value1 match {
        case 1 => ???
        case 2 => 2 + 2
        case _ => 100
      }

      check("EXERCISE11")(result1,3)
    }
    // x - mapping on lists
    // x - use guards
    // List(1) -> List("odd")
    // List(1,2) -> List("odd","even")
    // List(1,2,3) -> List("odd","even","odd")
    def exercise12() = {
      def matchOnelement(element: Int) = element match {
        // use guard if
        case e if (e % 2 == 0) => ???
      }

      val result2 = List(1, 2, 3, 4, 5).map(matchOnelement) //map will apply matchOneElement function on each element
      check("EXERCISE12")(result2,List("odd", "even", "odd", "even", "odd"))
    }
  }

  /**
    * Learning points - working with lists
   */
  object ExerciseLevel2 {
    // get first element of list or zero if list is empty
    def exercise21() = {
      def firstElementOrZero(l: List[Int]): Int = l match {
        // this is internal method // check for Nil -> empty list
        case Nil => ???
        case head :: tail => ???
      }

      val check21=check("EXERCISE21") _
      check21(firstElementOrZero(List(1, 2, 3)),1)
      check21(firstElementOrZero(List(2, 3)),2)
      check21(firstElementOrZero(List()),0)
    }


    def exercise22() = {
      //reverts the list only if it has three elements
      def revertListIfSizeThree[A](l: List[A]): List[A] = ???

      val check22=check("EXERCISE22") _
      check22(revertListIfSizeThree(List(1, 2, 3)),List(3, 2, 1))
      check22(revertListIfSizeThree(List('a', 'b', 'c')),List('a', 'b', 'c'))
      check22(revertListIfSizeThree(List(1, 2, 3, 4)),List(1, 2, 3, 4))
    }




  }


  /**
    * x pattern matchign and recursion
    */
  object ExerciseLevel3 {
    def exercise31() = {
      //use recursion to calculate size of the list
      def size[A](list: List[A]): Int = ???

      val check31=check("EXERCISE31") _
      check31(size(List(1, 2, 3)),3)
      check31(size(List(1, 2, 3, 3, 3)),5)
      check31(size(List('a', 'b')),2)

    }

    def exercise32() = {
      //return last element of the list - USE PATTERN MATCHING
      //return Some(x) if list is not empty
      //return None if list is empty
      //@tailrec
      def lastElement[A](list: List[A]): Option[A] = ???

      val check32=check("EXERCISE32") _
      check32(lastElement(List(1, 2, 3)),Some(3))
      check32(lastElement(List(1, 2, 5, 7, 88, 7)),Some(7))
      check32(lastElement(List()),None)
    }
  }
}
