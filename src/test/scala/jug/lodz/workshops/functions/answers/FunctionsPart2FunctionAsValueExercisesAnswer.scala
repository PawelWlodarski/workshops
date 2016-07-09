package jug.lodz.workshops.functions.answers

import org.scalatest.{Matchers, FunSuite}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by pawel on 08.07.16.
  */
class FunctionsPart2FunctionAsValueExercisesAnswer extends FunSuite with Matchers {


  test("test specific map") {
    val elements = Seq("word", "longer", "sh")
    val length: String => Int = s => s.length // or _.length

    val result = specificMap(elements, length)

    result should contain theSameElementsAs Seq(4, 6, 2)
  }

  def specificMap(seq: Seq[String], f: String => Int): Seq[Int] = {
    //1) explain we are using mutable structure
    //2) encapsulation
    //3) keep this in exercises
    val buffer: ArrayBuffer[Int] = new ArrayBuffer(seq.size)

    for (e <- seq) buffer += f(e) //.append(f(e))

    buffer.toSeq
  }


  test("specific filter") {
    val elements = Seq("word", "longer", "sh", "three")

    val result = specificFilter(elements, s => s.length > 4)

    result should contain theSameElementsAs Seq("longer", "three")
  }

  def specificFilter(seq: Seq[String], predicate: String => Boolean): Seq[String] = {
    val buffer: ArrayBuffer[String] = new ArrayBuffer() // no initial size

    for (e <- seq)
      if (predicate(e)) buffer += e

    buffer.toSeq
  }


  test("build in map and filter") {
    val parseInt : String => Int = s => s.toInt
    val square = (i:Int) => i*i

    val mappingFunction: String => Int = parseInt andThen square

    val filteringFunction: Int => Boolean = _ > 10

    val result = List("1", "2", "3", "4", "5")
      .map(mappingFunction)
      .filter(filteringFunction)

    result should contain theSameElementsAs (List(16, 25))
  }

  test("function reveiving function"){
    val executeWithTwo: (Int=>Int) => Int = f=>f(2)
    val reportExecution: (Int => Int ) => String = f=>"RESULT : "+f(2)

    // implement execute with two so the result of result2(x->x*5) is 10
    val result1: Integer = executeWithTwo.apply(x => x * 5)

    //implement report execution so that apply(x->x*5) is "RESULT : 10"
    val result2: String = reportExecution.apply(x => x * 5)

    result1 shouldBe 10
    result2 shouldBe "RESULT : 10"
  }


  //Level2
  test("generic map"){
    val transactions: List[(String, BigDecimal)] = List(
      ("t1", BigDecimal("20")),
      ("t2", BigDecimal("30")),
      ("t3", BigDecimal("60"))
    )

    val tax=BigDecimal("0.23")


    val netMoney: Seq[BigDecimal] =genericMap(transactions, (t:(String,BigDecimal))=>t._2)
    val grossMoney: Seq[BigDecimal] =genericMap(netMoney, (m:BigDecimal)=> m +  m * tax)

    grossMoney should contain theSameElementsAs Seq(BigDecimal("24.60"),BigDecimal("36.90"),BigDecimal("73.80"))
  }



  def genericMap[A,B](seq: Seq[A], f: A => B): Seq[B] = {
    val buffer: ArrayBuffer[B] = new ArrayBuffer(seq.size)

    for (e <- seq) buffer += f(e) //.append(f(e))

    buffer.toSeq
  }

  test("generic filter"){
    val transactions: List[(String, BigDecimal)] = List(
      ("t1", BigDecimal("20")),
      ("t2", BigDecimal("30")),
      ("t1", BigDecimal("60"))
    )

    val result=genericFilter(transactions, (t:(String,BigDecimal))=>t._1=="t1")

    result should contain theSameElementsAs Seq(
      ("t1", BigDecimal("20")),
      ("t1", BigDecimal("60"))
    )
  }

  def genericFilter[A](seq: Seq[A], predicate: A => Boolean): Seq[A] = {
    val buffer: ArrayBuffer[A] = new ArrayBuffer() // no initial size

    for (e <- seq)
      if (predicate(e)) buffer += e

    buffer.toSeq
  }
}