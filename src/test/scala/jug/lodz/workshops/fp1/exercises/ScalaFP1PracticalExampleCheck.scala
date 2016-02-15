package jug.lodz.workshops.fp1.exercises

import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 15.02.16.
  */
class ScalaFP1PracticalExampleCheck extends PropSpec with Matchers with PropertyChecks{

  //Math Generators
  val smallDecimals=Gen.choose(0,1000).map((v:Int) => BigDecimal(v))

  //  val bigDecimalList: Gen[List[BigDecimal]] =Gen.listOf(Arbitrary.arbitrary[BigDecimal])
  val bigDecimalList: Gen[List[BigDecimal]] =Gen.listOf(smallDecimals)

  //Domain Generators
  val productGenerator=for{
    name <- Arbitrary.arbitrary[String]
    price <- smallDecimals
  } yield Product(name,price)

  val productListGen=Gen.listOf(productGenerator)

  val purchaseGen= for{
    products <- productListGen
    id <- Arbitrary.arbitrary[Int]
  } yield Purchase(new PurchaseId(id),products)


  property("Math generic function should sum all nig decimals"){
    forAll(bigDecimalList){ (bs: List[BigDecimal]) =>
        MathLib.genericMathFunction(bs) shouldBe bs.sum
    }
  }


  property("domain function will extract all prices"){
    forAll(purchaseGen){ (purchase: Purchase) =>
      ScalaFp1PracticalExample.domainFunction(purchase) shouldBe purchase.purchasedProducts.map(_.price)
    }
  }
}
