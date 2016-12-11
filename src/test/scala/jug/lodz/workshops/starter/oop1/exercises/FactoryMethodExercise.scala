package jug.lodz.workshops.starter.oop1.exercises

import org.scalatest.{MustMatchers, WordSpec}

//SHOW SOLUTION LATER
class FactoryMethodExercise extends WordSpec with MustMatchers{

  "Processor Factory" should{
    "return toUpper processor" in {
      val upper: Processor =ProcessorFactory("UPPER")

      val result=upper.process("someInput")

      result mustBe "SOMEINPUT"
    }

    "return trim processor" in {
      val trim=ProcessorFactory("TRIM")

      val result=trim.process("      someInput    ")

      result mustBe "someInput"
    }
  }

}

trait Processor{
  def process(input:String):String
}

object ProcessorFactory{
  def apply(id:String):Processor = ???
}



