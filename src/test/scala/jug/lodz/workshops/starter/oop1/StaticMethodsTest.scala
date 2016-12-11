package jug.lodz.workshops.starter.oop1

import org.scalatest.{MustMatchers, WordSpec}

class StaticMethodsTest extends WordSpec with MustMatchers{

  "Static Methods" should{
    "add prefix" in {
      val result=StaticMethods.prefix("prefix","test")
      result.mustBe("prefix_test")
      result mustBe "prefix_test"
    }

    "mulitply integers" in {
      val result=StaticMethods.multiply(4,5)

      result mustBe 20 //change to 19 and check result
    }
  }

}
