package jug.lodz.workshops.functions.exercises

import org.scalatest.{FunSuite, Matchers}

import scala.annotation.tailrec
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Created by pawel on 09.07.16.
  */
class unctionsPart3MethodsAndFunctionsExercises extends FunSuite with Matchers{


  //LEVEL1
  //Convert methods defined below test to proper functions
  test("convert method to a function"){
        val addF: (Int, Int) => Int = ???
        val maxF: (Int, Int) => Int = ???
        val isPositiveF: Int => Boolean = ???

        addF(3,5) shouldBe 8
        addF(1,2) shouldBe 3
        maxF(4,9) shouldBe 9
        maxF(44,9) shouldBe 44
        isPositiveF(5) shouldBe true
        isPositiveF(-5) shouldBe false
  }

  def add(i1:Int,i2:Int) = i1+i2
  def max(i1:Int,i2:Int) = if(i1>i2) i1 else i2
  def isPositive(i:Int) = i>0


  //convert 'contains' method to a function
  //  * containsF - without applying anything
  //  * containsSeven - with applying first parameter
  test("curried method to function conversion"){

    val containsF: Int => Seq[Int] => Boolean = ???
    val containsSeven: Seq[Int] => Boolean = ???


    containsF(5)(Seq(1,2,3)) shouldBe false
    containsF(5)(Seq(1,5,3)) shouldBe true
    containsSeven(Seq(1,2,3)) shouldBe false
    containsSeven(Seq(1,7,3)) shouldBe true
  }

  def contains(e:Int)(s:Seq[Int]) = s.contains(e)


  //LEVEL2
  //Don't modify test , implement 'safeActionOnString' method and analyse invocation
  test("custom structure : safe action on string"){
    val result1=safeActionOnString("Rudy102"){s=>
      s.filter(_.isDigit).toSet
    }

    val result2=safeActionOnString(null){s=>
      s.filter(_.isDigit).toSet
    }

    result1 shouldBe Set('1','0','2')
    result2 shouldBe empty
  }


  def safeActionOnString[A](string : String)(action : String => A): A ={
      if(???) ??? else ???       // if string is null invoke with ""
  }


  // 'safeActionOnList' is already implemented
  // complete test so that buffer contains proper element in assertion
  test("custom structure : side effect function"){
      val buffer=ArrayBuffer[Int]()

      safeActionOnList(List(1,2,3,4,5)){elem =>
        ???
      }

      buffer should contain allOf (1,2,3,4,5)
  }


  def safeActionOnList(list : List[Int])(action : Int => Unit): Unit ={
    if(list != null)  list.foreach(action)
  }


  // implement f1 and f2 so that (f1 _) andThen (f2 _) compose to proper function
  test("method to function and then compose"){
    def f1(i:String):Int = ???  //parse and add 1
    def f2(i:Int):Int = ???  //square


    val composition: (String) => Int = (f1 _) andThen (f2 _)


    composition("1") shouldBe 4
    composition("2") shouldBe 9
  }


  //DON't CHANGE TEST - IMPLEMENT recSum method
  test("recursion reduce"){
    recSum(List(1,2,3,4,5)) shouldBe 15
    recSum(List(10,20,30)) shouldBe 60
  }


  //use
  //l.isEmpty
  //l.head - return first element List(1,2,3).head -> 1
  //l.tail - remove first element List(1,2,3).tail -> List(2,3)
  @tailrec
  final def recSum(l:List[Int],sum:Int=0): Int ={
    if(l.isEmpty) sum
    else  recSum(l.tail,sum+l.head)
  }


  // LEVEL 3

  //Don't modify test - implement 'mapRec'
  test("implement 'mapRec' method"){
    val l1=List(1,2,3,4,5)
    val l2=List("aaa","bbb","ccc")

    mapRec(l1)(_+10) should contain allOf(11,12,13,14,15)
    mapRec(l2)(_.toUpperCase) should contain allOf("AAA","BBB","CCC")
  }

  // use :
  // l.isEmpty
  // l.tail
  // l.head
  // buffer += elem adds element to buffer
  def mapRec[A,B](l:List[A])(f:A=>B)={
    @tailrec
    def mapRecBuffer(l:List[A],buffer:ListBuffer[B]):List[B]={
      if(l.isEmpty) buffer.toList
      else mapRecBuffer(l.tail, buffer += f(l.head))
    }

    mapRecBuffer(l,ListBuffer())
  }


  // Don't modify test implement 'recFilter'
  test("implement 'recFilter' method"){
    val l1=List(1,2,3,4,5)
    val l2=List("aa", "cccc" , "ddddd")

    recFilter(l1)(_ > 3) should contain allOf(4,5)
    recFilter(l2)(_.size > 2) should contain allOf("cccc","ddddd")
  }

  // use :
  // l.isEmpty
  // l.tail
  // l.head
  // buffer += elem adds element to buffer
  def recFilter[A](l:List[A])(predicate:A=>Boolean): List[A] ={
    def recFilterBuffer(l:List[A],buffer:ListBuffer[A]): List[A]= {
      if(l.isEmpty) buffer.toList
      else {
         val newBuffer = if(predicate(l.head)) buffer += l.head else buffer
          recFilterBuffer(l.tail, newBuffer)
      }
    }

    recFilterBuffer(l,ListBuffer())
  }

}
