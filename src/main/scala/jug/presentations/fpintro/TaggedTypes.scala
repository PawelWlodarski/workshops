package jug.presentations.fpintro

object TaggedTypes {

  type Tagged[U] = { type Tag = U }
  type @@[T, U] = T with Tagged[U]

  object Tag {
    def unwrap[A,B](a: @@[A,B]):A = a.asInstanceOf[A]
    def apply[A,B](a:A) : @@[A,B] = a.asInstanceOf[@@[A,B]]
  }

  sealed trait PriceTag
  type Price = BigDecimal @@ PriceTag

  object Price {
    def newPrice(p: BigDecimal): Price =
      Tag[BigDecimal, PriceTag](p)
    def lowerThan(a: Price, b: Price): Boolean =
      Tag.unwrap(a) < Tag.unwrap(b)
  }


  def main(args: Array[String]): Unit = {
      println("tagged")
    val p1:Price = Price.newPrice(BigDecimal(0))
    val p2:Price = Price.newPrice(BigDecimal(1))
    val p3:Price = Price.newPrice(BigDecimal("20"))

    arrays(p1,p2,p3)
  }

  def singlePrice() = {
    val p1:Price = Price.newPrice(BigDecimal(0))
    println(p1)
  }

  def arrays(p1:Price,p2:Price,p3:Price): Array[Price] = {
    val prices: Array[Price] =Array(p1,p2,p3)
    prices
  }



}
