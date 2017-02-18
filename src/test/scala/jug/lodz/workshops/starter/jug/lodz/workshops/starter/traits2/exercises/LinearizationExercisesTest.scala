package jug.lodz.workshops.starter.jug.lodz.workshops.starter.traits2.exercises

import jug.lodz.workshops.starter.traits2.exercises.LinearizationExercises._
import org.scalatest.{MustMatchers, WordSpec}

class LinearizationExercisesTest extends WordSpec with MustMatchers{

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


  "EXERCISE2 Location filter" should {
    "stop requests from forbidden country" in {
      val controller=new ShopController with LocationFilter
      val r=new Request("/shop","chrome","BANNED_COUNTRY",new Auth("login","password"))
      val response=controller.handle(r)

      response.status mustBe 403
    }

    "mask request location " in {
      val controller=new ShopController with LocationFilter with LocationMaskingFilter
      val r=new Request("/shop","chrome","BANNED_COUNTRY",new Auth("login","password"))
      val response=controller.handle(r)

      response.status mustBe 200
    }
  }

  "EXERCISE3 Security filter" should {
    "allow request with correct credentials" in {
      val controller=new ShopController with SecurityFilter
      val r=new Request("/shop","chrome","Poland",new Auth("user1","password1"))
      val response=controller.handle(r)

      response.status mustBe 200
    }

    "cancel request with wrong credentials" in {
      val controller=new ShopController with SecurityFilter
      val r=new Request("/shop","chrome","Poland",new Auth("user1","wrong1"))
      val response=controller.handle(r)

      response.status mustBe 403
    }
  }

}
