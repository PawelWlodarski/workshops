package jug.lodz.workshops.fp2.exercises

import scala.io.{BufferedSource, Source}

/**
  * Created by pwlodarski on 2016-03-15.
  */
object FP2ReadingFile1 {

  def demonstration(l:List[Int]):List[Int]= l.filter(e=>e>7)

  //exercise
  def filter_1(l:List[Int]):List[Int]= ???

  //exercise
  val someSet=Set(3,7,10,50)
  def filter_2(l:List[Int]):List[Int]= ???

  //additional
  def additional(l:List[Int]): (List[Int], List[Int]) = ???

  def main(args: Array[String]) {
    //PREPARATION
    println(demonstration(List(1,2,3,4,5,6,7,8,9,10))==List(8,9,10))

    //EXERCISES
    println(filter_1(List(1,2,3,4,5,6,7,8,9,10))==List(2,4,6,8,10))
    println(filter_1(List(10,13,18,23,37,50))==List(10,18,50))

    println(filter_2(List(1,2,3,4,5,6,7,8,9,10))==List(3,7,10))
    println(filter_2(List(10,13,18,23,37,50))==List(10,50))

    //EXERCISE - Display only rows where login==user1
    readFile()

    //ADDITIONAL
    println( additional(List(1,2,3,4)) == (List(2,4),List(1,3)))
  }

  def readFile(): Unit = {
    val source: BufferedSource = Source.fromURL(getClass().getResource("/fpjava/purchases.csv"))
    try {
      source.getLines()
        // ???
        .foreach(println)
    } finally {
      source.close()
    }
  }
}
