package jug.lodz.workshops.starter.patternmatching1

// * Show case class bytecode
object PatternMatching1Demo {

  def main(args: Array[String]): Unit = {
    println(" *** SCALA STARTER PATTERN MATCHING & CASE CLASSES")

    val someValue=1

    //at basic level similar to switch
    // * change someValue to present both paths
    // * change to :Any="aaa"
    someValue match {
      case 1 => println("matched 1")
      case other => println(s"matched $other")
    }

    println(" *** CASE CLASSES ***")
    val simpleExample=SimpleCaseClass("innerValue")

    //change type of simple example and show default case
    simpleExample match {
      case SimpleCaseClass(innerValue) => println(s"inner value is : $innerValue")
      case _ => println("can not extract inner value")
    }


    println(" *** COMPLEX CASE CLASSES ***")

    val complex=ComplexCaseClass("complex",SimpleCaseClass("simple"))

    complex match {
      case ComplexCaseClass(name,SimpleCaseClass(inner)) =>
        println(s"complex case class has : name=$name , with inner value=$inner")
    }


    println(" *** PATTERN MATCHING EVERYWHERE ***")
    val ComplexCaseClass(extractedName,SimpleCaseClass(extractedInner)) = complex

    println(s" * extracted name=$extractedName, extracted inner name = $extractedInner")

    val tuple:(Int,String) = (1,"aaa")

    val (key,value) = tuple

    println(s" * extracted from tuple : ($key,$value)")


    println(" *** GUARDS ***")

    val anotherValue:Any="bbbb"

    //Test different scenarios
    anotherValue match {
      case s:String if s.length < 3 => println(s" very short string $s")
      case s:String  => println(s" long string $s")
      case i:Int if i > 0 => println("positive integer $i")
      case i:Int if i < 0 => println("negative integer $i")
      case i:Int  => println("this must be zero : $i")
      case other => println(s"neither String nor Integer : $other")
    }


    // *****  pattern matching exception **************

//    val s="value"
//
//    s match {
//      case "something else" => println("this should throw an exception")
//    }

    // Capital letters X or `x`

    //@switchTable
    println(" *** @switch ***")

    val i:Any=9

    import scala.annotation.switch

    (i: @switch) match {
      case 9 => println("9")
        //uncomment to check warning
//      case SimpleCaseClass(value) => println(s"value from simple case class = $value")
      case _ => println("other")
    }

  }

}

case class SimpleCaseClass(innerValue:String)

case class ComplexCaseClass(value:String, innerCaseClass:SimpleCaseClass)
