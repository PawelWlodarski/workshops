package jug.lodz.workshops.fpeffects.exercises

/**
  * Created by pawel on 10.04.16.
  */
object EffectsPart3MultipleOptions {

  object Demonstration {
    def demonstrate() = {
      println("DEMONSTRATION")
      println("\n {DIVIDE BY HEAD OPTION}")
      val divideTenBy: Int => Option[Int] = b => if (b != 0) Some(10 / b) else None

      val list: List[Int] = List()
      val firstOptionalElement: Option[Int] = list.headOption

      val divisionResult: Option[Option[Int]] = firstOptionalElement.map(divideTenBy)
      println("  -- Option.map : " + divisionResult)

      val divisionResult2: Option[Int] = firstOptionalElement.flatMap(divideTenBy)
      println("  -- Option.flatMap : " + divisionResult2)

      println("\n {MULTIPLE OPTIONS}")
      //    divideTenBy(2).map(divideTenBy).map(divideTenBy).map(divideTenBy) --compilation error
      val resultMultipleOptions: Option[Int] = divideTenBy(2).flatMap(divideTenBy).flatMap(divideTenBy).flatMap(divideTenBy)
      println("  -- Multiple options : " + resultMultipleOptions)


      println("\n {COMPANY EXAMPLE}")
      case class Picture(asciiArt: String)
      case class Page(url: String, pictures: List[Picture])
      case class User(name: String, homePage: Option[Page])

      val john = User("John", Some(Page("www.john.com", List(Picture("♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪")))))
      val jane = User("Jane", None)

      john.homePage.map(p => p.pictures.headOption).foreach(println)
      john.homePage.flatMap(p => p.pictures.headOption).foreach(println)
      jane.homePage.flatMap(p => p.pictures.headOption).foreach(println)
    }
  }

  object Exercise {

    object MeetupDomain {

      case class Picture(asciiArt: String)

      case class MeetupHistory(title: String, picture: Option[Picture])

      case class User(id: Int, name: String, email: String, history: List[MeetupHistory])

    }

    object UsersDAO {

      import MeetupDomain._

      val meetupHistory1 = MeetupHistory("FP Scala", Some(Picture("SCALA_LOGO")))
      val meetupHistory2 = MeetupHistory("FP Java", None)
      val user1 = User(1, "FirstUser", "firstEmail@domain.com", List(meetupHistory1, meetupHistory2))
      val user2 = User(2, "SecondUser", "secondEmail@domain.com", List())
      private val database = Map(1 -> user1, 2 -> user2)
      def find(id: Int): Option[User] = database.get(id)
    }

    object Conversions {

      import FrontEnd._
      import MeetupDomain._

      def userToHTML(u: User): HTML = s"""<a href="mailto:${u.email}">${u.name}</a>"""
      //EXERCISE
      def meetupHistoryToHTML(mh: MeetupHistory) = ??? //"<h1>title</h1><img>image</img>"
    }

    object FrontEnd {
      type HTML = String
      def displayPage(body: String): HTML =s"""<html><body>$body</body></html>"""
      def displayError(content: String): HTML =s"""<h1>THERE IS AN ERROR : $content</h1>"""
    }

    object Controller {

      import Conversions._
      import FrontEnd._
      import MeetupDomain._

      //EXERCISE
      def userLastMeetup(id: Int): HTML = ???
      //UsersDAO.find(id) & history.headOption & meetupHistoryToHtml & displayPage || displayPageEmptyUser

    }

  }

  def main(args: Array[String]) {
    Demonstration.demonstrate()

    //EXERCISE
    //    println("\n\n ----------EXERCISE----------------")
    //    val pageUser1 = Exercise.Controller.userLastMeetup(1)
    //    println(pageUser1)  // <html><body><h1>FP Scala</h1><img>Picture(SCALA_LOGO)<img></body></html>
    //    val pageUser2 = Exercise.Controller.userLastMeetup(2)
    //    println(pageUser2)  //<html><body>NEW USER : 0 MEETUPS</body></html>
    //    val pageUser3 = Exercise.Controller.userLastMeetup(3)
    //    println(pageUser3) //???


    //    println("\n\n ----------ADDITIONAL----------------")
    //    import EffectsLibrary._
    //    val maybeDivide:Int=>Int=>Maybe[Int] = a=>b=> if(b==0) Empty else Just(a/b)
    //    println(flatMap(Just(2))(maybeDivide(10))==Just(5))
    //    println(flatMap(Empty)(maybeDivide(10))==Empty)
    //
    //
    //    val divide:(Int,Int)=>Int = (a,b)=>a/b
    //    println(map2(Just(10),Just(2))(divide)==Just(5))
    //    println(map2(Empty,Just(2))(divide)==Empty)
    //    println(map2(Just(10),Empty)(divide)==Empty)
    //    println(map2(Empty,Empty)(divide)==Empty)
  }

  //ADDITIONAL
  object EffectsLibrary {

    sealed trait Maybe[+A]

    case class Just[A](value: A) extends Maybe[A]

    case object Empty extends Maybe[Nothing]

    //EXERCISE
    def flatMap[A, B](m: Maybe[A])(f: A => Maybe[B]): Maybe[B] = ???

    //EXERCISE
    def map2[A, B, C](m1: Maybe[A], m2: Maybe[B])(f: (A, B) => C): Maybe[C] = ???

    def map[A, B](m: Maybe[A])(f: A => B): Maybe[B] = m match {
      case Just(v) => Just(f(v))
      case Empty => Empty
    }

    def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f
    def liftMaybe[A, B](f: A => B): Maybe[A] => Maybe[B] = ma => map(ma)(f)


  }

}
