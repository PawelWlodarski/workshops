package jug.lodz.workshops.fpmechanics.intro.exercises

import java.util.UUID


object FPMechanicsSolutions {


  def main(args: Array[String]) {
    exercise1Multiply()
    exercise2
    exercise3
    riddle
    exercise4
    exercise5

    exercise7
  }

  def exercise1Multiply() = {
    val multiply = new Function2[Int, Int, Int] {
      override def apply(v1: Int, v2: Int): Int = v1 * v2
    }

    println(s"object 2*3 = ${multiply(2, 3)}")

    val multiSugar = (a: Int, b: Int) => a * b

    println(s"with sugar 2*3 = ${multiSugar(2, 3)}")
  }

  def exercise2: Unit = {
    val f1 = (x: Int) => x * x * x
    val f2: Int => Int = x => x - 7
    val f3: Int => Int = _ + 3

    val fk = f3 andThen f1 andThen f2
    println("exercise 2 : " + fk(2))
  }
  def exercise3: Unit = {
    def chained(n: Int, f: Int => Int): Int => Int =
      (1 to n) map (_ => f) reduce (_ andThen _)

    val f = chained(3, x => x * 2)

    println("exercise3 : " + f(1))
  }

  def riddle = {
    case class A()
    case class B()
    case class C()

    def f1(a: A) = B()
    def f2(b: B) = C()

    //69
    val composition = (f1 _) andThen (f2 _)

    //or

    val function1 = f1 _ //function1: A => B = <function1>
    val function2 = f2 _ //function2: B => C = <function1>
    val composition2 = function1 andThen function2 //composition2: A => C = <function1>


    composition(A()) // res0: C = C()
  }
  def exercise4: Unit = {
    def multiplier(x: Int)(y: Int) = x * y
    val multiplyBy2 = multiplier(2) _
    val multiplyBy42 = multiplier(42) _

    multiplyBy2(4) //8
  }


  def exercise5 = {
    val fourStrings: String => String => String => String = s1 => s2 => s3 => s"s1=$s1,s2=$s2,s3=$s3"

    fourStrings("joł")("feel")("the flow")
  }

  def exercise6={
    def m1(a:Int,b:Int)=a+b
    def m2(a:Int,b:Int)=a*b
    def m3(a:Int,b:Int)=a-b

    List(m1 _,m2 _,m3 _).map(_.curried).map(_(2))
  }

  def exercise7={
    def timer[A](f: => A): A = {
      def now = System.currentTimeMillis
      val start = now; val a = f; val end = now
      println(s"Executed in ${end - start} ms")
      a
    }


    val n=1000000
    val uuids=timer{
      (1 to n).map(_=>UUID.randomUUID())
    }
    println(uuids.size)

  }

  def exercise8={
    def safe[A](operation: =>A):Option[A]={
      try{
        Some(operation)
      }catch{
        case _=> None
      }
    }

    safe{
      4/2
    }  // Some(2)

    safe{
      1/0
    }  //None
  }
}
