package jug.lodz.workshops.starter.valueclasses.answers

class ItemId(val value: Int) extends AnyVal

class PurchaseId(val value: Int) extends AnyVal

class Item(val id:ItemId,val name: String, val description: String)

class Amount(val value: Int) extends AnyVal

class Purchase(val id: PurchaseId, val item: Item, val amount: Amount)

object Item {
  def apply(id:Int,name: String, description: String) = new Item(new ItemId(id),name, description)
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
    1 -> Purchase(1, Item(1,"item1", "description1"), Amount(7)),
    2 -> Purchase(2, Item(2,"item2", "description2"), Amount(4)),
    3 -> Purchase(3, Item(3,"item3", "description3"), Amount(3)),
    4 -> Purchase(4, Item(4,"item4", "description4"), Amount(2)),
    5 -> Purchase(5, Item(3,"item3", "description5"), Amount(1))
  )

  def selectAll : List[(Int,Purchase)] = data.toList
  def find(id:Int):Option[Purchase] = data.get(id)
}


class SafeDao {

  private val database=Database

  def findPurchase(pid:PurchaseId):Option[Purchase] = {
    database.find(pid.value)
  }

  def findPurchaseWithItem(iid:ItemId) : List[Purchase] = {
    database.selectAll
      .map{ case (_, p) => p}
      .filter(p=> p.item.id == iid)
  }

}

