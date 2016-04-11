package jug.lodz.workshops.fpeffects.answers

/**
  * Created by pawel on 10.04.16.
  */
object EffectsPart3MultipleOptionsAnswer {

  object Demonstration{
    def demonstrate()={
      println("DEMONSTRATION")
      println("\n {DIVIDE BY HEAD OPTION}")
      val divideTenBy: Int=>Option[Int] = b => if(b!=0) Some(10/b) else None

      val list:List[Int]=List()
      val firstOptionalElement: Option[Int] = list.headOption

      val divisionResult: Option[Option[Int]] = firstOptionalElement.map(divideTenBy)
      println("  -- Option.map : "+divisionResult)

      val divisionResult2: Option[Int] = firstOptionalElement.flatMap(divideTenBy)
      println("  -- Option.flatMap : "+divisionResult2)

      println("\n {MULTIPLE OPTIONS}")
      //    divideTenBy(2).map(divideTenBy).map(divideTenBy).map(divideTenBy) --compilation error
      val resultMultipleOptions: Option[Int] = divideTenBy(2).flatMap(divideTenBy).flatMap(divideTenBy).flatMap(divideTenBy)
      println("  -- Multiple options : " + resultMultipleOptions)


      println("\n {COMPANY EXAMPLE}")
      case class Picture(asciiArt:String)
      case class Page(url:String,pictures:List[Picture])
      case class User(name:String, homePage:Option[Page])

      val john=User("John",Some(Page("www.john.com",List(Picture("♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪")))))
      val jane=User("Jane",None)

      john.homePage.map(p=>p.pictures.headOption).foreach(println)
      john.homePage.flatMap(p=>p.pictures.headOption).foreach(println)
      jane.homePage.flatMap(p=>p.pictures.headOption).foreach(println)
    }
  }

  object Exercise {
    object MeetupDomain{
      case class Picture(asciiArt:String)
      case class MeetupHistory(title:String,picture:Option[Picture])
      case class User(id:Int,name:String,email:String,history:List[MeetupHistory])
    }

    object UsersDAO{
      import MeetupDomain._
      val meetupHistory1=MeetupHistory("FP Scala",Some(Picture("SCALA_LOGO")))
      val meetupHistory2=MeetupHistory("FP Java",None)
      val user1=User(1,"FirstUser","firstEmail@domain.com",List(meetupHistory1,meetupHistory2))
      val user2=User(2,"SecondUser","secondEmail@domain.com",List())
      private val database=Map(1 -> user1, 2 -> user2)
      def find(id:Int):Option[User] = database.get(id)
    }

    object Conversions{
      import FrontEnd._
      import MeetupDomain._
      def userToHTML(u:User):HTML = s"""<a href="mailto:${u.email}">${u.name}</a>"""
      //EXERCISE
      def meetupHistoryToHTML(mh:MeetupHistory)= {
        val pictureHTML=mh.picture.map(p=>s"<img>$p<img>")
        s"""<h1>${mh.title}</h1>${pictureHTML.getOrElse("")}"""
      }
    }

    object FrontEnd{
      type HTML=String
      def displayPage(body:String):HTML=s"""<html><body>$body</body></html>"""
      def displayError(content:String):HTML=s"""<h1>THERE IS AN ERROR : $content</h1>"""
    }

    object Controller{
      import FrontEnd._
      import Conversions._
      import MeetupDomain._

      def userLastMeetup(id:Int):HTML= UsersDAO
        .find(id)
        .flatMap(_.history.headOption)
        .map(meetupHistoryToHTML)
        .map(displayPage)
        .getOrElse(displayPage("NEW USER : 0 MEETUPS"))

      def userLastMeetup2(id:Int):HTML={
          def handleExistingUser(u:User):Option[HTML] = u.history
            .headOption
            .map(meetupHistoryToHTML)
            .map(displayPage)
            .orElse(Some(displayPage("NEW USER : 0 MEETUPS")))

          UsersDAO.find(id).flatMap(handleExistingUser).getOrElse(displayError(s"there is no user with id = $id"))
      }
    }

  }

  def main(args: Array[String]) {
    Demonstration.demonstrate()

    //EXERCISE
    println("\n\n ----------EXERCISE----------------")
    val pageUser1 = Exercise.Controller.userLastMeetup(1)
    println(pageUser1)  // <html><body><h1>FP Scala</h1><img>Picture(SCALA_LOGO)<img></body></html>
    val pageUser2 = Exercise.Controller.userLastMeetup(2)
    println(pageUser2)  //<html><body>NEW USER : 0 MEETUPS</body></html>
    val pageUser3 = Exercise.Controller.userLastMeetup(3)
    println(pageUser3) //???

    println("\n\n Version 2")
    val pageUser3v2=Exercise.Controller.userLastMeetup2(3)
    println(pageUser3v2)  //Display Error Page

    println(Exercise.Controller.userLastMeetup2(1))  //Display Full Page
    println(Exercise.Controller.userLastMeetup2(2))  //Display New User Page


    println("\n\n ----------ADDITIONAL----------------")
    import EffectsLibrary._
    val maybeDivide:Int=>Int=>Maybe[Int] = a=>b=> if(b==0) Empty else Just(a/b)
    println(flatMap(Just(2))(maybeDivide(10))==Just(5))
    println(flatMap(Empty)(maybeDivide(10))==Empty)


    val divide:(Int,Int)=>Int = (a,b)=>a/b
    println(map2(Just(10),Just(2))(divide)==Just(5))
    println(map2(Empty,Just(2))(divide)==Empty)
    println(map2(Just(10),Empty)(divide)==Empty)
    println(map2(Empty,Empty)(divide)==Empty)
  }

  //ADDITIONAL
  object EffectsLibrary{
    def lift[A,B](f:A=>B):Option[A] => Option[B] = _ map f

    sealed trait Maybe[+A]
    case class Just[A](value:A) extends Maybe[A]
    case object Empty extends Maybe[Nothing]

    def flatMap[A,B](m:Maybe[A])(f:A=>Maybe[B]):Maybe[B]=m match{
      case Just(v) => f(v)
      case Empty => Empty
    }

    def map2[A,B,C](m1:Maybe[A],m2:Maybe[B])(f:(A,B)=>C):Maybe[C]=
        flatMap(m1){a=>
          map(m2){b=> f(a,b)}
        }

    def map[A,B](m:Maybe[A])(f:A=>B):Maybe[B] = m match {
      case Just(v) => Just(f(v))
      case Empty => Empty
    }

    def liftMaybe[A,B](f:A=>B):Maybe[A] => Maybe[B] = ma=> map(ma)(f)
  }
}
