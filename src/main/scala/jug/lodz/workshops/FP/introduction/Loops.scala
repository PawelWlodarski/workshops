package jug.lodz.workshops.FP.introduction

import scala.annotation.tailrec

object Loops {

  def main(args: Array[String]) {
    val ints=List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    imperativeLoop(ints)
//    recursion(ints)
//    println(product(List(1,2,3,4)))
  }


  def imperativeLoop(list: List[Int])={
    var sum=0
    for(i <- list){
      sum=sum+i
    }
    println(sum) //55
  }

  def recursion(list: List[Int])={

    //@tailrec
    def sum(l:List[Int],acc:Int=0):Int=
      if (l isEmpty) acc else sum(l.tail,acc+l.head)

    println(sum(list))
  }

  //Exercise - make it compile
  //@tailrec
  def product(l:List[Int]):Int= l match {
    case Nil => 0
    case head::Nil => head
    case head::tail=> head * product(tail)
  }

}
