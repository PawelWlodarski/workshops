package jug.lodz.workshops.fp2.exercises

import scala.io.{BufferedSource, Source}
import scala.util.Try

/**
  * Created by pawel on 20.03.16.
  */
object FP2MapReduce8 {

  object FunctionalLibrary{
    def liftGeneric[A,B](f:A=>B):List[A]=>List[B] = _ map f
    def liftOption[A,B](f:A=>B):Option[A]=>Option[B] = option=>option.map(f)
    def liftTry[A,B](f:A=>B):Try[A]=>Try[B] = tryInput=>tryInput.map(f)

    def fold[A,B](zero:B)(f:(B,A)=>B)(l:List[A]):B=l match {
      case head::tail => fold(f(zero,head))(f)(tail)
      case _ => zero
    }

    def reduce[A](combine:(A,A)=>A)(l:List[A]) = {
      def reduceInternal(acc:A,l:List[A]):A=l match {
        case head::tail =>
          val newAcc=combine(acc,head)
          reduceInternal(newAcc,tail)
        case _ => acc
      }

      reduceInternal(l.head,l.tail)
    }
  }
  import FunctionalLibrary._

  type Predicate= String=>Boolean


  val predicateForField : (Int,String) => String => Boolean =
    (index,expectValue)=>line=>line.split(",")(index)==expectValue

  val whenUser1 = predicateForField(0,"user1")
  val generatedCsvPredicateForTv = predicateForField(1,"tv")
  val generatedCsvPredicateForDate = predicateForField(3,"02-02-2016")

  val filterLines:Predicate=> (List[String] => List[String])= p => lines => lines.filter(p)

  //Exercise
  object MapReduce{
    def apply[A,B,C](fmap:A=>B)(reduce:List[B]=>C) : List[A] => C = ???
  }

  //MAP-REDUCE
  lazy val extractPrice:String => String = line => line.split(",")(2)
  val priceToInt =  extractPrice andThen (_.toInt)
  val sumOfPricesFromUser1  =  filterLines(whenUser1) andThen MapReduce(priceToInt)(reduce[Int](_+_))

  def processFile[C](path:String)(mapReduce:List[String]=>C):C={
    val source: BufferedSource = Source.fromURL(getClass().getResource(path))
    try {
      val lines=source.getLines().toList
      mapReduce(lines)
    } finally {
      source.close()
    }
  }

  //ADDITONAL

  lazy val extractProduct:String => String = ???

  val countOccurences: (List[String]) => Map[String, Int] = ???

  val occurencesOfProduct = MapReduce(extractProduct)(countOccurences)

  //Demonstration
  def main(args: Array[String]) {
      val result1 = processFile("/fpjava/purchases.csv")(sumOfPricesFromUser1)
      println("result1 : " + result1)

    val result2=processFile("/fpjava/purchases.csv"){lines=>
      val filtered = filterLines(whenUser1)(lines)
      MapReduce(priceToInt)(reduce[Int](_+_))(filtered)
    }
    println("result2 : "+result2)


//    println("-------------ADDITIONAL------------------")
//    println(processFile("/fpjava/purchases.csv")(occurencesOfProduct))
  }
}
