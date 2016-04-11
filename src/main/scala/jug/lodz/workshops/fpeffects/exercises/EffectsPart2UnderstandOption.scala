package jug.lodz.workshops.fpeffects.exercises

/**
  * Created by pawel on 09.04.16.
  */
object EffectsPart2UnderstandOption {

  object Demonstration {
    def demonstrate() = {
      val some: Option[Int] = Some(7)

      println("[OPTION] : isDefined & get (bad practice)")
      if (some.isDefined) {
        println(s"  -- very bad way way to extract value from ${some} : ${some.get}")
      }

      println("[OPTION] : map & foreach")
      val multiplyByTwo: Int => Int = i => i * 2 //is this function pure ?
      val display: Int => String = i => s"  -- RESULT : $i" // does this function have any knowledge about context?

      some.map(multiplyByTwo).foreach(i => println(s"  -- multiplied by two : $i"))
      some.map(multiplyByTwo).map(display).foreach(println)

      println("[OPTION] : filter & getOrElse & orElse")
      val default = some.filter(_ > 10).getOrElse(-1)
      println(s"  -- default value $default")
      some.filter(_ > 10).orElse(Some(-1)).map(i => s" -- filter(remove) & orElse -1 : i=$i").foreach(println)
      some.filter(_ < 10).orElse(Some(-1)).map(i => s" -- filter & orElse : i=$i").foreach(println)

      println("[OPTION] : pattern matching")
      some match {
        case Some(value) => println(s"  -- pattern matching value : $value")
        case None => println(s"  --  pattern matching is empty")
      }

      println("[OPTION] : fold")
      val onSuccess: Int => String = i => s"business value $i"
      val onError: String = "raise an error"
      val result = some.fold(onError)(onSuccess)
      println(s"  -- fold result : $result")
    }
  }

  object Exercise {

    object MeetupDomain {

      case class User(id: Int, name: String, email: String)

    }

    object UsersDAO {

      import MeetupDomain._

      private val database = Map(1 -> User(1, "OnlyUser", "onlyEmail@domain.com"))
      def find(id: Int): Option[User] = database.get(id)
    }

    object Conversions {

      import FrontEnd._
      import MeetupDomain._

      def userToHTML(u: User): HTML = s"""<a href="mailto:${u.email}">${u.name}</a>"""
    }

    object FrontEnd {
      type HTML = String
      def displayPage(body: String): HTML =s"""<html><body>$body</body></html>"""
      def displayError(content: String): HTML =s"""<h1>THERE IS AN ERROR : $content</h1>"""
    }

  }

  def main(args: Array[String]) {
    //DEMONSTRATION
    Demonstration.demonstrate()

    //EXERCISE
    println("\n\n-------------------EXERCISE---------------------")
    import Exercise._
    import Conversions._
    import FrontEnd._
    val html1 = UsersDAO.find(1) //use map & fold
    val html2 = UsersDAO.find(2) //use map & fold

    println("[ID=2] - below one line with domain transformation")
    UsersDAO.find(2) //use map & orElse & foreach to display result
    val html3 = UsersDAO.find(2) //use map & getOrElse


    println(s"[ID=1] : $html1")
    println(s"[ID=2] : $html2")
    println(s"[ID=2] : $html3")

    //    println("\n\nADDITIONAL - LIFT")
    //    import EffectsLibrary._
    //    def absOptional: (Option[Int]) => Option[Int] =lift(Math.abs)
    //    println(s"  -- optional abs None :${absOptional(None)} ")
    //    println(s"  -- optional abs Some :${absOptional(Some(-7))} ")
    //
    //
    //    println("\n\nADDITIONAL - MAYBE")
    //
    //    println(map(Just(7))(_*2))
    //    println(mapDifferentSignature((i:Int)=>i*2)(Just(7)))
    //    println(liftMaybe(Math.abs)(Just(-7)))

  }

  //ADDITIONAL
  object EffectsLibrary {
    def lift[A, B](f: A => B): Option[A] => Option[B] = ???

    sealed trait Maybe[+A]

    case class Just[A](value: A) extends Maybe[A]

    case object Empty extends Maybe[Nothing]

    def map[A, B](m: Maybe[A])(f: A => B): Maybe[B] = ???

    def mapDifferentSignature[A, B](f: A => B)(m: Maybe[A]): Maybe[B] = ???

    def liftMaybe[A, B](f: A => B): Maybe[A] => Maybe[B] = ???

  }

}
