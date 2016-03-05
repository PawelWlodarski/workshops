package jug.lodz.workshops.propertybased.exercises

import jug.lodz.workshops.propertybased.PBTMonoids.IntAddMonoid
import org.scalatest.{PropSpec, Matchers}
import org.scalatest.prop.PropertyChecks

/**
  * Created by pawel on 06.03.16.
  */
class PBTMonoidSpec extends PropSpec with Matchers with PropertyChecks{


  property("IntAddMonoid identity law"){
    forAll {(i: Int) =>
      import IntAddMonoid._
      val neutral=IntAddMonoid.empty
      combine(i,neutral) should (equal(i) and equal(combine(neutral,i)))
    }
  }

  property("IntAddMonoid asociativity law"){
    forAll{(a:Int,b:Int,c:Int)=>
      import IntAddMonoid._
      combine(combine(a,b),c) shouldBe(combine(a,combine(b,c)))
    }
  }

  //EXERCISES
  property("IntMultiplyMonoid identity law"){

  }

  property("IntMultiplyMonoid asociativity law"){

  }

  //ADDITIONAL
  property("PriceMonoid identity law"){

  }

  property("PriceMonoid asociativity law"){

  }

  property("reduce with Price Monoid should count sum of all prices"){

  }
}
