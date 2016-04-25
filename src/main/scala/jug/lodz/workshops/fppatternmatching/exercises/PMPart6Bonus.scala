package jug.lodz.workshops.fppatternmatching.exercises

import scala.annotation.switch

/**
  * Created by pawel on 25.04.16.
  */
object PMPart6Bonus {

  object Demo{
    def switch()={
      val input1=1
      val result1=(input1: @switch) match {
        case 1 => "value is one"
        case _ => "value is not one"
      }

      println(s"RESULT1 : "+result1)


//      val input2=1
//      val result2=(input2: @switch) match {
//        case 0=> "value is zero"
//        case `input1` => "value is one"
//        case _ =>   "value is something else"
//      }
//
//
//      println(s"RESULT2 : "+result2)
    }
  }

  def main(args: Array[String]) {
    Demo.switch()
  }

}
