package jug.lodz.workshops.starter.functions1.answers

import org.scalatest.{MustMatchers, WordSpec}

import scala.annotation.tailrec

class TailRecursionAnswers extends WordSpec with MustMatchers{

  "EXERCISE1" should {
    "Sum custom list of integers" in {
      @tailrec
      def sumList(list:CustomList,sum:Int=0):Int = list match {
        case ListEnd => sum
        case ListElem(e, tail) => sumList(tail,sum+e)
      }

      val l1=ListElem(1,ListElem(2,ListElem(3,ListEnd)))
      val l2=ListElem(15,ListElem(27,ListEnd))

      sumList(l1) mustBe 6
      sumList(l2) mustBe 42

    }
  }

  "EXERCISE2" should {
    "generate n elements" in {

      val expected2Elem=ListElem(4,ListElem(4,ListEnd))
      val expected3Elem=ListElem(6,ListElem(6,ListElem(6,ListEnd)))

      Exercise2.generate(4,2) mustBe expected2Elem
      Exercise2.generate(6,3) mustBe expected3Elem
    }
  }

}

sealed trait CustomList
case class ListElem(a:Int,rest:CustomList) extends CustomList
case object ListEnd extends CustomList

object Exercise2{

  def generate(element:Int,howMany:Int) : CustomList = {
      @tailrec
      def innerLoop(current:CustomList,howMany:Int) : CustomList =
        if(howMany<=0) current
        else innerLoop(ListElem(element,current),howMany-1)

    innerLoop(ListEnd,howMany)
  }
}