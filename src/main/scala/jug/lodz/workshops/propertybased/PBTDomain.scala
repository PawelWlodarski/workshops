package jug.lodz.workshops.propertybased

/**
  * Created by pawel on 05.03.16.
  */
object PBTDomain {
  case class Price(value:BigDecimal)
  case class Product(name:String, price:Price)
  case class Purchase(id:Int,products:List[Product])


  trait PurchaseService{
    def purchase(purchase:Purchase)(p:Product) : Purchase = Purchase(purchase.id, p :: purchase.products)
  }

  object PurchaseService extends PurchaseService

}
