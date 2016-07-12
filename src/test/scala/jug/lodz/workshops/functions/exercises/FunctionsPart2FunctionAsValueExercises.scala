package jug.lodz.workshops.functions.exercises

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by pawel on 08.07.16.
  */
class FunctionsPart2FunctionAsValueExercises extends FunSuite with Matchers {


  //Don't change test - implement 'specificMap' method
  test("test specific map") {
    val elements = Seq("word", "longer", "sh")
    val length: String => Int = s => s.length // or _.length

    val result = specificMap(elements, length)

    result should contain theSameElementsAs Seq(4, 6, 2)
  }

  /**
    *
    * use for-llop
    *
    * for(element <- collection) action
    *
    */
  def specificMap(seq: Seq[String], f: String => Int): Seq[Int] = {
    //1) explain we are using mutable structure
    //2) encapsulation
    //3) keep this in exercises
    val buffer: ArrayBuffer[Int] = new ArrayBuffer(seq.size)

    //for (??? <- ???) buffer += ??? // += element  .append(element)

    buffer.toSeq
  }


  //don't change test - implement 'specificFilter' method
  test("specific filter") {
    val elements = Seq("word", "longer", "sh", "three")

    val result = specificFilter(elements, s => s.length > 4)

    result should contain theSameElementsAs Seq("longer", "three")
  }

  /**
    *
    * for (element <- collection) if(condition) action
    *
    */
  def specificFilter(seq: Seq[String], predicate: String => Boolean): Seq[String] = {
    val buffer: ArrayBuffer[String] = new ArrayBuffer() // no initial size

    /// for (??? <- ???) ???

    buffer.toSeq
  }


  // implement 'parseInt' and 'filter' so that usage of map and filter from standard library return proper result
  test("use build in map and filter") {
    val parseInt : String => Int = ???
    val square = (i:Int) => ???

    val mappingFunction: String => Int = parseInt andThen square

    val filteringFunction: Int => Boolean = _ > 10

    val result = List("1", "2", "3", "4", "5")
      .map(mappingFunction)
      .filter(filteringFunction)

    result should contain theSameElementsAs (List(16, 25))
  }

  // 'executeWithTwo' receives a function as a parameter and you need to invoke this function with parameter=2
  // similar thinking for 'reportExecution'
  test("a function receiving a function"){
    val executeWithTwo: (Int=>Int) => Int = ???
    val reportExecution: (Int => Int ) => String = ???

    // implement execute with two so the result of result2(x->x*5) is 10
    val result1: Integer = executeWithTwo.apply(x => x * 5)

    //implement report execution so that apply(x->x*5) is "RESULT : 10"
    val result2: String = reportExecution.apply(x => x * 5)

    result1 shouldBe 10
    result2 shouldBe "RESULT : 10"
  }


  //LEVEL2

  //don't change this test - implement 'genericMap' method
  test("implement generic map"){
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



  // use for-loop and arraybuffer
  def genericMap[A,B](seq: Seq[A], f: A => B): Seq[B] = {
   ???
  }

  //don't change this test - implement 'genericFilter' method
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

  // use for-loop and arraybuffer
  def genericFilter[A](seq: Seq[A], predicate: A => Boolean): Seq[A] = {
    ???
  }
}