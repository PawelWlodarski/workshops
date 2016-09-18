package jug.lodz.workshops.general.fpmatters.exercises

/**
  * Created by pawel on 18.09.16.
  */
object FPMattersPart1Intro {

  def main(args: Array[String]): Unit = {
    println("\n\n Functional Programming Matters Intro \n")

    println("  *** Compose Functions ***")
    case class Product(name:String, price:BigDecimal)
    val tv = Product("Tv", BigDecimal(100.0))

    val getPrice:Product => BigDecimal = p=>p.price
    val gross : BigDecimal => BigDecimal = net => net * 1.23
    val display : BigDecimal => String = price => s"$price PLN"

    //CODE - Functional composition
//    val displayGross: Product => String = getPrice andThen gross andThen display
//    val displayGrossCompose: Product => String = display compose gross compose getPrice
//
//    println("      *  gross price : "+displayGross(tv))
//    println("      *  gross price : "+displayGrossCompose(tv))


    println("  *** Currying ***")

    val grossDI : Double => BigDecimal => BigDecimal = tax=> net => net * tax
    val displayDI: String=>BigDecimal => String = currency => price => s"$price $currency"

//   CODE - use currying to DI
//    val displayGrossDI: Product => String = getPrice andThen grossDI(1.07) andThen displayDI("ZLOTYCH")
//    println("      *  gross price DI: "+displayGrossDI(tv))

    val grossTwoArg: (Double,BigDecimal) => BigDecimal = (tax,net) => tax*net
    val grossCurried: Double => BigDecimal => BigDecimal =grossTwoArg.curried

//CODE - show workshit -> using currying to switch to One arg function
//    val grossInjected: (BigDecimal) => BigDecimal = grossCurried(1.23)
//    println("      *  gross injected: "+grossInjected(tv.price))


    println("  *** Fold Right ***")

    val sum=List(1,2,3,4,5).foldRight(0)((e,acc)=>acc+e)

// CODE
//    println("      *  fold right sum: "sum)

    println("  \nfold right step by step")
//    List(1,2,3,4,5).foldRight(0){(e,acc)=>
//      println(s"acc=$acc, e=$e")
//      acc+e
//    }

    println("  *** Map ***")
    val mapped = List(1, 2, 3, 4, 5).map(e => e + 1)
//    println(mapped)
  }

}
