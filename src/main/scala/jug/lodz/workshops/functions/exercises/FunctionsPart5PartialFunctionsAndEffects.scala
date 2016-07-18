package jug.lodz.workshops.functions.exercises

/**
  * Created by pawel on 05.07.16.
  */
object FunctionsPart5PartialFunctionsAndEffects {


  def main(args: Array[String]) {
    println("\n  * PURE & IMPURE FUNCTIONS - BASICS")


    println("\n   *** SECTION :  FUNCTIONS & REALITY ***")
    val parse: String => Int = s=>s.toInt

    println("     *  parsing integer : expectations "+parse("10"))  // IDEAL WORLD
//    println("     *  parsing integer : reality "+parse("aaaaaaaaaa"))  // REALITY
//    println("     *  parsing integer : reality "+parse(null))  // REALITY


    println(" \n  *** APPROACH 1 : PARTIAL FUNCTIONS")
    val increment : Int => Int = i =>i+1 // total function
    increment(5) // works everytime without exception
    val divide : (Int,Int) => Int = (a,b) => a/b  //partial function

//CODE : EXCEPTION    divide(4,0) // BUG : using partial function as a total function

    //PARTIAL FUNCTION AS A TYPE
//    val partialDivide =new PartialFunction[(Int,Int),Int] {
//      override def isDefinedAt(tuple: (Int, Int)): Boolean = tuple._2 != 0
//      override def apply(v: (Int, Int)): Int = v._1/v._2
//    }
//
//    println("    * partial divide is defined at (4,2) : "+partialDivide.isDefinedAt(4,2))
//    println("    * partial divide is defined at (4,0) : "+partialDivide.isDefinedAt(4,0))


    println("\n   *** APPROACH 2 : PARTIAL FUNCTIONS => TOTAL FUNCTION")

    //Option type
    val pureFunction:Int=>Int = i=>i+1
//CODE - mapping option
//    val some=Option(1)
//    val none=None
//    println("     * transforming some value : Some(1).map(paureFunction) : "+some.map(pureFunction))
//    println("     * transforming none : None.map(paureFunction) : "+none.map(pureFunction))

    //use option to move partial function to total function
    val parseTotal : String=>Option[Int] = s=>
      try{
        Option(s.toInt)
      }catch{
        case e:Exception => None
      }

//CODE - parseTotal defined for every param
//    println("     * total parse ('10') : "+parseTotal("10"))
//    println("     * total parse ('aaaa') : "+parseTotal("aaaa"))
//
//    //combined Option type with pure functions
//    parseTotal("10").map(pureFunction).foreach(e=>println(s"\n    * parse '10' combined with pure function = "+e))
//    parseTotal("aaa").map(pureFunction).foreach(e=>println(s"\n    * parse 'aaa' combined with pure function = "+e))


  }

}


