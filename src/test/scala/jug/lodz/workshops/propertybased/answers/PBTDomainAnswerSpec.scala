package jug.lodz.workshops.propertybased.answers

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 05.03.16.
  */
class PBTDomainAnswerSpec extends PropSpec with Matchers with PropertyChecks{

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
  val productGenerator = for{
    price <- priceGenerator
    name <- arbitrary[String].suchThat(_ .length > 0)
  } yield Product(name,price)

  property("product should have a name"){
    forAll(productGenerator){p=>
      p.name should not be empty
    }
  }

  val purchaseGenerator = for{
    products <- Gen.listOf(productGenerator).suchThat(_.length > 0)
    id <- Gen.choose(0,100)
  } yield Purchase(id,products)

  property("purchase should not have empty list of products"){
    forAll(purchaseGenerator){p=>
      p.products should not be empty
    }
  }

  //ADDITIONAL
  property("PurchaseService should add product to product list in an existing purchase"){
    forAll(purchaseGenerator,productGenerator){ (purchase,product) =>
      val newPurchase=PurchaseService.purchase(purchase)(product)

      newPurchase.products shouldBe(product::purchase.products)
    }
  }

}
