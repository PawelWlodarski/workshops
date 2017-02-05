package jug.lodz.workshops.starter.valueclasses.answers

import org.scalatest.{MustMatchers, WordSpec}

class ValueClassesExerciseAnswers extends WordSpec with MustMatchers{

  "Define domain objects factory" should {
    "should be defined for Item" in {
      val item: Item = Item("name", "description")

      item.name mustBe "name"
      item.description mustBe "description"
    }

    "should be defined for Amount" in {
      val amount:Amount=Amount(1)

      amount.value mustBe 1
    }

    "should be defined for Purchase" in {
      val p:Purchase=Purchase(1,Item("item1","desc1"),Amount(11))

      p.id  mustBe a[PurchaseId]
      p.item.name mustBe "item1"
    }
  }

  "Database" should {
    "be initiated" in {
      val ids=Database.selectAll.map{case (id,purchase) => id.value}
      val names=Database.selectAll.map{case (id,purchase) => purchase.item.name}

      ids must contain allOf(1,2,3,4,5)
      names must contain allOf("item1","item2","item3","item4","item5")
    }
  }



}
