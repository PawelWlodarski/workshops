package jug.lodz.workshops.starter.valueclasses.answers

class ItemId(val value: Int) extends AnyVal

class PurchaseId(val value: Int) extends AnyVal

class Item(val name: String, val description: String)

class Amount(val value: Int) extends AnyVal

class Purchase(val id: PurchaseId, val item: Item, val amount: Amount)

object Item {
  def apply(name: String, description: String) = new Item(name, description)
}

object Amount {
  def apply(value: Int) = new Amount(value)
}

object Purchase {
  def apply(id: Int, item: Item, amount: Amount): Purchase =
    new Purchase(new PurchaseId(id), item, amount)
}

object Database {
  private var data: Map[Int, Purchase] = Map(
    1 -> Purchase(1, Item("item1", "description1"), Amount(7)),
    2 -> Purchase(2, Item("item2", "description2"), Amount(4)),
    3 -> Purchase(3, Item("item3", "description3"), Amount(3)),
    4 -> Purchase(4, Item("item4", "description4"), Amount(2)),
    5 -> Purchase(5, Item("item5", "description5"), Amount(1))
  )

  def selectAll : List[(PurchaseId,Purchase)] =
    data.toList.map{
      case (id,p)=>(new PurchaseId(id),p)
    }
}

