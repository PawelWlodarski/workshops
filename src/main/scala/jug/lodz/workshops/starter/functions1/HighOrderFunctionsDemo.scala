package jug.lodz.workshops.starter.functions1

import jug.lodz.workshops.WorkshopDisplayer._

object HighOrderFunctionsDemo {

  def main(args: Array[String]): Unit = {
    println("\n *** Scala starter functions 2*** \n")


    println(" *** High order functions*** ")

    //method
    def highOrderMethod(f:Int=>Int): Int = f(1)
    //function
    val highOrderFunction : (Int=>Int) => Int = function => function(1)

    val simpleAddFive:Int=>Int = i => i+5
    val simpleMultiplyThree:Int=>Int = i => i*3

    section(s"high order addFive(1) : ${highOrderMethod(simpleAddFive)}")
    section(s"high order multiplyThree(1) : ${highOrderMethod(simpleMultiplyThree)}")
    section(s"high order function addFive(1) : ${highOrderFunction(simpleAddFive)}")

    //SHOW IN WORKSHEET!!!
    List(1,2,3).map(simpleAddFive)
    List(1,2,3).map(simpleMultiplyThree)
    Map(1 -> "a",2 -> "b").map(tuple => s"${tuple._1}+${tuple._1}")
    Map(1 -> "a",2 -> "b").map{ case (k,v) => s"$k+$v"}
    val mapper:((Int,String)) => String = {case (k,v) => s"$k+$v"}
    Map(1 -> "a",2 -> "b").map(mapper)


    section("function as a return type *** ")

    def createIncrementFunction(): Int=>Int = i => i+1

    val increment=createIncrementFunction()
    section(s"increment(1) "+increment(1))
    section(s"increment(5) "+increment(5))


    //method - with parameterization
    def createAddToN(n:Int) : Int => Int = i => i+n


    val addToThree=createAddToN(3)
    val addToTen=createAddToN(10)

    section(s"addToThree(4) "+addToThree(4))
    section(s"addToTen(4) "+addToTen(4))

    val  createAddToNFunction : Int => (Int => Int) = n=> i => i+n
    val addTo7FromFunction: Int => Int =createAddToNFunction(7)
    section(s"addTo7(2) : "+addTo7FromFunction(2))

    title("Functional Decorator")
    def decorateWithLogging(original:Int=>Int) : Int=>Int = i => {
      println(s" [LOGGING] : received parameter : $i")
      original(i) //calling original
    }

    val decrement : Int=>Int = i => i-1

    val decorated=decorateWithLogging(decrement)
    section("calling decorated function")
    decorated(4)

    title("functional dependency injection")

    def injectDao(dao:UserDao) : Int=> Option[User] =
      id => dao.findById(id)

    val dao= new UserDao

    val daoFunction: (Int) => Option[User] =injectDao(dao)

    section("daoFunction(1) : "+daoFunction(1))
    section("daoFunction(3) : "+daoFunction(3))


    title("currying intro")
    def calculateGross1(net:BigDecimal, tax:Double): BigDecimal = net + (net * tax)
    def calculateGross2(net:BigDecimal)(tax:Double): BigDecimal = net + (net * tax)
    def calculateGross3(tax:Double)(net:BigDecimal): BigDecimal = net + (net * tax)
    def calculateGross4(tax:Double) : BigDecimal =>  BigDecimal = net =>  net + (net * tax)
    def calculateGross5 : Double => BigDecimal =>  BigDecimal = tax => net =>  net + (net * tax)
    val calculateGross : Double => BigDecimal =>  BigDecimal = tax => net =>  net + (net * tax)


    section("calculate gross 1:",calculateGross1(BigDecimal(100),0.23))
    section("calculate gross 2:",calculateGross2(BigDecimal(100))(0.23))


    val calculateGross023: BigDecimal => BigDecimal = calculateGross3(0.23)
    section("calculate gross 0.23:",calculateGross023(BigDecimal(100)))
    val calculateGross008: BigDecimal => BigDecimal = calculateGross3(0.08)
    section("calculate gross 0.08:",calculateGross008(BigDecimal(100)))



    val calculateGrossFunction4 = calculateGross4(0.23)
    section("calculate gross 4:",calculateGrossFunction4(BigDecimal(100)))

    section("calculate gross 5:",calculateGross5(0.23)(BigDecimal(100)))

    val functionFor023: BigDecimal => BigDecimal = calculateGross(0.23)

    section("function for 0.23:",functionFor023(BigDecimal(100)))




  }

}

case class User(id:Int,name:String)
class UserDao(){
  private var users=Map(
    1 -> User(1, "Maria"),
    2 -> User(2, "John")
  )

  def findById(id:Int):Option[User] = users.get(id)
}
