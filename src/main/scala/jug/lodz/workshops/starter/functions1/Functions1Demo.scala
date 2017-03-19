package jug.lodz.workshops.starter.functions1

object Functions1Demo {

  def main(args: Array[String]): Unit = {
    println("\n *** Scala starters functions *** \n")

    val someValue:Int=5

    //show Function type
    val incrementFunction:Function[Int,Int] = new Function[Int,Int](){
      override def apply(v1: Int): Int = v1+1
    }

    val incrementFunction2:Function[Int,Int] = (v1:Int) => v1+1

    val incrementFunction3:Int => Int = v1 => v1 + 1

    val incrementFunction4:Int => Int = _ + 1

    println("\n *** functions composition *** \n")

    val f1:Int=>Int =  i=> i+1
    val f2=(i:Int) => i*10

    val f1AndThenf2= f1 andThen f2
    val f1Composef2 = f1 compose f2

    println(s"   ***  andThen f(1) : " +f1AndThenf2(1))
    println(s"   ***  compose f(1) : " +f1Composef2(1))

    val chained=Function.chain(List[Int=>Int](f1,f2,_-7))
    println(s"   ***  chained f(1) : " +chained(1))

    println("\n *** functions and methods*** \n")

    def method(i:Int):Int = i + 1

    val asFunction: (Int) => Int =  method _

    val addOneMethodAndThenTimeThree=(method _).andThen(_*3)

    println(s" **** method composition (1): ${addOneMethodAndThenTimeThree(1)}")


    println("\n *** PARTIAL FUNCTION")

    val abs1:PartialFunction[Int,Int] = {
      case i if i>=0 => i
      case i => -i
    }

    //Function ineritence Function >: PartialFunction
    val abs2:Int=>Int = {
      case i if i>=0 => i
      case i => -i
    }

    val divide:(Double,Double) => Double = {
      case (a,b) if b!=0 => a/b
    }

    println(s"abs1 (-5) : "+abs1(-5))
    println(s"abs2 (-5) : "+abs2(-5))
    println(s"divide (4/2) : "+divide(4.0,2.0))
//    println(s"divide (4/0) ! : "+divide(4.0,0.0))  //ERROR!

    val dividePartial:PartialFunction[(Double,Double), Double] = {
      case (a,b) if b!=0 => a/b
    }

    //IF FUNCTION IS DEFINED
    println(s"divide is defined at (4/0) ! : "+dividePartial.isDefinedAt(4.0,0.0))

    val zeroHandler:PartialFunction[(Double,Double), Double] = {
      case (_,0) => Double.PositiveInfinity
    }

    //EXPLAIN WHY TOTAL FUNCTIONS ARE IMPORTANT
    val totalFunction = dividePartial orElse zeroHandler
    println(s"total (4/0) ! : "+totalFunction(4.0,0.0))  //ERROR!



  }


}
