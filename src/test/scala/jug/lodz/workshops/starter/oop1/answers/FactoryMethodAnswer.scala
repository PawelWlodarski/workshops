package jug.lodz.workshops.starter.oop1.answers

import org.scalatest.{MustMatchers, WordSpec}

//SHOW SOLUTION LATER
class FactoryMethodAnswer extends WordSpec with MustMatchers{

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
  def apply(id:String):Processor=
    if(id == "UPPER") Upper
    else if(id == "TRIM") Trim
    else throw new RuntimeException(s"unknown processor $id")

  private object Upper extends Processor{
    override def process(input: String): String = input.toUpperCase
  }

  private object Trim extends Processor{
    override def process(input: String): String = input.trim
  }
}



