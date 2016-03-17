package jug.lodz.workshops.fp2.answers

import scala.io.{BufferedSource, Source}

/**
  * Created by pwlodarski on 2016-03-15.
  */
object FP2LoanPattern5Answer {

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
    println("------------ADDITIONAL--------------")
    val processingResult: List[String] = readFileCurried("/fpjava/purchases.csv")(processContent(generatedCsvPredicate))
    processingResult.foreach(println)

    println("------------ADDITIONAL 2--------------")
    readFileCurried("/fpjava/purchases.csv"){lines=>
      processContent(generatedCsvPredicateForTv)(lines)
    }.foreach(println)

  }

  //Exercise
  def readFile[B](filePath:String)(process:List[String]=>B): Unit = {
    val source: BufferedSource = Source.fromURL(getClass().getResource(filePath))
    try {
      val lines=source.getLines().toList
      process(lines)
    } finally {
      source.close()
    }
  }

  //ADDITIONAL
  type Predicate=String=>Boolean
  val processContent:Predicate=>List[String]=>List[String]= p=>
    lines => lines.filter(p)

  val readFileCurried: (String) => ((List[String]) => List[String]) => List[String] = path => (process:List[String]=>List[String]) =>{
    val source: BufferedSource = Source.fromURL(getClass().getResource(path))
    try {
      val lines=source.getLines().toList
      process(lines)
    } finally {
      source.close()
    }
  }

}
