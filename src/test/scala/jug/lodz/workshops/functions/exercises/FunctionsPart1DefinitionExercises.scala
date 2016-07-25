package jug.lodz.workshops.functions.exercises

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by pawel on 04.07.16.
  */
class FunctionsPart1DefinitionExercises extends FunSuite with Matchers{


  //***********"LEVEL 1"****************
  // TRY TO EXPERIMENT WITH DEFINING FUNCTION IN VARIOUS FORMS - SHORT,LONG ETC..

  test("complete function definition so that test passes"){
      val square : Int=>Int = ???      // x*x
      val isEven: Int=>Boolean = ???     // 3 % 2 -> 1 , 4% 2 -> 0

      val isEven_underscore: Int=>Boolean = ???  //use form with underscore

      square(2) shouldBe 4
      square(3) shouldBe 9
      isEven(2) shouldBe true
      isEven(3) shouldBe false
      isEven_underscore(3) shouldBe false
  }


  test("operations on tuples"){
    val snd : ((String,Int)) => Int = ???  //returns second element
    val swap : ((String,Int)) => (Int,String) = ???  //swap elements   t._1 - first element, t._2 - second element


    snd(("a",1)) shouldBe(1)
    snd("b",2) shouldBe(2)

    swap(("a",1)) shouldBe((1,"a"))
    swap("b",2) shouldBe(2,"b")
  }


  //quess the order of function composition so that result is correct
  test("compose 3 functions in proper order"){
    val square : Int=>Int= i => i * i
    val fst: ((String,Int)) => String = t => t._1
    val parse : String=>Int = s => s.toInt

    val squareFst: ((String,Int)) => Int = ???

    squareFst("2",1) shouldBe 4
    squareFst("3",1) shouldBe 9
  }

  //use external value inside lambda
  test("closure example"){
    val prefix="RESULT : "

    val createResult:String=>String = ???  //use 'prefix' value

    createResult("example1") shouldBe "RESULT : example1"
  }

  //LEVEL2

  //don't change test - implement 'customAndThen' method without using Function.andThen
  test("custom 'andThen' method"){
    val squareStr=customAndThen(_.toInt,i=>Math.sqrt(i))

    squareStr("4") shouldBe 2.0
    squareStr("9") shouldBe 3.0

  }

  //don't use Function.andThen
  def customAndThen(f:String=>Int, g:Int=>Double):String=>Double= ???


  //LEVEL3

  //don't change test implement 'andThenGeneric' without Function.andThen
  test("andThen generic"){
    val isStringEven=andThenGeneric((s:String)=>s.toInt,(i:Int)=>i%2==0)  // why we need a type Int? We will return to this in 'currying' section
    val squareAndToString:Int=>String=andThenGeneric(i=>i*i,(i:Int)=>"Result is : "+i)

    isStringEven("2") shouldBe true
    isStringEven("3") shouldBe false

    squareAndToString(3) shouldBe "Result is : 9"
    squareAndToString(4) shouldBe "Result is : 16"
  }


  //don't use Function.andThen
  def andThenGeneric[A,B,C](f:A=>B,g:B=>C) : A=>C= ???

  //don't change test - complete 'andThenSeq' method
  test("compose sequence of functions"){
    val intToInts=Seq[Int=>Int](_+1, i=>i*i , i=>i-1,_-1)
    val stringToStrings=Seq[String=>String](_+"a",_+"b",_+"c")

    andThenSeq(intToInts)(2) shouldBe 7
    andThenSeq(intToInts)(3) shouldBe 14
    andThenSeq(stringToStrings)("start_") shouldBe "start_abc"
  }


  //compose all functions from a given collection
  def  andThenSeq[A](seq:Seq[A=>A]):A=>A={
      ???
  }


}
