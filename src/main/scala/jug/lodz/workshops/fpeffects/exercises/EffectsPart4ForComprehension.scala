package jug.lodz.workshops.fpeffects.exercises

/**
  * Created by pawel on 10.04.16.
  */
object EffectsPart4ForComprehension {

  object Demonstration {
    def demonstrate() = {
      println("-------------DEMONSTRATION-----------")
      println("[ADD ALL]map & flatMap")
      val some1 = Some(1)
      val some2 = Some(2)
      val some3 = Some(3)
      val some4 = Some(4)

      val result = some1.flatMap { a =>
        some2.flatMap { b =>
          some3.flatMap { c =>
            some4.map { d => a + b + c + d }
          }
        }
      }

      println("  -- result : " + result)
      println("[ADD ALL]for Comprehension")

      val resultComprehension = for {
        s1 <- some1
        s2 <- some2
        s3 <- some3
        s4 <- some4
      } yield s1 + s2 + s3 + s4
      println("  -- result comprehension : " + resultComprehension)

      println("[USER EXAMPLE]")
      case class Picture(asciiArt: String)
      case class Page(url: String, pictures: List[Picture])
      case class User(name: String, homePage: Option[Page])

      val john = User("John", Some(Page("www.john.com", List(Picture("♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪")))))
      val jane = User("Jane", None)

      val johnExampleResult: Option[String] = for {
        page <- john.homePage
        picture <- page.pictures.headOption
      } yield "  -- [john] comprehension : " + picture.asciiArt

      val janeExampleResult: Option[String] = for {
        page <- jane.homePage
        picture <- page.pictures.headOption
      } yield "  -- [jane] comprehension : " + picture.asciiArt

      johnExampleResult.foreach(println)
      janeExampleResult.orElse(Some("  -- [jane] comprehension empty")).foreach(println)
    }
  }

  //EXERCISE
  object Exercise {
    def join(o1: Option[String], o2: Option[String], o3: Option[String]): Option[String] = ???
  }

  def main(args: Array[String]) {
    Demonstration.demonstrate()

    //EXERCISE
    //    import Exercise._
    //    println("\n\n EXERCISES")
    //    println("\n\n [JOIN OPTIONS]")
    //    println(join(Some("aa"),Some("bb"),Some("cc"))==Some("aabbcc"))
    //    println(join(Some("aa"),None,Some("cc"))==None)

    //    println("\n\nADDITIONAL")
    //    import EffectsLibrary._
    //    println("[MAP2]")
    //    println(map2(Just(1),Just(2))(_+_)==Just(3))
    //    println(map2(Just(5),Just(5))(_+_)==Just(10))


    //    println("[SEQUENCE]")
    //    println(sequence(List(Just(1),Just(2),Just(3),Just(4))) == Just(List(1,2,3,4)))
    //    println(sequence(List(Just(1),Just(2),Empty,Just(4)))==Empty)

  }

  //ADDITIONAL
  object EffectsLibrary {

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


    //EXERCISE - use for comprehension
    def map2[A, B, C](m1: Maybe[A], m2: Maybe[B])(f: (A, B) => C): Maybe[C] = ???

    //use foldRight & map2   || flatMap & map & recursion
    def sequence[A](l: List[Maybe[A]]): Maybe[List[A]] = ???

    def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f
    def liftMaybe[A, B](f: A => B): Maybe[A] => Maybe[B] = ma => ma map f
  }

}
