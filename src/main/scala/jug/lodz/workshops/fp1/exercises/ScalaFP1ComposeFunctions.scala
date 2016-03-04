package jug.lodz.workshops.fp1.exercises

/**
  * Created by pawel on 21.02.16.
  */
object ScalaFP1ComposeFunctions {


  val parse : String => Int = s=>s.toInt
  val neighbours:Int=>List[Int] = x=>List(x-1,x+1)

  def main(args: Array[String]) {
    //DEMONSTRATION
    println("DEMONSTRATION : AndThen & compose")
    println(parse.andThen(neighbours)("3") == List(2,4))
    println(neighbours.compose(parse)("5") == List(4,6))

//    //LAB
//    println("LAB : andThen & compose")
//    println(customAndThen(parse,neighbours)("4")==List(3,5))
//    println(customCompose(neighbours,parse)("9")==List(8,10))
//
//    println("LAB : generics")
//    println(customAndThenGeneric(parse,neighbours)("4")==List(3,5))
//    println(customComposeGeneric(neighbours,parse)("9")==List(8,10))
//
//    println("LAB : andThenSeq")
//    println(andThenSeq(Seq(x=>x+1,x=>x*2,x=>x+5))(1)==9)
//    println(andThenSeq(Seq(x=>x+1,x=>x*2,x=>x+5))(3)==13)

  }

  def customAndThen(f1:String=>Int, f2:Int=>List[Int]):String=>List[Int] = ???
  def customCompose(f1:Int=>List[Int], f2:String=>Int):String=>List[Int] = ???

  //ADDITIONAL
  def customAndThenGeneric[A,B,C](f1:A=>B, f2:B=>C):A=>C = ???
  def customComposeGeneric[A,B,C](f1:B=>C, f2:A=>B):A=>C = ???

  //ADDITIONAL 2
  def andThenSeq(fs:Seq[Int=>Int]): Int=>Int = ???


}
