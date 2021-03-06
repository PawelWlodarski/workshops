package jug.lodz.workshops.functions.answers

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by pawel on 04.07.16.
  */
class FunctionsPart1DefinitionExercisesAnswer extends FunSuite with Matchers{


  //***********"LEVEL 1"****************
  // TRY TO EXPERIMENT WITH DEFINING FUNCTION IN VARIOUS FORMS - SHORT,LONG ETC..

  test("define simple functions so test passes"){
      val square : Int=>Int = i=>i*i
      val isEven: Int=>Boolean = i=>i%2==0

      val isEven_underscore: Int=>Boolean = _%2==0  //try this one with underscore

      square(2) shouldBe 4
      square(3) shouldBe 9
      isEven(2) shouldBe true
      isEven(3) shouldBe false
      isEven_underscore(3) shouldBe false
  }


  test("operations on tuples"){
    val snd : ((String,Int)) => Int = _._2  //returns second element
    val swap : ((String,Int)) => (Int,String) = t=> (t._2,t._1)  //swap elements


    snd(("a",1)) shouldBe(1)
    snd("b",2) shouldBe(2)

    swap(("a",1)) shouldBe((1,"a"))
    swap("b",2) shouldBe(2,"b")
  }


  test("compose 3 functions in proper order"){
    val fst: ((String,Int)) => String = t => t._1
    val parse : String=>Int = s => s.toInt
    val square : Int=>Int= i => i * i

    val squareFst: ((String,Int)) => Int = fst andThen parse andThen square

    squareFst("2",1) shouldBe 4
    squareFst("3",1) shouldBe 9
  }

  test("closure example"){
    val prefix="RESULT : "

    val createResult:String=>String = str => prefix + str  //use 'prefix' value

    createResult("example1") shouldBe "RESULT : example1"
  }

  //LEVEL2

  test("custom 'andThen' method"){
    val squareStr=customAndThen(_.toInt,i=>Math.sqrt(i))

    squareStr("4") shouldBe 2.0
    squareStr("9") shouldBe 3.0

  }

  def customAndThen(f:String=>Int, g:Int=>Double)= f andThen g


  //LEVEL3

  test("andThen generic"){
    val isStringEven=andThenGeneric((s:String)=>s.toInt,(i:Int)=>i%2==0)  // why we need a type Int? We will return to this in 'currying' section
    val squareAndToString:Int=>String=andThenGeneric(i=>i*i,(i:Int)=>"Result is : "+i)

    isStringEven("2") shouldBe true
    isStringEven("3") shouldBe false

    squareAndToString(3) shouldBe "Result is : 9"
    squareAndToString(4) shouldBe "Result is : 16"
  }


  def andThenGeneric[A,B,C](f:A=>B,g:B=>C)=f andThen g

  test("compose sequence of functions"){
    val intToInts=Seq[Int=>Int](_+1, i=>i*i , i=>i-1,_-1)
    val stringToStrings=Seq[String=>String](_+"a",_+"b",_+"c")

    andThenSeq(intToInts)(2) shouldBe 7
    andThenSeq(intToInts)(3) shouldBe 14
    andThenSeq(stringToStrings)("start_") shouldBe "start_abc"
  }


  def  andThenSeq[A](seq:Seq[A=>A])={
      seq.reduce((f1,f2)=>f1 andThen f2)
      //      seq.reduce(_ andThen _)
      // Function.chain(seq)
  }


}
