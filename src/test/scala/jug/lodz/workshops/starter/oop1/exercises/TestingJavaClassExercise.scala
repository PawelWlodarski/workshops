package jug.lodz.workshops.starter.oop1.exercises

import jug.lodz.workshops.starter.oop1.JavaStatefulObject
import org.scalatest.{MustMatchers, WordSpec}

class TestingJavaClassExercise extends WordSpec with MustMatchers{

  "state in java class" should{
   "be set correctly" in {
     val sut=new JavaStatefulObject()

     ???

     sut.getState mustBe 101
   }

    //UNCOMMENT AND FIX
//   "convert to string correctly" in {
//     ???
//
//     ???
//
//     sut.toString mustBe "JavaStatefulObject{state=STATE}"
//   }

  }

}
