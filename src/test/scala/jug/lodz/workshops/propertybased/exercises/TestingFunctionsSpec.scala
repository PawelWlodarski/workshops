package jug.lodz.workshops.propertybased.exercises

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 05.03.16.
  */
class TestingFunctionsSpec extends PropSpec with Matchers with PropertyChecks {

  //DEMONSTRATION - GENERATING VALUES
  val simpleGenerator: Gen[Int] =Gen.oneOf(1,3,5,7)
  val composedGenerator: Gen[Int] =Gen.oneOf(Gen.choose(0,100),Gen.choose(100,200))

  val pairsGenerator: Gen[(Int, Int)] = for{
    i1 <- simpleGenerator
    i2 <- composedGenerator
  } yield (i1,i2)


  property("generators should properly generate pair of integers"){
      forAll(pairsGenerator){case (i1,i2) =>
        Set(1,3,5,7) should contain(i1)
        i2 should (be >= 0 and be <= 200)
      }
  }

  val intTointFunctions:Gen[Int=>Int]=Gen.oneOf((x:Int)=>x+1,(x:Int)=>x*2,(x:Int)=>x%10)

  property("generator should generate a funcion"){
    forAll(intTointFunctions){ f:(Int=>Int) =>
      f shouldBe a [Function1[_,_]]
    }
  }

  val stringToIntFunctions:Gen[String=>Int]=Gen.oneOf((s:String)=>s.toInt,(s:String)=>s.length)


  //EXERCISES
  //MANDATORY
  property("for every two functions f1 : String=> Int and f2 : Int=>Int , f1 andThen f2 should be equal to f2(f1(x))"){

  }


  property("for every two functions f1 : Int=> Int and f2 : String=>Int , f1 compose f2 should be equal to f1(f2(x))"){

  }

  //ADDITIONAL
  property("for every f:(Int,Int)=> Int , f.curry should generate equal function f` : Int=>Int=>Int"){

  }
}
