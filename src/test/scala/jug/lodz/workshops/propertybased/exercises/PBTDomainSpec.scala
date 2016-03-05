package jug.lodz.workshops.propertybased.exercises

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 05.03.16.
  */
class PBTDomainSpec extends PropSpec with Matchers with PropertyChecks{

  import org.scalacheck.Arbitrary._
  import jug.lodz.workshops.propertybased.PBTDomain._

  val priceGenerator=for {
    i <- Gen.choose(0,Int.MaxValue)
  } yield Price(BigDecimal(i))

//  val priceGenerator2=arbitrary[Int].suchThat(_ >0 ).map(BigDecimal(_)).map(Price(_))

  property("price should be larger than 0"){
    forAll(priceGenerator){p=>
      p.value should be > BigDecimal(0)
    }
  }

  //Exercise
  property("product should have a name"){

  }

  property("purchase should not have empty list of products"){

  }

  //ADDITIONAL
  property("PurchaseService should add product to product list in an existing purchase"){

  }

}
