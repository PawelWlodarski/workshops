package jug.lodz.workshops

/**
  * Created by pawel on 15.05.16.
  */
object Workshops {

  def check[A](label:String)(actual:A ,expected:A):Unit = actual match {
    case `expected` =>println(s"  * in $label value is correct : $expected")
    case _=>   println(s"  * in $label value is NOT CORRECT! expected : $expected but actual is $actual")
  }



}
