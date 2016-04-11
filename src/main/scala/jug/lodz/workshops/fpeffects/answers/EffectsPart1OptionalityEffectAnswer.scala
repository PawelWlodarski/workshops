package jug.lodz.workshops.fpeffects.answers


/**
  * Created by pawel on 09.04.16.
  */
object EffectsPart1OptionalityEffectAnswer {

  //DEMONSTRATION
  object Demonstration{
    def firstElement1Exception(l : List[Int]):Int = l.head
    def firstElement1Null(l : List[Int]):Int = if (l.size > 0) l.head else null.asInstanceOf[Int]
    def firstElement1OrDefault(l : List[Int],default:Int):Int = if (l.size > 0) l.head else default
    def firstOptionalElement(l : List[Int]):Option[Int] = if (l.size > 0) Some(l.head) else None

    def demonstrate():Unit={
      println("EFFECTS : DEMONSTATION - HOW TO HANDLE MISSING VALUE")
      //////////////////////TRY
      println("1) first element of empty list with try")
      try{
        val firstElement=firstElement1Exception(List())  //are you forced to check if exception?
        println(s"  --  first element is $firstElement")
      }catch{
        case e:Exception => println("  --  there is no first element")
      }

      //////////////////////NULL
      println("2) first element of empty list with null")
      val firstElementOrNull=firstElement1Null(List()) // are you forced to check if null?
      if(firstElementOrNull != null.asInstanceOf[Int])
        println(s"  --  first element is $firstElementOrNull")
      else
        println("  --  there is no first element")

      //////////////////////DEFAULT
      println("3) first element of empty list with default")
      val firstElementOrDefault=firstElement1OrDefault(List(),-1) // what if there is no intuitive default?
        println(s"  --  first element is $firstElementOrDefault ...but maybe list was really empty, we don't know")

      //////////////////////OPTION
      println("4) first element of empty list with option")
      val optionalFirstElement:Option[Int]=firstOptionalElement(List())

      //not the best way of Option usage, we will learn better approach in the next exercise
      if(optionalFirstElement.isDefined)
        println(s"  --  first element is ${optionalFirstElement.get}")
      else
        println("  --  there is no first element")
    }
  }

  //EXERCISE
  object MeetupDomain{
    case class User(id:Int,name:String,email:String)
  }

  object UsersDAO{
    import MeetupDomain._
    private val database=Map(1 -> User(1,"OnlyUser","onlyEmail@domain.com"))
    def findOrNull(id:Int):User = if (database.contains(id)) database(id) else null
    @throws(classOf[Exception])
    def findOrException(id:Int):User = if (database.contains(id)) database(id) else throw new RuntimeException("break computation!")
    def findOrDefault(id:Int, defaultUser:User):User = if (database.contains(id)) database(id) else defaultUser //i co jest default?
    def findOption(id:Int):Option[User] = database.get(id)
  }

  object Conversions{
    import FrontEnd._
    import MeetupDomain._
    def userToHTML(u:User):HTML = s"""<a href="mailto:${u.email}">${u.name}</a>"""
  }

  object FrontEnd{
    type HTML=String
    def displayPage(body:String):HTML=s"""<html>
              <body>$body</body>
      </html>"""
    def displayError(content:String):HTML=s"""<h1>THERE IS AN ERROR : $content</h1>"""
  }

  //Additional
  def main(args: Array[String]) {
    Demonstration.demonstrate()

    //Exercises
    import Conversions._
    import FrontEnd._

    //PART1 - OPTION
    val optionalUser = UsersDAO.findOption(1)
    if(optionalUser.isDefined){
      displayPage(userToHTML(optionalUser.get))
    }else{
      displayError("There is no user with this id")
    }

    ///PART 2 - NULL
    val userOrNull=UsersDAO.findOrNull(1)
    if(userOrNull!=null){
      displayPage(userToHTML(userOrNull))
    }else{
      displayError("There is no user with this id")
    }

    //PART3 - EXCEPTION
    try{
      val user=UsersDAO.findOrException(1)
      displayPage(userToHTML(user))
    }catch{
      case e:Exception=>displayError("There is no user with this id")
    }

    //PART4 - DEFAULT
    val user=UsersDAO.findOrDefault(1,MeetupDomain.User(-1,"Not Existing","noexisting@domain.com"))
    if(user.id == -1){
      displayPage(userToHTML(user))
    }else{
      displayError("There is no user with this id")
    }



  }
}
