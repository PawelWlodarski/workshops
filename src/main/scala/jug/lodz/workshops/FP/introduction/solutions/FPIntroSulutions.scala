package jug.lodz.workshops.FP.introduction.solutions

import scala.annotation.tailrec

object FPIntroSulutions {

  def main(args: Array[String]): Unit = {
    //recursiveProduct()
    //twoCustomStrategies()
    decorateFunction()
  }

  //Ex1
  def recursiveProduct()={
    @tailrec
    def product(l:List[Int],acc:Int=1):Int= l match {
      case Nil => 0
      case head::Nil => head*acc
      case head::tail=> product(tail,acc*head)
    }
    println("Exercise 1")
    println(product(List(1,2,3,4)))
  }

  //Ex2
  def twoCustomStrategies(): Unit ={
    type IntMappingStrategy = Int=>Int
    val multiplyByTwoStrategy:IntMappingStrategy=_*2
    val powerTwoStrategy:IntMappingStrategy= i => i * i
    println("Exercise 2")
    println("multiply by two"+List(1,2,3,4,5).map(multiplyByTwoStrategy))
    println("power of two"+List(1,2,3,4,5).map(powerTwoStrategy))
  }

  //Ex3
  def decorateFunction()={
    type TwoParamFunction=(Int,Int)=>Int
    val add:TwoParamFunction=(a:Int,b:Int)=>a+b

    val withLogging: TwoParamFunction => TwoParamFunction = f =>
      (a,b)=>{
        println(s"a=$a, b=$b")
        f(a,b)
      }

    val addWithLogging=withLogging(add)
    println(addWithLogging(3,4))

  }

  //andThen
  def andThen[A,B,C](f1:A=>B,f2:B=>C)= (a:A) => f2(f1(a))

  //partial application
  def partial[A,B,C](f:(A,B)=>C,a:A): B=>C = (b:B) => f(a,b)
  
  //currying
  def currying[A,B,C](f:(A,B)=>C):A=>B=>C = (a:A) => (b:B) => f(a,b)

  //uncurry
  def uncurry[A,B,C](f:A=>B=>C):(A,B)=>C = (a,b) => f(a)(b)
}
