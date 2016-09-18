package jug.lodz.workshops.old.intro.exercises


object FPMechanicsExercises {

  def main(args: Array[String]) {

  riddle

  }

  def exercise2(): Unit ={
    val f1=(x:Int)=>x*x*x
    val f2:Int=>Int= x=>x-7
    val f3:Int=>Int= _ + 3

    val solution = ???
  }


  def exercise3={
    def chained(n:Int,f:Int=>Int):Int=>Int = ???
  }

  def riddle={
    case class A()
    case class B()
    case class C()

    def f1(a:A)=B()
    def f2(b:B)=C()

    //69
    val composition= (f1 _) andThen (f2 _)

    println("riddle : "+ composition(A())) // Co tu się wypisze?
  }

  def exercise6={
    def m1(a:Int,b:Int)=a+b
    def m2(a:Int,b:Int)=a*b
    def m3(a:Int,b:Int)=a-b
    ???
  }

}
