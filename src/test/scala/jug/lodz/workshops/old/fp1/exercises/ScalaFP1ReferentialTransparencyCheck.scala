package jug.lodz.workshops.old.fp1.exercises

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 21.02.16.
  */
class ScalaFP1ReferentialTransparencyCheck extends PropSpec with Matchers with PropertyChecks{

  val smallInt=Gen.choose(-100,100)

  import ScalaFP1ReferentialTransparency._

  property("pure function should be referentially transparent"){
    forAll(smallInt){i=>
      ???
    }
  }
}
