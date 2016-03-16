package jug.lodz.workshops.fp2.answers

import jug.lodz.workshops.fp2.exercises.FP2TestFunctions3
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, FlatSpecLike, Matchers, PropSpec}

/**
  * Created by pwlodarski on 2016-03-15.
  */
class FP2TestFunction3AnswerSpec extends PropSpec with Matchers with PropertyChecks{

  private val fields: Gen[String] = Gen.oneOf("one","two","three","four")

  private val listOfValues: Gen[List[String]] = Gen.listOfN(4,fields).map(list => list.zipWithIndex.map{case (v,ind)=>s"$v$ind"})

  import FP2TestFunctions3._

  property("'predicateForField' should generate predicate for given field and value"){
    forAll(listOfValues){vs=>
      val value=vs.head
      val csvLine=vs.mkString(",")
      val predicate: (String) => Boolean = predicateForField(0,value)
      predicate(csvLine) shouldBe(true)
    }
  }



}

class FP2TestFunction3AnswerUnitTests extends FlatSpec with Matchers{
  import FP2TestFunctions3._

  "Produced Predicate" should "check proper field with proper value" in {
    val line="first,second,third,fourth"

    val predicate: (String) => Boolean = predicateForField(0,"first")

    predicate(line) shouldBe(true)
  }

}
