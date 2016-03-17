package jug.lodz.workshops.fp2.answers

import cats.Functor

import scala.io.{BufferedSource, Source}
import scala.util.Try

/**
  * Created by pwlodarski on 2016-03-15.
  */
object FP2MapList6Answer {

  val predicateForField : (Int,String) => String => Boolean =
    (index,expectValue)=>line=>line.split(",")(index)==expectValue

  val generatedCsvPredicate = predicateForField(0,"user1")
  val generatedCsvPredicateForTv = predicateForField(1,"tv")
  val generatedCsvPredicateForDate = predicateForField(3,"02-02-2016")

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
  lazy val extractPrice:Text => Text = line => line.split(",")(2)

  lazy val lift: (Text=>Text) => (List[Text]=>List[Text]) = f => list => list.map(f)

  //ADDITIONAL
  def liftGeneric[A,B](f:A=>B):List[A]=>List[B] = _ map f

  def liftOption[A,B](f:A=>B):Option[A]=>Option[B] = option=>option.map(f)

  def liftTry[A,B](f:A=>B):Try[A]=>Try[B] = tryInput=>tryInput.map(f)

  //ADDITIONAL HARDCORE
  import cats.std.all._
  implicit val listFunctor:Functor[List]=Functor[List]
  implicit val optionFunctor:Functor[Option]=Functor[Option]

  def liftSomething[A,B,F[_]](f:A=>B)(implicit functor:Functor[F]): F[A] => F[B] =
    (input:F[A]) => functor.map(input)(f)



  //Demonstration
  def main(args: Array[String]) {

    println("------------DEMONSTRATION--------------")
    val extractPrices=lift(extractPrice)
    val program: (List[String]) => List[String] = processContent(generatedCsvPredicate).andThen(extractPrices)

    val processingResult: List[String] = readFileCurried("/fpjava/purchases.csv")(program)
    processingResult.foreach(println)

    println("-----------Exercise---------------------------------")
    println(extractPrice("user1,tv,100")=="100")
    println(extractPrice("user2,console,200")=="200")

    println(lift(extractPrice)(List("user1,tv,100","user2,console,200"))==List("100","200"))

    println("-----------Additional---------------------------------")
    println(liftGeneric(extractPrice)(List("user1,tv,100","user2,console,200"))==List("100","200"))
    println(liftOption(extractPrice)(Some("user1,tv,100"))==Some("100"))
    println(liftTry(extractPrice)(Try("user1,tv,100"))==Try("100"))

    println("-----------Additional Hardcore---------------------------------")
    val lifted: (List[Text]) => List[Text] = liftSomething[Text,Text,List](extractPrice)
    println(lifted(List("user1,tv,100","user2,console,200"))==List("100","200"))

    val liftedOption: (Option[Text]) => Option[Text] =liftSomething[Text,Text,Option](extractPrice)

    println(liftedOption(Some("user1,tv,100"))==Some("100"))


  }



}
