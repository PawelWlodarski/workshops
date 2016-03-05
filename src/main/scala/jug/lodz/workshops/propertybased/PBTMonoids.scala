package jug.lodz.workshops.propertybased

import algebra.Monoid

/**
  * Created by pawel on 06.03.16.
  */
object PBTMonoids {

  object IntAddMonoid extends Monoid[Int]{
    override def empty: Int = 0
    override def combine(x: Int, y: Int): Int = x+y
  }

  object IntMultiplyMonoid extends Monoid[Int]{
    override def empty: Int = 1
    override def combine(x: Int, y: Int): Int = x*y
  }

  import PBTDomain.Price

  object PriceMonoid extends Monoid[Price] {
    override def empty: Price = Price(BigDecimal(0))
    override def combine(x: Price, y: Price): Price = Price(x.value+y.value)
  }


  def reduce[A](seq:Seq[A])(implicit monoid:Monoid[A]): A ={
    seq.fold(monoid.empty)(monoid.combine)
  }

}
