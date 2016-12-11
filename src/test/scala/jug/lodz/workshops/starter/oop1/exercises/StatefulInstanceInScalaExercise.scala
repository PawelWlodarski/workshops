package jug.lodz.workshops.starter.oop1.exercises

import java.util
import java.util.Collections

import org.scalatest.{MustMatchers, WordSpec}

class StatefulInstanceInScalaExercise extends WordSpec with MustMatchers{


  "Scala Stateful class" should {
// EXERCISE - UNCOMMENT AND FIX
//    "correctly implement HasState" in {
//      val instance:HasState=new ScalaStafulClass()
//
//      instance.set("one")
//
//      instance.get() mustBe "one"
//    }

    //BONUS - uncomment
//    "implement data changes journal" in {
//      val instance=new ScalaStafulClass()
//
//      instance.set("one")
//      instance.set("two")
//      instance.set("three")
//
//      val history: util.Collection[Any] = instance.showHistory
//      history must contain allOf("one","two","three")
//    }
  }

}

trait HasState{
  def set(a:Any) : Unit
  def get():Any
}

class ScalaStafulClass {}

