package jug.lodz.workshops.fp2.answers

import scala.io.{BufferedSource, Source}
import scala.util.Try

/**
  * Created by pawel on 20.03.16.
  */
object FP2ReduceList7Answer {
  val predicateForField : (Int,String) => String => Boolean =
    (index,expectValue)=>line=>line.split(",")(index)==expectValue

  val whenUser1 = predicateForField(0,"user1")
  val generatedCsvPredicateForTv = predicateForField(1,"tv")
  val generatedCsvPredicateForDate = predicateForField(3,"02-02-2016")

  type FilePath=String
  type Text=String
  type Predicate= Text=>Boolean
  type Mapping[A]=List[Text]=>List[A]

  lazy val extractPrice:Text => Text = line => line.split(",")(2)
  lazy val lift: (Text=>Text) => (List[Text]=>List[Text]) = f => list => list.map(f)

  object FunctionalLibrary{
    def liftGeneric[A,B](f:A=>B):List[A]=>List[B] = _ map f
    def liftOption[A,B](f:A=>B):Option[A]=>Option[B] = option=>option.map(f)
    def liftTry[A,B](f:A=>B):Try[A]=>Try[B] = tryInput=>tryInput.map(f)
  }
  import FunctionalLibrary._


  val processContent:Predicate=>Mapping[Text]= p => lines => lines.filter(p)

  val readFileCurried: (FilePath) => (Mapping[Text]) => List[String] = path => (process:Mapping[Text]) =>{
    val source: BufferedSource = Source.fromURL(getClass().getResource(path))
    try {
      val lines=source.getLines().toList
      process(lines)
    } finally {
      source.close()
    }
  }

  //Demonstration - pattern matching
  def secondElement[A](l:List[A])= l match {
    case first::second::tail => second
    case _ => throw new RuntimeException("list has less than two elements!")
  }

  def external(prefix:String, suffix:String) :String = {
    def internal(middle:String) = prefix+middle+suffix

    internal("internal method exercise")
  }

  //Exercise
  //val mapToInt:List[String] => List[Int]
  val mapToInt:Mapping[Int] = liftGeneric(field=>field.toInt)

  def reduce(combine:(Int,Int)=>Int)(l:List[Int]) = {
    def reduceInternal(acc:Int,l:List[Int]):Int=l match {
      case head::tail =>
        val newAcc=combine(acc,head)
        reduceInternal(newAcc,tail)
      case _ => acc
    }

    reduceInternal(l.head,l.tail)
  }

  //ADDITIONAL
  def fold[A,B](zero:B)(l:List[A])(f:(B,A)=>B):B=l match {
    case head::tail => fold(f(zero,head))(tail)(f)
    case _ => zero
  }

  //Demonstration
  def main(args: Array[String]) {

    println("------------DEMONSTRATION--------------")
    println("pattern matching")
    println("second of List(1,2,3,4,5): "+secondElement(List(1,2,3,4,5)))
    println("""second of List("aaa","bbb","ccc")"""+secondElement(List("aaa","bbb","ccc")))

    println("internal methods")
    println(external("[ "," ]"))

    println("------------EXERCISES--------------")
    val extractPrices: (List[Text]) => List[Text] =lift(extractPrice)
    val program: (List[Text]) => List[Text] = processContent(whenUser1).andThen(extractPrices)

    val processingResult: List[Text] = readFileCurried("/fpjava/purchases.csv")(program)
    val addAll: (List[Int]) => Int = reduce((element1, element2)=>element1+element2)
    val sumPrices: (List[Text]) => Int =mapToInt andThen addAll
    println("sum of all prices for user 1: "+sumPrices(processingResult)) //5525

    println("------------ADDITIONAL--------------")
    println(fold(0)(List(1,2,3,4,5))(_+_) == 15)
    println(fold(0)(List("aa","bbb","cccc"))(_ + _.length) == 9)
    println(fold("")(List("aa","bbb","cccc"))(_ + ":" + _) == ":aa:bbb:cccc")
  }
}
