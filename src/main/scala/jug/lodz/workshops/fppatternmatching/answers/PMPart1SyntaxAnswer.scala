package jug.lodz.workshops.fppatternmatching.answers

import jug.lodz.workshops.fppatternmatching.answers.PMPart1SyntaxAnswer.ExerciseLevel2.ExerciseLevel3

import scala.annotation.tailrec

/**
  * Created by pawel on 22.04.16.
  */
object PMPart1SyntaxAnswer {


  object Demonstration {

    def demo(): Unit = {
      println("[DEMONSTRATION]")
      println("\n *MATCH BY VALUE")
      val someValue: Int = 5 //check what happens when given value is not in the pattern matching
      someValue match {
        case 1 => println("  -->value is one")
        case 5 => println("  -->value is five")
      }

      println("\n *DEFAULT VALUE")

      val someValue2: Int = 2
      someValue2 match {
        case 1 => println("  -->value is one")
        case 5 => println("  -->value is five")
        case _ => println("  -->some other value")
      }

      println("\n *VARIABLE IN PM & GUARDS")
      val someValue3 = 3
      someValue3 match {
        case even if (even % 2 == 0) => println(s"  -->value is even : $even")
        case odd => println(s"  -->value is odd : $odd")
      }

      println("\n *MATCHING LIST")
      val someValues = List(1, 2, 3, 4, 5, 6)
      someValues match {
        case first :: second :: third :: tail => println(s"  --> $first , $second , $third, $tail")
        case oneElement :: Nil => println(s"  --> this list has only one element  : $oneElement")
        case _ => println("  --> list is empty") // comment this line and check warning , why compiler can notice that some combinations are missing?
      }

    }
  }

  object ExerciseLevel1 {
    def exercise11() = {
      val value1 = 1
      val result1 = value1 match {
        case 1 => 1 + 2
        case 2 => 2 + 2
        case _ => 100
      }

      println(s" *EXERCISE11 : ${result1 == 3}")
    }

    def exercise12() = {
      def matchOnelement(element: Int) = element match {
        // use guard if
        case e if (e % 2 == 0) => "even"
        case _ => "odd"
      }
      val result2 = List(1, 2, 3, 4, 5).map(matchOnelement) //map will apply matchOneElement function on each element
      println(s""" *EXERCISE12 : ${result2 == List("odd", "even", "odd", "even", "odd")}""")
    }
  }

  object ExerciseLevel2 {
    def exercise21() = {
      def firstElementOrZero(l: List[Int]): Int = l match {
        // this is internal method // check for Nil -> empty list
        case Nil => 0
        case head :: tail => head
      }

      println(s" *EXERCISE21 : ${firstElementOrZero(List(1, 2, 3)) == 1}")
      println(s" *EXERCISE21 : ${firstElementOrZero(List(2, 3)) == 2}")
      println(s" *EXERCISE21 : ${firstElementOrZero(List()) == 0}")
    }


    def exercise22() = {
      //reverts the list only if it has three elements
      def revertListIfSizeThree[A](l: List[A]): List[A] = l match {
        case one :: two :: three :: Nil => List(three, two, one)
        case list => list
      }

      println(s" *EXERCISE22 : ${revertListIfSizeThree(List(1, 2, 3)) == List(3, 2, 1)}")
      println(s" *EXERCISE22 : ${revertListIfSizeThree(List('a', 'b', 'c')) == List('c', 'b', 'a')}")
      println(s" *EXERCISE22 : ${revertListIfSizeThree(List(1, 2, 3, 4)) == List(1, 2, 3, 4)}")
    }


    object ExerciseLevel3 {
      def exercise31() = {
        //use recursion
        def size[A](list: List[A]): Int = list match {
          case Nil => 0
          case head :: tail => 1 + size(tail)
        }

        println(s" *EXERCISE31 : ${size(List(1, 2, 3)) == 3}")
        println(s" *EXERCISE31 : ${size(List(1, 2, 3, 3, 3)) == 5}")
        println(s" *EXERCISE31 : ${size(List('a', 'b')) == 2}")
      }

      def exercise32() = {
        @tailrec
        def lastElement[A](list: List[A]): Option[A] = list match {
          case Nil => None
          case last :: Nil => Some(last)
          case head :: tail => lastElement(tail)
        }

        println(s" *EXERCISE32 : ${lastElement(List(1, 2, 3)) == Some(3)}")
        println(s" *EXERCISE32 : ${lastElement(List(1, 2, 5, 7, 88, 7)) == Some(7)}")
        println(s" *EXERCISE32 : ${lastElement(List()) == None}")
      }
    }

  }

  def main(args: Array[String]) {
    Demonstration.demo()
    println("[------EXERCISES------]")
    println("\n[------EXERCISE LEVEL 1------]")
    ExerciseLevel1.exercise11()
    ExerciseLevel1.exercise12()

    println("\n[------EXERCISE LEVEL 2------]")
    ExerciseLevel2.exercise21()
    ExerciseLevel2.exercise22()

    println("\n[------EXERCISE LEVEL 3------]")
    ExerciseLevel3.exercise31()
    ExerciseLevel3.exercise32()
  }

}
