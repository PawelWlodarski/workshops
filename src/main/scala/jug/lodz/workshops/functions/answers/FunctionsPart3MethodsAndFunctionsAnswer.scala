package jug.lodz.workshops.functions.answers

import scala.annotation.tailrec

/**
  * Created by pawel on 04.07.16.
  */
object FunctionsPart3MethodsAndFunctionsAnswer {


  def main(args: Array[String]) {
    println("\n\n METHODS AND FUNCTIONS \n")


    println("  * METHODS to FUNCIONS")
    //FOCUS ON SYNTAX

    //why there is no return
    //can we remove braces?
    def method(s: String) = {
      s.toInt + 20
    }

    val f: (String) => Int = method _ // explain underscore

    println("   * function created from method : " + f("22"))

    println("\n  * METHOD RETURN A FUNCTION")


    println("\n  * PARTIAL APPLICATION & CURRYING")

    def calculateGrossStandard(tax: Double, net: Int) = net + net * tax
    def calculateGross(tax: Double)(net: Int) = net + net * tax

    println("     * calculate gross in a standard way : " + calculateGrossStandard(0.23, 10))
    println("     * calculate gross in a curried way : " + calculateGross(0.23)(10))

    //why name 'currying'??
    val grossFunction: (Double) => (Int) => Double = calculateGross _
    val grossWithTaxSet: (Int) => Double = calculateGross(0.23) _

    println("     * calculate gross function : " + grossFunction(0.23)(10))
    println("     * calculate gross function with tax set : " + grossWithTaxSet(10))


    println("\n  * PARTIAL APPLICATION & TYPE DETECTION")

    def mapStandard[A,B](s:Seq[A],f:A=>B) = s.map(f)
    def mapCurring[A,B](s:Seq[A])(f:A=>B) = s.map(f)

    mapStandard(Seq(1,2,3),(i:Int)=>i+1)

    //no type needed
    mapCurring(Seq(1,2,3))(i=>i+1)
    mapCurring(Seq(1,2,3))(_+1)

    //no generics
    val mapFixedSequence:(Int => Int) => Seq[Int] = mapCurring(Seq(1,2,3))_
    println("     * invoking curried function : "+mapFixedSequence(_+1))

    println("\n  * PARTIAL APPLICATION & FUNCTION PARAMETERS")
    //move functional parameter to second position
    def loanFile[A](path:String)(f:Iterator[String]=>A): A ={
      val source = scala.io.Source.fromFile(path)
      try{
        f(source.getLines())
      }finally{
        source.close()
      }
    }

    loanFile("/tmp/file.txt")(lines=>lines.size)
    loanFile("/tmp/file.txt")({lines=>lines.size})
    loanFile("/tmp/file.txt"){lines=>lines.size}
    loanFile("/tmp/file.txt"){ lines=>
      lines.foreach(println)
      lines.size
    }


    println("\n  * TAIL RECURSION")

    @tailrec
    def sumRange(start:Int,stop:Int,acc:Int=0): Int ={
      if(start>stop) acc
      else sumRange(start+1,stop,acc+start)
    }

    println("     * recursion [1,5] : "+sumRange(1,5))
    println("     * recursion [3,6] : "+sumRange(3,6))



    println("\n  * TAIL RECURSION - INTERNAL LOOP")


    /**
      * TRAP
      * def method(a:Int){a+1}  //procedure
      */
  }
}
