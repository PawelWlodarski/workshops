package jug.lodz.workshops.fp1.answers

/**
  * Created by pawel on 20.02.16.
  */
object ScalaFP1ExercisesFunctionImplementationAnswer {

  //FUNCTION IMPLEMENTATION
  // example 1 - standard form
  val addOneToX: Int=>Int = x => x+1

  //example 2 - without alias type
  val addOneToXNoAlias: Function[Int,Int] = x => x+1

  //example 3 - Full declaration
  val addOneToXFull:Function[Int,Int]=new Function[Int,Int]{
    override def apply(v1: Int): Int = v1+1
  }

  //example 4 - shortest form with underscore
  val addOneToXShort:Int=>Int = _+1


  //EXERCISE1
  lazy val multiplyXByTwo: Int=>Int = x=>x*2
  lazy val multiplyXByTwoNoAlias: Function[Int,Int] = x=>x*2
  lazy val multiplyXByTwoFull:Function[Int,Int] = new Function[Int,Int] {
    override def apply(v1: Int): Int = v1*2
  }
  lazy val multiplyXByTwoShort:Int=>Int = _ *2

  //ADDITIONAL EXERCISE
  lazy val addTwoNumbers: (Int,Int) => Int = (a,b) => a+b
  lazy val addTwoNumbersNoAlias: Function2[Int,Int,Int]= (a,b) => a+b
  lazy val addTwoNumbersLong: Function2[Int,Int,Int] = new Function2[Int,Int,Int] {
    override def apply(v1: Int, v2: Int): Int = v1+v2
  }
  lazy val addTwoNumbersShort: (Int,Int) => Int = _+_

  def main(args: Array[String]) {
    //DEMONSTRATION
    println("Demonstration f(x) = x+1")
    println("standard form  : " + addOneToX(1))
    println("without Alias  : " + addOneToXNoAlias(1))
    println("full form  :     " + addOneToXFull(1))
    println("shortest form  : " + addOneToXShort(1))

    //EXERCISE 1
    println("EXERCISE1")
    println(multiplyXByTwo(1) == 2)
    println(multiplyXByTwo(2) == 4)
    println(multiplyXByTwoNoAlias(3) == 6)
    println(multiplyXByTwoNoAlias(1) == 2)
    println(multiplyXByTwoFull(1)==2 )
    println(multiplyXByTwoFull(5)==10)
    println(multiplyXByTwoShort(11)==22 )
    println(multiplyXByTwoShort(110)==220 )

    //ADDITIONAL EXERCISE
    println("ADDITIONAL EXERCISE")
    println(addTwoNumbers(1,2)==3)
    println(addTwoNumbers(2,5)==7)
    println(addTwoNumbersNoAlias(1,2)==3)
    println(addTwoNumbersNoAlias(2,5)==7)
    println(addTwoNumbersLong(1,2)==3)
    println(addTwoNumbersLong(2,5)==7)
    println(addTwoNumbersShort(1,2)==3)
    println(addTwoNumbersShort(2,5)==7)

  }
}
