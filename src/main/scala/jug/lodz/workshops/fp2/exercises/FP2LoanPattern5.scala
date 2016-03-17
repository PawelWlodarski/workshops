package jug.lodz.workshops.fp2.exercises

import scala.io.{BufferedSource, Source}

/**
  * Created by pwlodarski on 2016-03-15.
  */
object FP2LoanPattern5 {

  val predicateForField : (Int,String) => String => Boolean =
    (index,expectValue)=>line=>line.split(",")(index)==expectValue

  val generatedCsvPredicate = predicateForField(0,"user1")
  val generatedCsvPredicateForTv = predicateForField(1,"tv")
  val generatedCsvPredicateForDate = predicateForField(3,"02-02-2016")

  def main(args: Array[String]) {

    println("-----------READING FILE------------")
    readFile("/fpjava/purchases.csv"){lines=>
      lines.filter(generatedCsvPredicate).foreach(println)
    }

    //ADDITIONAL
//    println("------------ADDITIONAL--------------")
//    val processingResult: List[String] = readFileCurried("/fpjava/purchases.csv")(processContent(generatedCsvPredicate))
//    processingResult.foreach(println)
//
//    println("------------ADDITIONAL 2--------------")
//    readFileCurried("/fpjava/purchases.csv"){lines=>
//      processContent(generatedCsvPredicateForTv)(lines)
//    }.foreach(println)

  }

  //Exercise
  def readFile[B](filePath:String)(process:List[String]=>B): Unit = ???

  //ADDITIONAL
  type Predicate=String=>Boolean
  lazy val processContent:Predicate=>List[String]=>List[String]= ???

  val readFileCurried: (String) => ((List[String]) => List[String]) => List[String] = ???

}
