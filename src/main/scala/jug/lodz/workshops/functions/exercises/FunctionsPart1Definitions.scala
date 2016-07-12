package jug.lodz.workshops.functions.exercises

/**
  * Created by pawel on 03.07.16.
  */
object FunctionsPart1Definitions {

  def main(args: Array[String]) {
    println("\n\n SCALA FUNCTIONS INTRO \n")

    println("  *** SECTION :  DEFINE A FUNCTION ***")

    // form similar to classical java - anonymous class
    val anonymousClassForm:Function[Int,Int]=new Function[Int,Int]{
      override def apply(i: Int): Int = i+1
    }

//CODE  -- change Function[Int,Int] to (Int => Int)
//    val typeAlias: Int=>Int = new Function[Int,Int]{
//      override def apply(i: Int): Int = i+1
//    }

//CODE  -- introduce lambda notation
//    val shortWithType: Int=>Int = i=>i+1
//    val shortWithTypeInLambda = (i:Int) => i+1
//
//    val inc=(i:Int) => i+1   // reason for short names! // type inference
//
//    val f:Int=>Int=_+1  // with underscore
//
//    println("      * anonymous class form: "+anonymousClassForm(7))
//    println("      * type alias form: "+typeAlias(7))
//    println("      * short with Type form: "+shortWithType(7))
//    println("      * short with type in lambda form: "+shortWithTypeInLambda(7))
//    println("      * inc form: "+inc(7))
//    println("      * underscore form: "+f(7))


    println("\n  *** SECTION :  FUNCTIONS COMPOSITION ***")


    val parse: String => Int = s=>s.toInt  //exercise - make short with underscore
    val square:Int=>Int = i=> i * i

//CODE - dot is not needed
//    val squareString=parse andThen square
//
//    println("      *  square string : "+squareString("4"))


    println("\n  *** SECTION :  SUGAR ***")
    ///syn..... sugarrrrr -
    // 1) how method invocation can be simplified
    // 2) operators are also method
    parse.andThen(square)
    println(s"      *  without sugar 1+2 : ${1.+(2)}")
    println(s"      *  without sugar 3*7 : ${3.*(7)}")
    //Console println "text"

    println("\n  *** SECTION :  TUPLES ***")
    //1) every project has class 'Pair'
    //2) standardization
    val t1=(1)
    val t2=("a",1)
    val t3=("a",1,false)

    val withoutSugar=new Triple[String,Int,Boolean]("a",1,false)

    println("      * 3 elements tuple : "+withoutSugar)

//CODE
//    println(s"      * tuple elements: (${t3._1},${t3._2},${t3._3})")

    println("\n  *** SECTION :  CLOSURE *** ")
    //using external variables

    val config=Map[String,Double]("tax"->0.23)
//    val config=Map("tax"->0.23)  // type inference

    val gross=(net:Int)=>config("tax") * net + net

    println("      * taking tax from closure 10 + 2.3 : "+gross(10))
  }

}
