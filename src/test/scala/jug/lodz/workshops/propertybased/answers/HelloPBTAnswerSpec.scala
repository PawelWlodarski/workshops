package jug.lodz.workshops.propertybased.answers

import jug.lodz.workshops.propertybased.HelloPBT.HelloPBTExercises.Kelvin
import jug.lodz.workshops.propertybased.HelloPBT.MathLib
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 05.03.16.
  */
class HelloPBTAnswerSpec extends PropSpec with Matchers with PropertyChecks{

  val natural: Gen[Int] =Gen.choose(0,1000)

  property("for every n>0 where n is Natural SUM(1,2...n)=n*(n+1)/2"){
      forAll(natural){(n:Int)=>
        println(n)
        MathLib.sumN(n) shouldBe ((1L to n).sum)
      }
  }

  val kelvinGenerator:Gen[Kelvin] = Gen.choose(0, Int.MaxValue)

  import jug.lodz.workshops.propertybased.HelloPBT.HelloPBTExercises._
  property("(kelvin to celsius to fahrenheit to kelvin) should give first value"){
    val testFunction=kelvinToCelsius andThen celsiusToFahrenheit andThen fahrenheitToKelvin
    forAll(kelvinGenerator){k:Kelvin=>
        testFunction(k) shouldBe(k +- 0.0001)
    }
  }

  val notEmptyString = Arbitrary.arbitrary[String].suchThat(_.nonEmpty)

  property("for every pair of non empty strings (s1.length + s2.length) > s1.length "){
    forAll(notEmptyString,notEmptyString){(s1:String, s2:String) =>
      (s1.length + s2.length) should be > s1.length
    }
  }
}
