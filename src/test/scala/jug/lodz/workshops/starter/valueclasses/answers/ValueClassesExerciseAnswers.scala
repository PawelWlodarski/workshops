package jug.lodz.workshops.starter.valueclasses.answers

import org.scalatest.{MustMatchers, WordSpec}

import scala.collection.immutable.Seq

class ValueClassesExerciseAnswers extends WordSpec with MustMatchers {

  "Define domain objects factory" should {
    "should be defined for Item" in {
      val item: Item = Item(1, "name", "description")

      item.name mustBe "name"
      item.description mustBe "description"
    }

    "should be defined for Amount" in {
      val amount: Amount = Amount(1)

      amount.value mustBe 1
    }

    "should be defined for Purchase" in {
      val p: Purchase = Purchase(1, Item(1, "item1", "desc1"), Amount(11))

      p.id mustBe a[PurchaseId]
      p.item.name mustBe "item1"
    }
  }

  "Database" should {
    "be initiated" in {
      val ids = Database.selectAll.map { case (id, _) => id }
      val names = Database.selectAll.map { case (_, purchase) => purchase.item.name }

      ids must contain allOf(1, 2, 3, 4)
      names must contain allOf("item1", "item2", "item3", "item4")
    }
  }

  "Safe Dao" should {
    "find purchase" in {
      val dao = new SafeDao
      val pid = new PurchaseId(1)
      val result: Option[Purchase] = dao.findPurchase(pid)

      result must not be empty
      result.get.id.value mustBe 1
      result.get.amount.value mustBe 7
    }

    "find all purchases with item" in {
      val dao = new SafeDao
      val iid = new ItemId(3)

      val result: Seq[Purchase] = dao.findPurchaseWithItem(iid)

      result.size mustBe 2

    }
  }

  "Dao with cache" should {
    "store responses in cache " in {
      val dao : SafeDao = new SafeDao with DataCache

      val p1 = dao.findPurchase(new PurchaseId(1))
      val p2 = dao.findPurchase(new PurchaseId(3))

      dao.asInstanceOf[DataCache].show.map(p=>p.id.value) must contain only(1,3)
    }
  }


}
