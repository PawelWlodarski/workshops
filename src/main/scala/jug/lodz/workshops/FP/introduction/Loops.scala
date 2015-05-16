package jug.lodz.workshops.FP.introduction

import scala.annotation.tailrec

object Loops {

  def main(args: Array[String]) {
    imperativeLoop()
    recursion()
  }


  def imperativeLoop()={
    val list=List(1,2,3,4,5,6,7,8,9,10)
    var sum=0
    for(i <- list){
      sum=sum+i
    }
    println(sum) //55
  }

  def recursion()={
    val list=List(1,2,3,4,5,6,7,8,9,10)

    //@tailrec
    def sum(l:List[Int],acc:Int=0):Int=
      if (l isEmpty) acc else sum(l.tail,acc+l.head)

    println(sum(list))
  }

}
