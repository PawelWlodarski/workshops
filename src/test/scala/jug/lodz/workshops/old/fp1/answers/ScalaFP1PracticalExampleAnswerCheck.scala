package jug.lodz.workshops.old.fp1.answers

import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 15.02.16.
  */
class ScalaFP1PracticalExampleAnswerCheck extends PropSpec with Matchers with PropertyChecks{


  import jug.lodz.workshops.old.fp1.answers.ScalaFp1PracticalExampleAnswer.DomainLib._
  import jug.lodz.workshops.old.fp1.answers.ScalaFp1PracticalExampleAnswer.MathLib

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
  } yield Purchase(id,products)


  property("Math generic function should sum all decimals"){
    forAll(bigDecimalList){ (bs: List[BigDecimal]) =>
        MathLib.genericMathFunction(bs) shouldBe bs.sum
    }
  }


  property("domain function will extract all prices"){
    forAll(purchaseGen){ (purchase: Purchase) =>
      ScalaFp1PracticalExampleAnswer.domainFunction(purchase) shouldBe purchase.purchasedProducts.map(_.price)
    }
  }
}
