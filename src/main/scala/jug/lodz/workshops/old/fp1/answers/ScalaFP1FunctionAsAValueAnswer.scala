package jug.lodz.workshops.old.fp1.answers

/**
  * Created by pawel on 21.02.16.
  */
object ScalaFP1FunctionAsAValueAnswer {

  //DEMONSTRATION - FUNCTION AS PARAMETER
  val fun:Int=>Int = x=>x+1

  def logInvocation(f: Int=>Int, arg:Int):Int={
    val result=f(arg)
    println(s"invoking function with arg : $arg and result is : $result")
    result
  }

  //LAB - FUNCTION AS PARAMETER
  //def invokeParsed(f:Function[Int,Int],arg:String):Int
  def invokeParsed(f:Int=>Int,arg:String):Int= f(arg.toInt)

  ////LAB - FUNCTION AS PARAMETER - ADDITIONAL EXERCISE
  //def generateN(f:Function0[String],n:Int) : List[String]
  def generateN(f:()=>String,n:Int) : List[String] = (1 to n).map(_ => f()).toList

  //DEMONSTRATION
  // def createSomeFunction():Function[Int,Int] = x=>x+5
  def createSomeFunction():Int=>Int = x=>x+5

  //LAB - CREATE A FUNCTION
  def createHello() : String => String = s=> s"Hello $s"
  def createNegation() : Int => Int = i => -i
  //def createAddTwoNumbers() : Function2[Int,Int,Int]
  def createAddTwoNumbers() : (Int,Int) => Int = (a,b) => a+b

  //LAB - FUNCTION AS AN ARGUMENT AND RETURN VALUE
  //  def parseArgumentBeforeCallingFunction(f:Function[Int,Int]) : Function[String,Int]
  def parseArgumentBeforeCallingFunction(f:Int=>Int) : String=>Int = s=>f(s.toInt)
  def addLoggingToAFunction(f:Int=>Int) : Int=>(Int,String) = i => (f(i),s"arg=$i")


  //DEMONSTRATION FUNCTIONS AND METHODS
  def addToOneMethod(x:Int)=x+1

  def main(args: Array[String]) {
    //DEMONSTRATION
    println("DEMONSTRATION")
    println("passing function as parameter")
    logInvocation(fun,1)

        println("\nEXERCISE FUNCTION AS PARAMETER")
        println(invokeParsed(fun,"5")==6)
        println(invokeParsed(fun,"7")==8)
        println(invokeParsed(x=>x*2,"7")==14)
        println("\nEXERCISE FUNCTION AS PARAMETER ADDITIONAL")
        println(generateN(()=>"FP",2)==List("FP","FP"))
        println(generateN(()=>"FP",3)==List("FP","FP","FP"))

    //DEMONSTRATION
        println("\ncreating a function")
        println(createSomeFunction()(1))

    //    println("\nEXERCISE CREATE A FUNCTION")
        println(createHello()("Romek") == "Hello Romek")
        println(createHello()("Staszek") == "Hello Staszek")
        println(createNegation()(10) == -10)
        println(createNegation()(-5) == 5)
        println(createAddTwoNumbers()(1,3)==4)
        println(createAddTwoNumbers()(7,2)==9)

        println("\nEXERCISE CHANGING FUNCTION")
        println(parseArgumentBeforeCallingFunction(fun)("3")==4)
        println(parseArgumentBeforeCallingFunction(fun)("8")==9)
        println(parseArgumentBeforeCallingFunction(createNegation())("-8")==8)

        println(addLoggingToAFunction(fun)(1)==(2,"arg=1"))
        println(addLoggingToAFunction(fun)(3)==(4,"arg=3"))


    //example5
        println("DEMONSTRATION FUNCTION AS A METHOD")
        logInvocation(addToOneMethod,1)
        val functionFromMethod: (Int) => Int = addToOneMethod _
        println("function as a method : " + functionFromMethod)
  }

}
