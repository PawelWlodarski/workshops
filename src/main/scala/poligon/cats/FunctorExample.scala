package poligon.cats

import cats.Functor
import cats.instances.function._
import cats.syntax.functor._

import scala.language.higherKinds

object FunctorExample {

  import cats.instances.list._
  import cats.instances.option._

  def main(args: Array[String]): Unit = {
    println("functions")

    val func1: Int => Double = (x: Int) => x.toDouble
    val func2: Double => Double = (y: Double) => y * 2

    val r=(func1 map func2)(1) //"-Ypartial-unification" in build.sbt
    println(r)

    println("functors")
    val list1 = List(1,2,3)
    val list2 = Functor[List].map(list1)(_*2)

    val option1 = Option(123)
    val option2 = Functor[Option].map(option1)(_.toString)



    println(list2)
    println(option2)

    println("lift")
    val func = (x:Int) => x+1
    val liftedFunc=Functor[Option].lift(func)

    println(liftedFunc(option1))

    println("doMath")
    println(doMath(Option(1)))
    println(doMath(List(1,2,3)))



  }

  def doMath[F[_]](start:F[Int])(implicit functor: Functor[F]): F[Int] = start.map(n => n +1 *2)

}
