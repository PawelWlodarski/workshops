package jug.lodz.workshops.general.fpmatters.exercises

/**
  * Created by pawel on 18.09.16.
  */
object FPMattersPart2HighOrderFunctions {

  def main(args: Array[String]): Unit = {
    println("\n\n Functional Programming Matters High Order Functions \n")

    println("  *** How To use list in recursion ***")
    val l1 = List(1, 2, 3)
    val l2 = 1 :: 2 :: 3 :: Nil
    // CODE - how to do recursion
    //    println(s"      *  l1==l2 : ${l1==l2}")
    //
    //    val head::tail=l2
    //    println(s"      *  first head {$head} and first tail $tail")
    //    val head2::tail2=tail
    //    println(s"      *  second head {$head2} and second tail $tail2")
    //    val head3::tail3=tail2
    //    println(s"      *  third head {$head3} and third tail $tail3")

    println("  *** Generic Parameterization ***")
    def loop[A](l: List[A])(zero: A)(f: (A, A) => A): A = l match {
      case Nil => zero
      case head :: tail => f(head, loop(tail)(zero)(f))
    }

    // CODE - less generic functions with loop
    //    val sum:List[Int]=>Int= list=> loop(list)(0){(e,acc)=>
    //      e+acc
    //    }
    //
    //    val sumShort=(l:List[Int]) => loop(l)(0)(_+_)
    //
    //    println("      *  sum with loop: "+sum(List(1,2,3,4,5)))


    println("  *** compose parameterization ***")

    val unitSum: Int => Int => Int = a => b => a + b
    val unitSquare: Int => Int = i => i * i


    // CODE compose simple functions
    //    val unitSumSquared: (Int, Int) => Int = Function.uncurried(unitSquare andThen unitSum)

    //    val sumSquared:List[Int] => Int = l=> loop(l)(0)(unitSumSquared)
    //
    //   println("      *  sum squared with loop : "+sumSquared(List(1,2,3)))

    println("  *** different types ***")
    case class Product(name: String, price: BigDecimal)
    val tv = Product("Tv", BigDecimal(10.0))
    val pc = Product("Pc", BigDecimal(20.0))
    val mouse = Product("Mouse", BigDecimal(30.0))

    val getPrice: Product => BigDecimal = p => p.price
    val getName: Product => String = p => p.name
    val gross: BigDecimal => BigDecimal = net => net * 1.23

    object MathLibrary {
      val sum: (BigDecimal, BigDecimal) => BigDecimal = (bd1, bd2) => bd1 + bd2
      val max: (BigDecimal, BigDecimal) => BigDecimal = (bd1, bd2) => if (bd1 > bd2) bd1 else bd2
    }

    object CSVLib {
      val joinCsvLine: (Any, Any) => String = (a, b) => if ("" != b) a + "," + b else a.toString
    }

    def generalLoop[A, B](l: List[A])(zero: B)(f: (A, B) => B): B = l match {
      case Nil => zero
      case head :: tail => f(head, generalLoop(tail)(zero)(f))
    }

    //MANIPULATE COMPOSITION INDEPENDENTLY!!
    val grossPrice: (Product) => BigDecimal = getPrice andThen gross // try to comment out gross , how much code need to change
    val sumGrossPrice: (Product) => (BigDecimal) => BigDecimal = grossPrice andThen MathLibrary.sum.curried //change sum to max
    val conditionUncurried: (Product, BigDecimal) => BigDecimal = Function.uncurried(sumGrossPrice)

    //COMPOSITION WITH DIFFERENT TYPES
    //    val buildCsv=Function.uncurried(grossPrice andThen CSVLib.joinCsvLine.curried)
    //    val buildNamesCsv=Function.uncurried(getName andThen CSVLib.joinCsvLine.curried)
    //("")(buildCsv) - try this variant

    //different types
    //    val compositionResult=generalLoop(List(tv,pc,mouse))(BigDecimal(0))(conditionUncurried)
    //    println("      *  sum gross prices : "+compositionResult)


  }


}
