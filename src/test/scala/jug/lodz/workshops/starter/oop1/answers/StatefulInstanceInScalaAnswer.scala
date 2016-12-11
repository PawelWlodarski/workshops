package jug.lodz.workshops.starter.oop1.answers

import java.util
import java.util.Collections

import org.scalatest.{MustMatchers, WordSpec}

class StatefulInstanceInScalaAnswer extends WordSpec with MustMatchers{


  "Scala Stateful class" should {
    "correctly implement HasState" in {
      val instance:HasState=new ScalaStafulClass()

      instance.set("one")

      instance.get() mustBe "one"
    }

    //BONUS
    "implement data changes journal" in {
      val instance=new ScalaStafulClass()

      instance.set("one")
      instance.set("two")
      instance.set("three")

      val history: util.Collection[Any] = instance.showHistory
      history must contain allOf("one","two","three")
    }
  }

}

trait HasState{
  def set(a:Any) : Unit
  def get():Any
}

class ScalaStafulClass extends HasState{

  private var state:Any = _
  private val journal:java.util.Collection[Any] = new util.LinkedList[Any]()

  override def set(v: Any): Unit = {
    journal.add(v)
    state=v
  }
  override def get(): Any = state

  def showHistory:java.util.Collection[Any] = Collections.unmodifiableCollection(journal)
}

