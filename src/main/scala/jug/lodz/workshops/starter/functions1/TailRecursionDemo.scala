package jug.lodz.workshops.starter.functions1

import jug.lodz.workshops.WorkshopDisplayer._

import scala.annotation.tailrec

object TailRecursionDemo {

  def main(args: Array[String]): Unit = {
      header("tail recursion")

      section("unsafeSum(5)",unsafeSum(5))
//      section("unsafeSum(500000000)",unsafeSum(500000000))  ---> Stack overflow
      section("safeSum(5)",safeSum(5))
      section("safeSum(500000000)",safeSum(500000000))
  }

  def unsafeSum(n:Int):Int =
    if(n<=0) 0
    else n + unsafeSum(n-1)

  //you can use named parameters in safeSum but it will show implementation details
  def safeSum(n:Int):Int = {
    @tailrec
    def internalLoop(actual:Int,sum:Int):Int=
      if(actual<=0) sum
      else internalLoop(actual-1,sum+actual)

    internalLoop(n,0)
  }
}