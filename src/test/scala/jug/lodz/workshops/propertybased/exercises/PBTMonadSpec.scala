package jug.lodz.workshops.propertybased.exercises


import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

/**
  * Created by pawel on 06.03.16.
  */
class PBTMonadSpec extends PropSpec with Matchers with PropertyChecks{

  val split:String=>List[String] = s=>s.split(" ").toList
  val variants:String=>List[String] = s=>List(s,s.toUpperCase,s.toLowerCase)

  property("List satisfies left identity law : M.unit(a).flatMap(f) == f(a)"){
    forAll{(s:String)=>
        List(s).flatMap(split) shouldBe(split(s))
    }
  }

  property("List satisfies right identity law : M.flatMap(M.unit) == M"){
    forAll{(ls:List[String])=>
      ls.flatMap(a=>List(a)) shouldBe(ls)
    }
  }


  property("List satisfies Associativity law : M.flatMap(f1).flatMap(f2) == M.flatMap(x=>f1(x).flatMap(f2))"){
    forAll{(ls:List[String])=>
      ls.flatMap(split).flatMap(variants) shouldBe(ls.flatMap(string=>split(string).flatMap(variants)))
    }
  }

  //EXERCISE
  import jug.lodz.workshops.propertybased.PBTDomain._
  val purchaseWithId7=Purchase(7,List(
    Product("tv",Price(BigDecimal(300)))
  ))
  val purchases:Map[Int,Purchase] = Map(7 -> purchaseWithId7)
  val findPurchase : Map[Int,Purchase] => Int => Option[Purchase] = m => id => m.get(id)

  val largest : List[Int] => Option[Int] = l => if(l.isEmpty) None else Some(l.max)
  val findPurchaseInMockDB: (Int) => Option[Purchase] =findPurchase(purchases)

  //use in associativity law test
  val optionalListGenerator: Gen[Option[List[Int]]] =for{
    id <- Gen.oneOf(1,3,7)
    ids <-Gen.nonEmptyListOf(id)
    optionOfIds <- Gen.option(ids)
  } yield optionOfIds

  property("Option satisfies left identity law : M.unit(a).flatMap(f) == f(a)"){

  }

  property("Option satisfies right identity law : M.flatMap(M.unit) == M"){

  }


  property("Option satisfies Associativity law : M.flatMap(f1).flatMap(f2) == M.flatMap(x=>f1(x).flatMap(f2))"){

  }


}
