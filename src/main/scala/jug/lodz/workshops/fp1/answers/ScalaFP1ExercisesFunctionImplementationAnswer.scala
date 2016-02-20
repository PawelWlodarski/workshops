package jug.lodz.workshops.fp1.answers

/**
  * Created by pawel on 20.02.16.
  */
object ScalaFP1ExercisesFunctionImplementationAnswer {

  val addOneToX: Int=>Int = x => x+1
  val multiplyXByTwo: Int=>Int = x => x*2

  val addOntToXShort:Int=>Int = _+1

  val addOntToXFull:Function[Int,Int]=new Function[Int,Int]{
    override def apply(v1: Int): Int = v1+1
  }

  //exercise
  val multiplyByTwoShort:Int=>Int = _*2

  val multiplyByTwoFull:Function1[Int,Int]=new Function[Int,Int] {
    override def apply(v1: Int): Int = v1*2
  }

  def main(args: Array[String]) {
    //1
    println("example 1")
    (1 to 10).map(addOneToX).foreach(x=>print(x+","))

    println("\nexample 2")
    (1 to 10) map multiplyXByTwo  foreach(x=>print(x+","))

    println("\nexample 3")
    (1 to 10).map(addOntToXShort).foreach(x=>print(x+","))

    println("\nexample 4")
    (1 to 10).map(addOntToXFull).foreach(x=>print(x+","))

    //exercise
    println("\nexercise")
    (1 to 10).map(multiplyByTwoFull).foreach(x=>print(x+","))

  }
}
