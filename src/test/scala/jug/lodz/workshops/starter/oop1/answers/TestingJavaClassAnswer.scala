package jug.lodz.workshops.starter.oop1.answers

import jug.lodz.workshops.starter.oop1.JavaStatefulObject
import org.scalatest.{MustMatchers, WordSpec}

class TestingJavaClassAnswer extends WordSpec with MustMatchers{

  "state in java class" should{
   "be set correctly" in {
     val sut=new JavaStatefulObject()

     sut.setState(101)

     sut.getState mustBe 101
   }

   "convert to string correctly" in {
     val sut=new JavaStatefulObject()

     sut.setState("STATE")

     sut.toString mustBe "JavaStatefulObject{state=STATE}"
   }

  }

}
