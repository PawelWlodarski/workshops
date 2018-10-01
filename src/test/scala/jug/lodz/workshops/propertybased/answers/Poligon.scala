package jug.lodz.workshops.propertybased.answers

import org.scalacheck.Gen
import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.PropertyChecks

class Poligon extends PropSpec with Matchers with PropertyChecks{

  val ints=Gen.chooseNum(minT = Int.MinValue, maxT = Int.MaxValue)


  val neg : Int => Int = Math.negateExact

  property(" For every i from {Int}  neg(i) == -i "){
    forAll(ints){ i =>
      neg(i) == i
    }
  }

}