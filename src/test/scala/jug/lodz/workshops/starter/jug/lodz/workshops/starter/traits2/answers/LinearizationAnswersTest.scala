package jug.lodz.workshops.starter.jug.lodz.workshops.starter.traits2.answers

import jug.lodz.workshops.starter.traits2.answers.LinearizationAnswers.{Auth, Request, ShopController}
import org.scalatest.{MustMatchers, WordSpec}

class LinearizationAnswersTest extends WordSpec with MustMatchers{

  "EXERCISE1 Shop controller" should {
    "handle shop url" in {
      val controller=new ShopController
      val r=new Request("/shop","chrome","lodz",new Auth("login","password"))
      val response=controller.handle(r)

      response.status mustBe 200
      response.body mustBe "<h1>Welcome in Shop</h1>"
    }

    "return not found for other urls" in {
      val controller=new ShopController
      val r=new Request("/wrong","chrome","lodz",new Auth("login","password"))
      val response=controller.handle(r)

      response.status mustBe 404
    }
  }

}
