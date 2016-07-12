package jug.lodz.workshops.old.fp1.exercises

/**
  * Created by pawel on 20.02.16.
  */
object ScalaFP1ExercisesFunctionImplementation {

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
  lazy val multiplyXByTwo: Int=>Int = ???
  lazy val multiplyXByTwoNoAlias: Function[Int,Int] = ???
  lazy val multiplyXByTwoFull:Function[Int,Int] = ???
  lazy val multiplyXByTwoShort:Int=>Int = ???

  //ADDITIONAL EXERCISE
//  lazy val addTwoNumbers: (Int,Int) => Int = ???
//  lazy val addTwoNumbersNoAlias: Function2[Int,Int,Int]= ???
//  lazy val addTwoNumbersLong: Function2[Int,Int,Int] = ???
//  lazy val addTwoNumbersShort: (Int,Int) => Int = ???

  def main(args: Array[String]) {
    //DEMONSTRATION
    println("Demonstration f(x) = x+1")
    println("standard form  : " + addOneToX(1))
    println("without Alias  : " + addOneToXNoAlias(1))
    println("full form  :     " + addOneToXFull(1))
    println("shortest form  : " + addOneToXShort(1))

    //EXERCISE 1
//    println("EXERCISE1")
//    println(multiplyXByTwo(1) == 2)
//    println(multiplyXByTwo(2) == 4)
//    println(multiplyXByTwoNoAlias(3) == 6)
//    println(multiplyXByTwoNoAlias(1) == 2)
//    println(multiplyXByTwoFull(1)==2 )
//    println(multiplyXByTwoFull(5)==10)
//    println(multiplyXByTwoShort(11)==22 )
//    println(multiplyXByTwoShort(110)==220 )

    //ADDITIONAL EXERCISE
//    println("ADDITIONAL EXERCISE")
//    println(addTwoNumbers(1,2)==3)
//    println(addTwoNumbers(2,5)==7)
//    println(addTwoNumbersNoAlias(1,2)==3)
//    println(addTwoNumbersNoAlias(2,5)==7)
//    println(addTwoNumbersLong(1,2)==3)
//    println(addTwoNumbersLong(2,5)==7)
//    println(addTwoNumbersShort(1,2)==3)
//    println(addTwoNumbersShort(2,5)==7)

  }
}
