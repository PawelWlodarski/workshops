package jug.lodz.workshops.fpmechanics.intro

object FunctionAsAValue {

  def main(args: Array[String]) {
    defineAFunction
    functionsAsValues
    List(1,2,3).reduce((a,b)=>a+b)


  }
  def defineAFunction: Unit = {
      val addOne = (a: Int) => a + 1

      val addOneType: Int => Int = (a: Int) => a + 1

      val addOneTypeShort: Int => Int = a => a + 1

      val addOneTypeEmoticon: Int => Int = _ + 1

      val addOneObject: Function[Int, Int] = (a: Int) => a + 1

      val addOneFullObject: Function[Int, Int] = new Function[Int, Int] {
        override def apply(a: Int): Int = a + 1
      }

      println(addOne(3))
      println(addOneType(3))
      println(addOneTypeShort(3))
      println(addOneTypeEmoticon(3))
      println(addOneObject(3))
      println(addOneFullObject(3))
    }
  def functionsAsValues: Unit = {
    val fun1 = (x: Int) => x + 1
    val fun2 = (x: Int) => x + 1
    val funs10 = (1 to 10).map(_ => (x: Int) => x + 1).toList
    val funs12=fun1::fun2::funs10
    val functionsChained = funs12.reduce(_ andThen _)
    val functionsComposed = funs12.reduce(_ compose _)

    println("functions chained : " + functionsChained(0))
    println("functions composed : " + functionsComposed(0))

    val chained = Function.chain(funs12)
    println("functions chained by library : " + chained(0))


  }



}
