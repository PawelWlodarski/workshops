package jug.lodz.workshops.propertybased.answers

import jug.lodz.workshops.propertybased.PBTDomain.Price
import jug.lodz.workshops.propertybased.PBTMonoids._
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 06.03.16.
  */
class PBTMonoidAnswerSpec extends PropSpec with Matchers with PropertyChecks{


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
    forAll {(i: Int) =>
      import IntMultiplyMonoid._
      val neutral=IntMultiplyMonoid.empty
      combine(i,neutral) should (equal(i) and equal(combine(neutral,i)))
    }
  }

  property("IntMultiplyMonoid asociativity law"){
    forAll{(a:Int,b:Int,c:Int)=>
      import IntMultiplyMonoid._
      combine(combine(a,b),c) shouldBe(combine(a,combine(b,c)))
    }
  }

  //ADDITIONAL
  val priceGenerator=for {
    i <- Gen.choose(0,Int.MaxValue)
  } yield Price(BigDecimal(i))

  property("PriceMonoid identity law"){
    forAll(priceGenerator){p=>
      import PriceMonoid._
      val neutral=PriceMonoid.empty
      combine(p,neutral) should (equal(p) and equal(combine(neutral,p)))
    }
  }

  property("PriceMonoid asociativity law"){
    forAll(priceGenerator,priceGenerator,priceGenerator){(p1,p2,p3)=>
      import PriceMonoid._
      combine(combine(p1,p2),p3) shouldBe(combine(p1,combine(p2,p3)))
    }
  }

  property("reduce with Price Monoid should count sum of all prices"){
    forAll(Gen.listOfN(3,priceGenerator)){ps=>
      implicit val monoid=PriceMonoid
      reduce(ps) shouldBe(ps.reduce((p1,p2)=>Price(p1.value+p2.value)))
    }
  }
}
