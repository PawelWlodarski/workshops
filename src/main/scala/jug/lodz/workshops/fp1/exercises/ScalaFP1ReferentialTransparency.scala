package jug.lodz.workshops.fp1.exercises

/**
  * Created by pawel on 21.02.16.
  */
object ScalaFP1ReferentialTransparency {

  var globalState:Int=0

  val pure:Int=>Int= x=>x+1
  val impure: Int=>Int = x=>{
    globalState=globalState+1
    x+1+globalState
  }

  def main(args: Array[String]) {
    //definition
    val r=pure(1)
    println("pure value")
    println(r)
    println(r)
    println(r)

    println("pure function")
    println(pure(1))
    println(pure(1))
    println(pure(1))

    //not referential transparency
    val r2=impure(1)
    println("impure value")
    println(r2)
    println(r2)
    println(r2)

    println("impure function")
    println(impure(1))
    println(impure(1))
    println(impure(1))

  }

}
