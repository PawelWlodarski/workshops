package jug.lodz.workshops.fpeffects.exercises

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * Created by pawel on 10.04.16.
  */
object EffectsPart5ErrorEffect {

  object Demonstration{

    def demonstrate()={
      println("DEMONSTRATE")
      println("[TRY] Reading File")

      val goodPath="/fpjava/purchases.csv"
      val badPath="notExisting.csv"
      val goodResult: Try[List[String]] = readLines(goodPath)
      val badResult: Try[List[String]] = readLines(badPath)

      println("  -- Display File")
      goodResult.getOrElse(List()).foreach(println)
      badResult.getOrElse(List()).foreach(println)


      println(" \n -- Recover Good")
      goodResult.recover{
        case e:Throwable => List(s"something went wrong  : [${e.getMessage}]")
      }.get.foreach(println)

      println(" \n -- Recover Bad")
      badResult.recover{
        case e:Exception =>  List(s"something went wrong  : [${e.getMessage}]")
      }.get.foreach(println)

      println(s"\n  -- transform Good")
      val businessFunction:List[String] => Try[Int] = l=> Try(l.length)
      val errorHandler : Throwable => Try[Int] = _ => Try(0)
      goodResult.transform(businessFunction,errorHandler).map(i=>s"file length $i").foreach(println)

      println(s"\n  -- transform Bad")
      badResult.transform(businessFunction,errorHandler).map(i=>s"file length $i").foreach(println)

      println(s"\n  -- pattern matching")
      goodResult match {
        case Success(lines) => println(s"file size PM : "+lines.size)
        case Failure(exception) => throw exception
      }
    }

    def readLines(path:String):Try[List[String]]= Try{
      Option(getClass().getResource(path)).map{r=>
        val source = Source.fromURL(r)
        source.getLines().toList
      }.getOrElse(throw new RuntimeException(s"there is no file under path : $path"))

    }

  }

  //EXERCISE
    object Exercise {
      def stringToInt(s: String): Try[Int] = Try(s.toInt)
      def tryToAdd(s1: String, s2: String, s3: String, s4: String) :Try[String] = ???

    }

  def main(args: Array[String]) {
    Demonstration.demonstrate()

//    import Exercise._
//    println("\n\n   ---- EXERCISES ----  ")
//    println(tryToAdd("1","2","3","4")==Try(10))
//    println(tryToAdd("1","2","♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪","4").isFailure==true)


//    println("\n\n   ---- ADDITIONAL ----  ")
//    import EffectsLibrary._
//    println(map2(Try(1),Try(2))(_+_)==Try(3))
//    println(sequence(List(Try(1),Try(2),Try(3)))==Try(List(1,2,3)))
//    println(map3(Just(5),Just(5),Just(5))(_+_+_)==Just(15))
  }


  object EffectsLibrary {

    //ADDITIONAL
    def map2[A,B,C](t1:Try[A],t2:Try[B])(f:(A,B)=>C):Try[C] = ???

    def map2[A, B, C](m1: Maybe[A], m2: Maybe[B])(f: (A, B) => C): Maybe[C] = for {
      a <- m1
      b <- m2
    } yield f(a, b)

    def sequence[A](l: List[Maybe[A]]): Maybe[List[A]] =
      l.foldRight[Maybe[List[A]]](Just(List.empty[A]))((a, b) => map2(a, b)((a, b) => a :: b))

    def sequence[A](l: List[Try[A]]): Try[List[A]] = ???


    def map3[A, B, C,D](m1: Maybe[A], m2: Maybe[B],m3:Maybe[C])(f: (A, B,C) => D): Maybe[D] = ???


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
