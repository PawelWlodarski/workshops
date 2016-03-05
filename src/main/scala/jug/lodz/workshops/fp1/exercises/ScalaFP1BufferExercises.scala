package jug.lodz.workshops.fp1.exercises

/**
  * Created by pawel on 21.02.16.
  */
object ScalaFP1BufferExercises {

  //PARTIAL APPLICATION
  val calculateTax:(Double,Int) => Double = (a,b)=>a*b
  val calculateVat : Int=>Double = calculateTax(0.23,_:Int)

  def partialApplication(f:(Double,Int)=>Double, firstArg:Double): Int=>Double = ???

  //CURRYING
  lazy val curriedTax : Double=>Int=>Double= tax => sum=>tax*sum
  lazy val curriedJoinString3 : String=>String=>String=>String = s1=>s2=>s3=>s1+s2+s3

  lazy val curriedAdd2: Int=>Int=>Int= ???
  lazy val curriedAdd3: Int=>Int=>Int=>Int= ???
  lazy val curriedAdd4: Int=>Int=>Int=>Int=>Int= ???

  def curry[A,B,C](f:(A,B)=>C):A=>B=>C= ???
  def uncurry[A,B,C](f: A=>B=>C) : (A,B)=>C= ???


  //DEMONSTRATION - CURRYING WITH METHODS
  def curriedAddMethod(a:Int)(b:Int)(c:Int)=a+b+c

  def main(args: Array[String]) {
    println("DEMONSTRATION : PARTIAL APPLICATION")
    println(calculateTax(0.5,100)==50)
    println(calculateVat(100)==23.0)


//    println("EXERCISE : partial application")
//    println(partialApplication(calculateTax,0.23)(100)==23)
//    println(partialApplication(calculateTax,0.23)(200)==46)
//
//
//    println("DEMONSTRATION : curried")
//    println(curriedTax(0.23))
//    println(curriedTax(0.23)(100)==23)
////
//    println(curriedJoinString3("aaa"))
//    println(curriedJoinString3("aaa")("bbb"))
//    println(curriedJoinString3("aaa")("bbb")("ccc")=="aaabbbccc")
//
//    println(curriedAdd2(1)(2)==3)
//    println(curriedAdd3(1)(2)(3)==6)
//    println(curriedAdd4(1)(2)(3)(4)==10)
//
//    println("EXERCISE : curried")
//    println(curry(calculateTax)(0.23)(100)==23)
//    println(curry(calculateTax)(0.23)(200)==46)
//    println(uncurry(curriedTax)(0.23,100)==23)
//    println(uncurry(curriedTax)(0.23,200)==46)


//    println("DEMONSTRATION - CURRYING WITH METHODS")
//    println(curriedAddMethod(1) _)
//    println(curriedAddMethod(1)(2) _)
//    println(curriedAddMethod(1)(2)(3)==6)
  }

}
