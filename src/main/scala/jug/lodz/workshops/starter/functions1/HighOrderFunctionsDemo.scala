package jug.lodz.workshops.starter.functions1

import jug.lodz.workshops.WorkshopDisplayer._

object HighOrderFunctionsDemo {

  def main(args: Array[String]): Unit = {
    println("\n *** Scala starters functions 2*** \n")


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

    section("function as return type *** ")

    def createIncrementFunction(): Int=>Int = i => i+1

    val increment=createIncrementFunction()
    section(s"increment(1) "+increment(1))
    section(s"increment(5) "+increment(5))


    //method
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

  }

}

case class User(id:Int,name:String)
class UserDao(){
  private var users=Map(
    1 -> User(1, "Zofia"),
    2 -> User(1, "Stefan")
  )

  def findById(id:Int):Option[User] = users.get(id)
}
