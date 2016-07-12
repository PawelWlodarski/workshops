package jug.lodz.workshops.functions.exercises

import scala.annotation.tailrec

/**
  * Created by pawel on 04.07.16.
  */
object FunctionsPart3MethodsAndFunctions {


  def main(args: Array[String]) {
    println("\n\n METHODS AND FUNCTIONS \n")


    println("  *** SECTION : METHODS to FUNCIONS ***")
    //FOCUS ON SYNTAX

    //1) explain why there is no return
    //2) explain when we can remove braces?
    def method(s: String) = {
      s.toInt + 20
    }

    val f: (String) => Int = method _ // explain underscore

    println("   * function created from method : " + f("22"))


    println("\n  *** SECTION :  PARTIAL APPLICATION & CURRYING ***")

    //1) explain syntax with two parentheses
    def calculateGrossStandard(tax: Double, net: Int) = net + net * tax
    def calculateGross(tax: Double)(net: Int) = net + net * tax

    println("     * calculate gross in a standard way : " + calculateGrossStandard(0.23, 10))
    println("     * calculate gross in a curried way : " + calculateGross(0.23)(10))

    //1) explain the name 'currying'??
    val grossFunction: (Double) => (Int) => Double = calculateGross _
    val grossWithTaxSet: (Int) => Double = calculateGross(0.23) _

    println("     * calculate gross function : " + grossFunction(0.23)(10))
    println("     * calculate gross function with tax set : " + grossWithTaxSet(10))


    println("\n  *** SECTION : PARTIAL APPLICATION & TYPE DETECTION ***")

    // We will see when types in a second parentheses pair can be omitted
    def mapStandard[A,B](s:Seq[A],f:A=>B) = s.map(f)
    def mapCurring[A,B](s:Seq[A])(f:A=>B) = s.map(f)

    mapStandard(Seq(1,2,3),(i:Int)=>i+1)

    //no type needed in lambdas
    mapCurring(Seq(1,2,3))(i=>i+1)
    mapCurring(Seq(1,2,3))(_+1)

    //you can not use generics when defining value function, all types has to be set
    val mapFixedSequence:(Int => Int) => Seq[Int] = mapCurring(Seq(1,2,3))_
    println("     * invoking curried function : "+mapFixedSequence(_+1))

    println("\n  *** SECTION : PARTIAL APPLICATION & FUNCTION PARAMETERS ***")
    //move functional parameter to second position - LOAN PATTERN
    def loanFile[A](path:String)(f:Iterator[String]=>A): A ={
      val source = scala.io.Source.fromFile(path)
      try{
        f(source.getLines())
      }finally{
        source.close()
      }
    }

    //translate to 'custom structure'
    loanFile("/tmp/file.txt")(lines=>lines.size)
    loanFile("/tmp/file.txt")({lines=>lines.size})
    loanFile("/tmp/file.txt"){lines=>lines.size}
    loanFile("/tmp/file.txt"){ lines=>
      lines.foreach(println)
      lines.size
    }


    println("\n  *** SECTION :  TAIL RECURSION ***")

    //1) explain what @tailrec give us
    @tailrec
    def sumRange(start:Int,stop:Int,acc:Int=0): Int ={
      if(start>stop) acc
      else sumRange(start+1,stop,acc+start)
    }

    println("     * recursion [1,5] : "+sumRange(1,5))
    println("     * recursion [3,6] : "+sumRange(3,6))



    println("\n  *** SECTION : TAIL RECURSION - INTERNAL LOOP ***")

    //1) explain what @tailrec give us
    //2) why internal method can access 'stop'
    def sumRangeWithInternalMethod(start:Int,stop:Int): Int ={
      @tailrec
      def sumRange(start:Int,acc:Int):Int={
        if(start>stop) acc
        else sumRange(start+1,acc+start)

      }
      sumRange(start,0)
    }

    println("     * recursion with internal method [1,5] : "+sumRangeWithInternalMethod(1,5))
    println("     * recursion with internal method [3,6] : "+sumRangeWithInternalMethod(3,6))

    /**
      * TRAP
      * def method(a:Int){a+1}  //procedure
      */
  }
}
