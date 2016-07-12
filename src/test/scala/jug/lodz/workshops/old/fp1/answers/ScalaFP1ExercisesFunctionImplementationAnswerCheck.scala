package jug.lodz.workshops.old.fp1.answers

import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 20.02.16.
  */
class ScalaFP1ExercisesFunctionImplementationAnswerCheck extends PropSpec with Matchers with PropertyChecks{

  import ScalaFP1ExercisesFunctionImplementationAnswer._

  property("Full Function of function f(x)=x+1 should return the same result as standard implementation"){
    forAll{(x:Int) =>
      addOneToXFull(x) shouldBe(addOneToX(x))
    }
  }

  property("Short Form of Function f(x)=x+1 should return the same result as standard implementation"){
    forAll{(x:Int) =>
      addOneToXShort(x) shouldBe(addOneToX(x))
    }
  }

  property("Full Function of function f(x)=x*2 should return the same result as standard implementation"){
    forAll{(x:Int) =>
      multiplyXByTwoFull(x) shouldBe(multiplyXByTwo(x))
    }
  }

  property("Short Form of Function f(x)=x*2 should return the same result as standard implementation"){
    forAll{(x:Int) =>
      multiplyXByTwoShort(x) shouldBe(multiplyXByTwo(x))
    }
  }

}
