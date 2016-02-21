package jug.lodz.workshops.fp1.answers

/**
  * Created by pawel on 21.02.16.
  */
object ScalaFP1BufferExercisesAnswer {


  def partialApplication(f:(Int,Int)=>Int, firstArg:Int): Int=>Int =
    x=>f(firstArg,x)


  def curry(f:(Int,Int)=>Int):Int=>Int=>Int = a=>b=>f(a,b)

  def uncurry(f: Int=>Int=>Int) : (Int,Int)=>Int = (a,b)=>f(a)(b)

  val add:(Int,Int) => Int = (a,b)=>a+b

  def main(args: Array[String]) {
    println(s"standard form a=1,b=2 : ${add(1,2)}")

    val addToFive=partialApplication(add,5)
    println(s"add to five b=1 : ${addToFive(1)}")

    val curried: (Int) => (Int) => Int =curry(add)
    println(s"curried only first paramater a=3 : ${curried(3)}")
    println(s"curried two parameters a=3, b=7 : ${curried(3)(7)}")

    val uncurried: (Int, Int) => Int =uncurry(curried)
    println(s"uncurried - back to the begining : ${uncurried(1,2)==add(1,2)}")

  }

}
