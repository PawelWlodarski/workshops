package jug.lodz.workshops.fp1.exercises

/**
  * Created by pawel on 21.02.16.
  */
object ScalaFP1ComposeFunctions {

  val addOne:Int=>Int = x=>x+1

  val multiplyByTwo : Int=>Int = x=> x*2

  def main(args: Array[String]) {
    val andThenResult=addOne.andThen(multiplyByTwo)(1)
    println(andThenResult)

    val composeResult=addOne.compose(multiplyByTwo)(1)
    println(composeResult)

    //lab
//    println("customAndThen : "+customAndThen(addOne,multiplyByTwo)(1))
//    println("customCompose : "+customCompose(addOne,multiplyByTwo)(1))
  }

  def customAndThen(f1:Int=>Int, f2:Int=>Int):Int=>Int = ???


  def customCompose(f1:Int=>Int, f2:Int=>Int):Int=>Int = ???

}
