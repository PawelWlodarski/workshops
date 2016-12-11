package jug.lodz.workshops.fpeffects.answers

import java.util.concurrent.TimeUnit

import cats.Apply

import scala.concurrent.{Await, Future}
import scala.util.{Try, Failure, Success}

/**
  * Created by pawel on 10.04.16.
  */
object EffectsPart7OtherEffectsAnswer {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  object DemonstrationTime {
    def demonstrate() = {
      println("[DEMONSTRATION] TIme Effect")
      val comprehensionResult = for {
        r1 <- Service1.call(1)
        r2 <- Service2.call(r1)
      } yield r2

      comprehensionResult.onSuccess {
        case v => println(s"  -- comprehensionResult : $v")
      }
      TimeUnit.SECONDS.sleep(2)

      println("[DEMONSTRATION] sequence")
      val sequence: Future[List[Int]] = Future.sequence(List(Service1.call(1), Service2.call(2)))

      sequence.onComplete {
        case Success(list) => println(list)
        case Failure(e) => println(e)
      }

      Await.result(sequence, 2.second)
    }

    object Service1 {
      def call(input: Int): Future[Int] = Future {
        TimeUnit.MILLISECONDS.sleep(500)
        input + 1
      }
    }

    object Service2 {
      def call(input: Int): Future[Int] = Future {
        TimeUnit.MILLISECONDS.sleep(500)
        input + 2
      }
    }

  }

  object DemonstrateNonDeterminism {
    def demonstrate() = {
      println("[DEMONSTRATE] Non-Determinism")
      println("  -- sqrt(4.0) : " + Math.sqrt(4.0))
      println("  -- 2^2 : " + Math.pow(2.0, 2))
      println("  -- -2^2 : " + Math.pow(-2.0, 2))

      //f(x)=y
      val allRoots: Double => List[Double] = input => {
        val result = Math.sqrt(input)
        List(-result, result)
      }

      println("-- handling non determinism1 : " + allRoots(4.0).map(Math.abs).head)
      println("-- handling non determinism2 : " + allRoots(4.0).filter(_ > 0).head)

      println("[DEMONSTRATE] real example")
      case class Purchase(productName: String, date: String, profit: Int)

      val purchasesOnDate: String => List[Purchase] = _ match {
        case ("01-01-2016") => List(Purchase("tv", "01-01-2016", 300), Purchase("console", "01-01-2016", 200))
      }

      println("  -- counting profit - moving from set of possible values into a single value")
      val singleProfit = purchasesOnDate("01-01-2016").map(_.profit).reduce(_ + _)
      println("  -- singleProfit : " + singleProfit)
    }
  }


  object Exercise {

    case class Driver(name: String)

    case class Car(model: String)

    val drivers = List(Driver("John"), Driver("Jane"), Driver("Kubica"))
    val cars = List(Car("Polonez"), Car("Porsche"))

    def combinations(): List[(String, String)] = for {
      car <- cars
      drivers <- drivers
    } yield (drivers.name, car.model)
  }

  def main(args: Array[String]) {
    //    DemonstrationTime.demonstrate()
    //    DemonstrateNonDeterminism.demonstrate()

    println("\n\nExercises")
    import Exercise._
    println(combinations() == List(("John", "Polonez"), ("Jane", "Polonez"), ("Kubica", "Polonez"),
      ("John", "Porsche"), ("Jane", "Porsche"), ("Kubica", "Porsche")))

    println("\n\nADDITIONAL SMALL HARDCORE")

    import EffectsLibrary._

    println(mapApp(Some(1))(_ + 1) == Some(2))
    println(map2App(Some(1), Some(2))(_ + _) == Some(3))

    println("\n\n♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ADDITIONAL HARDCORE ♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪")
    import cats._
    import cats.implicits._
    //      implicit val applicativeOption:Apply[Option] = Apply[Option]
    //      implicit val applicativeList:Apply[List] = Apply[List]
    println(universalMap3(Option(1), Option(2), Option(3))(_ + _ + _) == Some(6))
    println(universalMap3(List(1, 2), List(2, 3), List(3))(_ + _ + _) == List(6, 7, 7, 8))
  }

