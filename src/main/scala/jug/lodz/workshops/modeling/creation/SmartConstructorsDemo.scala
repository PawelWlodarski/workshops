package jug.lodz.workshops.modeling.creation

import jug.lodz.workshops.WorkshopDisplayer

object SmartConstructorsDemo extends WorkshopDisplayer {

  def main(args: Array[String]): Unit = {
    header("Smart Constructors")
    title("DayOfWeek")

    section("DayOfWeek(1)",DayOfWeek.dayOfWeek(1))
    section("DayOfWeek(7)",DayOfWeek.dayOfWeek(7))
    section("DayOfWeek(-20)",DayOfWeek.dayOfWeek(-20))
    section("DayOfWeek(100)",DayOfWeek.dayOfWeek(100))


    title("Meetup")
    import MeetupModule._

    val meetupFactory: (String, Set[User], Option[DayOfWeek]) => Option[Meetup] =Meetup.meetup(4,Set(User("banned","ban@onet.pl")))

    val m1: Option[Meetup] =meetupFactory("bridge game",Set(User("user1","u1@gmail.com")),DayOfWeek.dayOfWeek(1))
    val m2: Option[Meetup] =meetupFactory("bridge game",Set(User("user1","u1@gmail.com")),DayOfWeek.dayOfWeek(6))
    val m3: Option[Meetup] =meetupFactory("bridge game",Set(User("user1","u1@gmail.com")),DayOfWeek.dayOfWeek(100))
    val m4: Option[Meetup] =meetupFactory("bridge game",Set(
      User("user1","u1@gmail.com"),
      User("user2","u1@gmail.com"),
      User("user3","u1@gmail.com"),
      User("user4","u1@gmail.com"),
      User("user5","u1@gmail.com")
    ),DayOfWeek.dayOfWeek(2))

    section("m1 - Correct",m1)
    section("m2 - Saturday",m2)
    section("m3 - Wrong day",m3)
    section("m1 - To Many People",m4)
  }


  sealed trait DayOfWeek {
    val value: Int
    override def toString = value match {
      case 1 => "Monday"
      case 2 => "Tuesday"
      case 3 => "Wednesday"
      case 4 => "Thursday"
      case 5 => "Friday"
      case 6 => "Saturday"
      case 7 => "Sunday"
    }
  }

  object DayOfWeek{
    private def unsafeDayOfWeek(d: Int) = new DayOfWeek { val value = d }
    private val isValid: Int => Boolean = { i => i >= 1 && i <= 7 }
    //more powerful types than option in workshops about ADT
    def dayOfWeek(d: Int): Option[DayOfWeek] = if (isValid(d))
        Some(unsafeDayOfWeek(d)) else None
  }


  object MeetupModule{
    private type MeetupCondition[A] = A => Boolean

    case class User(name:String,email:String)
    case class Meetup private(topic:String,us:Set[User],day:DayOfWeek)

    object Meetup {

      //Cyrrying - depdendency injection of configs
      def meetup(max: Int, banned: Set[User])(topic: String, us: Set[User], day: Option[DayOfWeek]) = {
        val userConditions: List[MeetupCondition[Set[User]]] = List(noBanned(banned), maxUsers(max))
        val dateConditions: List[MeetupCondition[Option[DayOfWeek]]] = List(notWeekends)

        //nicer construction will appear with monoids
        val validation = userConditions.map(c => c(us)) ++ dateConditions.map(c => c(day))

        //the consequence of using option is that we need to extract value fro m it
        //we can not use map because we will end with Option[Option[Meetup]]
        //we can not use map2 from Applicative because Meetup depends on day
        //we will see ways of composition in those situation during Monad workshop
        if (validation.contains(false)) None else Some(Meetup(topic, us, day.get))
      }

      private val noBanned: Set[User] => MeetupCondition[Set[User]] =
        banned => signed => banned.intersect(signed).isEmpty

      private val maxUsers: Int => MeetupCondition[Set[User]] =
        maxAllowed => us => us.size <= maxAllowed

      private val notWeekends: MeetupCondition[Option[DayOfWeek]] = day =>
        day.exists(d => d.value < 6)


    }
  }



}
