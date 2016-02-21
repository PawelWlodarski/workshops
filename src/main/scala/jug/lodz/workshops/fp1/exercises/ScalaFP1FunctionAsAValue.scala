package jug.lodz.workshops.fp1.exercises

/**
  * Created by pawel on 21.02.16.
  */
object ScalaFP1FunctionAsAValue {

  val fun:Int=>Int = x=>x+1

  def logInvocation(f: Int=>Int, arg:Int):Int={
    val result=f(arg)
    println(s"invoking function with arg : $arg and result is : $result")
    result
  }

  //lab
  def multiplyArgByTwo(f:Int=>Int,arg:Int):Int= ???

  def createSomeFunction():Int=>Int = x=>x+5

  //lab
  def decorateFunctionSoThatArgumentIsMultipliedByTwo(f:Int=>Int) : Int=>Int = ???

  def addToOneMethod(x:Int)=x+1

  def main(args: Array[String]) {
    //example1
//    logInvocation(fun,1)

    //example2
//    println("multiply argument by two : "+multiplyArgByTwo(fun,1))

    //example3
//    println("create some function : "+createSomeFunction()(1))

    //example4
//    val fun2=decorateFunctionSoThatArgumentIsMultipliedByTwo(fun)
//    logInvocation(fun2,1)

    //example5
//    logInvocation(addToOneMethod,1)
  }

}