  object EffectsLibrary {

    def map2[A, B, C](t1: Option[A], t2: Option[B])(f: (A, B) => C): Option[C] = for {
      a <- t1
      b <- t2
    } yield f(a, b)


    def map2[A, B, C](t1: Try[A], t2: Try[B])(f: (A, B) => C): Try[C] = for {
      a <- t1
      b <- t2
    } yield f(a, b)

    def map2[A, B, C](m1: Maybe[A], m2: Maybe[B])(f: (A, B) => C): Maybe[C] = for {
      a <- m1
      b <- m2
    } yield f(a, b)


    //ADDITIONAL
    val applicative1ArgInt: (Option[Int => Int]) => (Option[Int] => Option[Int]) = function => firstOption =>
      map2(function, firstOption)((f, argument) => f(argument))

    def mapApp(o1: Option[Int])(f: Int => Int): Option[Int] = applicative1ArgInt(Option(f))(o1)

    val applicative2ArgIntInt: (Option[Int => Int => Int]) => (Option[Int] => Option[Int => Int]) = function => firstOption =>
      map2(function, firstOption)((f, argument) => f(argument))

    def map2App(o1: Option[Int], o2: Option[Int])(f: (Int, Int) => Int): Option[Int] = {
      val optionIntInt: Option[(Int) => Int] = applicative2ArgIntInt(Option(f.curried))(o1)
      applicative1ArgInt(optionIntInt)(o2)
    }


    //ADDITIONAL HARDCORE
    def applicative[A, B](fab: Option[A => B])(oa: Option[A]): Option[B] = map2(fab, oa)((f, arg) => f(arg))

    def mapAppGeneric[A, B](o1: Option[A])(f: A => B): Option[B] = applicative(Some(f))(o1)
    def map2AppGeneric[A, B, C](o1: Option[A], o2: Option[B])(f: (A, B) => C): Option[C] = applicative(applicative(Some(f.curried))(o1))(o2)
    def map3AppGeneric[A, B, C, D](o1: Option[A], o2: Option[B], o3: Option[C])(f: (A, B, C) => D): Option[D] =
      applicative(applicative(applicative(Some(f.curried))(o1))(o2))(o3)

    def universalMap2[A, B, C, M[_]](m1: M[A], m2: M[B])(fab: (A, B) => C)(implicit applicative: Apply[M]): M[C] =
      applicative.map2(m1, m2)(fab)

    def universalMap3[A, B, C, D, M[_]](m1: M[A], m2: M[B], m3: M[C])(fabc: (A, B, C) => D)(implicit applicative: Apply[M]): M[D] =
      applicative.map3(m1, m2, m3)(fabc)


    //OLD
    def sequence[A](l: List[Maybe[A]]): Maybe[List[A]] =
      l.foldRight[Maybe[List[A]]](Just(List.empty[A]))((a, b) => map2(a, b)((a, b) => a :: b))

    def sequence[A](l: List[Try[A]]): Try[List[A]] =
      l.foldRight[Try[List[A]]](Try(List.empty[A]))((a, b) => map2(a, b)((a, b) => a :: b))


    def map3[A, B, C, D](m1: Maybe[A], m2: Maybe[B], m3: Maybe[C])(f: (A, B, C) => D): Maybe[D] = for {
      a <- m1
      b <- m2
      c <- m3
    } yield f(a, b, c)

    def map3[A, B, C, D](m1: Option[A], m2: Option[B], m3: Option[C])(f: (A, B, C) => D): Option[D] = for {
      a <- m1
      b <- m2
      c <- m3
    } yield f(a, b, c)


    sealed trait Maybe[+A] {
      def flatMap[B](f: A => Maybe[B]): Maybe[B] = this match {
        case Just(v) => f(v)
        case Empty => Empty
      }

      def map[B](f: A => B): Maybe[B] = this match {
        case Just(v) => Just(f(v))
        case Empty => Empty
      }
    }

    case class Just[A](value: A) extends Maybe[A]

    case object Empty extends Maybe[Nothing]


    def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f
    def liftMaybe[A, B](f: A => B): Maybe[A] => Maybe[B] = ma => ma map f
  }

}
