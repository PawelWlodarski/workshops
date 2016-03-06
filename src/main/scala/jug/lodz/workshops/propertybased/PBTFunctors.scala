package jug.lodz.workshops.propertybased

import algebra.Monoid
import cats.Functor
import jug.lodz.workshops.propertybased.PBTDomain.{Price, Product}
import jug.lodz.workshops.propertybased.PBTMonoids.PriceMonoid

/**
  * Created by pawel on 06.03.16.
  */
object PBTFunctors {



  def reduce[A,B](seq:List[A])(f:A=>B)(implicit monoid:Monoid[B], functor:Functor[List]): B ={
    functor.map(seq)(f).fold(monoid.empty)(monoid.combine)
  }


//  def reduce2[A,B:Monoid, S[A] <: List[A] : Functor](seq:S[A])(f:A=>B): B ={
//    import cats.std.all._
//
//    val functor = implicitly[Functor[List]]
//    val monoid = implicitly[Monoid[B]]
//
//    functor.map(seq)(f).fold(monoid.empty)(monoid.combine)
//  }


  def main(args: Array[String]) {
    import cats.std.all._
    implicit val monoid=PriceMonoid

    val products= List(
      Product("tv",Price(BigDecimal(300))),
      Product("mouse",Price(BigDecimal(20)))
    )

    println(reduce(products)(p=>p.price))
//    println(reduce2(products)(p=>p.price))

  }
}
