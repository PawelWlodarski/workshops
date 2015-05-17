package jug.lodz.workshops.FP.introduction.solutions

import scala.annotation.tailrec

object FPExercises {

  def main(args: Array[String]): Unit = {
    recursiveProduct()
    //twoCustomStrategies()
    //decorateFunction()
  }

  //Ex1
  def recursiveProduct()={
    //@tailrec
    def product(l:List[Int]):Int= l match {
      case Nil => 0
      case head::Nil => head
      case head::tail=> head * product(tail)
    }
    println("Exercise 1")
    println(product(List(1,2,3,4)))
  }

  //Ex2
  def twoCustomStrategies(): Unit ={
    type IntMappingStrategy = Int=>Int
    val multiplyByTwoStrategy:IntMappingStrategy= ???
    val powerTwoStrategy:IntMappingStrategy= ???
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
  }

  //andThen
  def andThen[A,B,C](f1:A=>B,f2:B=>C)= ???

  //partial application
  def partial[A,B,C](f:(A,B)=>C,a:A): B=>C = ???

  //currying
  def currying[A,B,C](f:(A,B)=>C):A=>B=>C = ???

  //uncurry
  def uncurry[A,B,C](f:A=>B=>C):(A,B)=>C = ???
}
