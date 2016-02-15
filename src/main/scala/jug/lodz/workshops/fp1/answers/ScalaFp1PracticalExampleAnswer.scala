package jug.lodz.workshops.fp1.answers

/**
  * Created by pawel on 09.02.16.
  */
object ScalaFp1PracticalExampleAnswer {

  private val products:Map[String,Product]=Map(
    "tv"->Product("tv",BigDecimal("3000")),
    "keyboard"->Product("keyboard",BigDecimal("120")),
    "mouse"->Product("mouse",BigDecimal("70")),
    "headphones"->Product("head phones",BigDecimal("200"))

  )

  private val database:Map[PurchaseId,Purchase] =Map(
      PurchaseId(1) -> Purchase(PurchaseId(1),List(products("tv"),products("headphones"))),
      PurchaseId(1) -> Purchase(PurchaseId(1),List(products("keyboard"),products("headphones"),products("mouse")))
  )

  //lab
  def findPurchase(id:PurchaseId):Option[Purchase] = database.get(id)

  val domainFunction: Purchase => Seq[BigDecimal] = p=>p.purchasedProducts.map(p=>p.price)

  def main(args: Array[String]) {
    import MathLib._
    val pureDomainFunction: (Purchase) => BigDecimal =domainFunction andThen genericMathFunction

    findPurchase(new PurchaseId(1)).map(pureDomainFunction).foreach(println)
    val purchase3Price: BigDecimal =findPurchase(new PurchaseId(3)).map(pureDomainFunction)
      .getOrElse(throw new RuntimeException("there is no purchase with id 3"))

    println(s"somehow there is a purchase with id 3 : $purchase3Price")
  }

}

object MathLib {
  val genericMathFunction:Seq[BigDecimal]=>BigDecimal = bs=>bs.fold(BigDecimal(0))((a,b) => a + b)
    //bs=>bs.reduce((a,b)=>a+b) -- throws exception in scalacheck
}

case class PurchaseId(val id:Int) extends AnyVal

case class Product(name:String,price: BigDecimal)
case class Purchase(id:PurchaseId,purchasedProducts:List[Product])
