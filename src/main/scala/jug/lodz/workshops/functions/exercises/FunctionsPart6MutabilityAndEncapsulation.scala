package jug.lodz.workshops.functions.exercises

/**
  * Created by pawel on 11.07.16.
  */
object FunctionsPart6MutabilityAndEncapsulation {

  def main(args: Array[String]) {

    println("\n MUTABILITY \n")

    val two = new MutableNumber(2)
    val three = new MutableNumber(3)

    val result = (two + three) * two + two
    println("    * MUTABLE NUMBER RESULT (2+3)*2*2 = " + result)

    //CODE - String example
    //    println("\n    * STRING EXAMPLE")
    //    val string:String="start"
    //    globalFunction(string)
    //    println(s"string after global function : "+string)
    //
    //    val stringBuilder:StringBuilder = new StringBuilder("builderstart")
    //    globalFunction(stringBuilder)
    //    println(s"string after global function : "+stringBuilder)

    println("\n ENCAPSULATED MUTABILITY \n")

    //    def filter[A](s:List[A])(p:A=>Boolean): List[A] ={
    //      val result=new ListBuffer[A]()
    //
    //      for(e <- s) if(p(e)) result += e
    //
    //      result.toList
    //    }
    //
    //
    //    println("filtering (is there a mutable structure?) : "+filter(List(1,2,3,4,5))(i=>i>3))
  }

  // Explain Unit Return Type (mutate state or warm up air)
  def globalFunction(s: String): Unit = ???
  //s +"_stop"
  def globalFunction(s: StringBuilder): Unit = ??? //s.append("_stop")


}


// var - variable which can be changed
class MutableNumber(var value: Int) {
  // <- mutate state
  def +(other: MutableNumber) = {
    this.value = this.value + other.value
    this
  }
  def *(other: MutableNumber) = {
    this.value = this.value * other.value
    this
  }

  override def toString() = s"${value}"
}