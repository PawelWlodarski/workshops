package jug.lodz.workshops.fp2.answers

import scala.io.{BufferedSource, Source}

/**
  * Created by pwlodarski on 2016-03-15.
  */
object FP2ConfigureFiltering2Answer {

  def demonstration(l:List[Int],predicate:Int=>Boolean):List[Int]= l.filter(predicate)

  def demonstration2(l:List[Int])(predicate:Int=>Boolean):List[Int]= l.filter(predicate)

  def demonstration3(predicate:Int=>Boolean)(l:List[Int]):List[Int]= l.filter(predicate)

  //exercise
  val user1Predicate:String => Boolean = line=>line.split(",")(0)=="user1"

  //additional
  val predicateForField : (Int,String) => String => Boolean =
    (index,expectValue)=>line=>line.split(",")(index)==expectValue

  val generatedCsvPredicate = predicateForField(0,"user1")
  val generatedCsvPredicateForTv = predicateForField(1,"tv")
  val generatedCsvPredicateForDate = predicateForField(3,"02-02-2016")

  def main(args: Array[String]) {
    //PREPARATION
    println(demonstration(List(1,2,3,4,5,6,7,8,9,10),e=>e>7)==List(8,9,10))
    println(demonstration(List(1,2,3,4,5,6,7,8,9,10),_>7)==List(8,9,10))
    println(demonstration(List(1,2,3,4,5,6,7,8,9,10),e=>e>5)==List(6,7,8,9,10))

    println(demonstration2(List(1,2,3,4,5))(e=>e>3)==List(4,5))

    val preparedFilter = demonstration3(e=>e>5)_

    println(preparedFilter(List(3,4,5,6,7,8))==List(6,7,8))

    //EXERCISE - Pass predicate
//    println("-----------READING FILE------------")
//    readFile("/fpjava/purchases.csv")(user1Predicate)
//    readFile("/fpjava/purchases.csv")(generatedCsvPredicateForDate)

  }

  def readFile(filePath:String)(p:String=>Boolean): Unit = {
    val source: BufferedSource = Source.fromURL(getClass().getResource(filePath))
    try {
      source.getLines()
        .filter(p)
        .foreach(println)
    } finally {
      source.close()
    }
  }
}
