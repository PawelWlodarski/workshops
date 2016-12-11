package jug.lodz.workshops.fp2.exercises

import scala.io.{BufferedSource, Source}
import scala.util.Try

/**
  * Created by pwlodarski on 2016-03-15.
  */
object FP2MapList6 {

  val predicateForField : (Int,String) => String => Boolean =
    (index,expectValue)=>line=>line.split(",")(index)==expectValue

  val generatedCsvPredicate = predicateForField(0,"user1")
  val generatedCsvPredicateForTv = predicateForField(1,"tv")
  val generatedCsvPredicateForDate = predicateForField(3,"02-02-2016")

  //Aliases
  type FilePath=String
  type Text=String
  type Predicate= Text=>Boolean
  type Transformation=List[Text]=>List[Text]

  val processContent:Predicate=>Transformation= p => lines => lines.filter(p)

  val readFileCurried: (FilePath) => (Transformation) => List[String] = path => (process:Transformation) =>{
    val source: BufferedSource = Source.fromURL(getClass().getResource(path))
    try {
      val lines=source.getLines().toList
      process(lines)
    } finally {
      source.close()
    }
  }

  //Exercise
  lazy val extractPrice:Text => Text = ???

  lazy val lift: (Text=>Text) => (List[Text]=>List[Text]) = ???

  //ADDITIONAL
  def liftGeneric[A,B](f:A=>B):List[A]=>List[B] = ???

  def liftOption[A,B](f:A=>B):Option[A]=>Option[B] = ???

  def liftTry[A,B](f:A=>B):Try[A]=>Try[B] = ???

  //ADDITIONAL HARDCORE
  import cats._
  implicit val listFunctor:Functor[List]=Functor[List]
  implicit val optionFunctor:Functor[Option]=Functor[Option]

  def liftFunctor[A,B,F[_]](f:A=>B)(implicit functor:Functor[F]): F[A] => F[B] = ???



  //Demonstration
  def main(args: Array[String]) {

    println("------------DEMONSTRATION--------------")
    val extractPrices: (List[Text]) => List[Text] =lift(extractPrice)
    val program: (List[Text]) => List[Text] = processContent(generatedCsvPredicate).andThen(extractPrices)

    val processingResult: List[Text] = readFileCurried("/fpjava/purchases.csv")(program)
    processingResult.foreach(println)

    println("-----------Exercise---------------------------------")
//    println(extractPrice("user1,tv,100")=="100")
//    println(extractPrice("user2,console,200")=="200")
//
//    println(lift(extractPrice)(List("user1,tv,100","user2,console,200"))==List("100","200"))
//
//    println("-----------Additional---------------------------------")
//    println(liftGeneric(extractPrice)(List("user1,tv,100","user2,console,200"))==List("100","200"))
//    println(liftOption(extractPrice)(Some("user1,tv,100"))==Some("100"))
//    println(liftTry(extractPrice)(Try("user1,tv,100"))==Try("100"))
//
//    println("-----------Additional Hardcore---------------------------------")
//    val lifted: (List[Text]) => List[Text] = liftFunctor[Text,Text,List](extractPrice)
//    println(lifted(List("user1,tv,100","user2,console,200"))==List("100","200"))
//
//    val liftedOption: (Option[Text]) => Option[Text] =liftFunctor[Text,Text,Option](extractPrice)
//
//    println(liftedOption(Some("user1,tv,100"))==Some("100"))


  }



}
