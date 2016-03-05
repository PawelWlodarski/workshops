package jug.lodz.workshops.propertybased.exercises

import jug.lodz.workshops.propertybased.HelloPBT.MathLib
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 05.03.16.
  */
class HelloPBTSpec extends PropSpec with Matchers with PropertyChecks{

  property("for every n>0 where n is Natural SUM(1,2...n)=n*(n+1)/2"){
      forAll{(n:Int)=>
        println(n)
        MathLib.sumN(n) shouldBe ((1L to n).sum)
      }
  }

  import jug.lodz.workshops.propertybased.HelloPBT.HelloPBTExercises._
  property("(kelvin to celsius to fahrenheit to kelvin) should give first value"){
    //mather for comparison within some range "shouldBe(k +- 0.0001)"
  }

  //ADDITIONAL
  property("for every pair of non empty strings (s1.length + s2.length) > s1.length "){
    //matcher for greater "x should be > s1.length"
  }

}
