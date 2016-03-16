package jug.lodz.workshops.fp2.exercises

import scala.io.{BufferedSource, Source}

/**
  * Created by pwlodarski on 2016-03-15.
  */
object FP2TestFunctions3 {

  val predicateForField : (Int,String) => String => Boolean =
    (index,expectValue)=>line=>line.split(",")(index)==expectValue

  val generatedCsvPredicate = predicateForField(0,"user1")
  val generatedCsvPredicateForTv = predicateForField(1,"tv")
  val generatedCsvPredicateForDate = predicateForField(3,"02-02-2016")

  def main(args: Array[String]) {

    //EXERCISE - Pass predicate
    println("-----------READING FILE------------")
    readFile("/fpjava/purchases.csv")(generatedCsvPredicate)
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
