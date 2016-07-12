package jug.lodz.workshops.old.fp1.answers

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 21.02.16.
  */
class ScalaFP1ReferentialTransparencyAnswerCheck extends PropSpec with Matchers with PropertyChecks{

  import jug.lodz.workshops.old.fp1.exercises.ScalaFP1ReferentialTransparency._

  val smallInt=Gen.choose(-100,100)

  property("pure function should be referentially transparent"){
    forAll(smallInt){i=>
      val r=pure(i)
      val values=(1 to 100).map(_=>pure(i))

      values.forall(_==r) shouldBe(true)
    }
  }
}
