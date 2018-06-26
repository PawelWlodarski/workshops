package jug.presentations.fpintro


import scala.util.{Failure, Success, Try}

object Codomains {

  class UserForm(val name: String, val geneder: String, val age: String, val salary: Int)

  class User(val name: String, val geneder: String, val age: Int, val salary: Int)

  val user = new User("", null, 0, -3000)

  trait Validator[A] {
    def check(a: A): Boolean
  }

  object UserValidator extends Validator[UserForm] {
    override def check(form: UserForm): Boolean = ???
  }

  val totalConversion: UserForm => Try[User] = form =>
    if (UserValidator.check(form)) Success(???) else Failure(new IllegalArgumentException("message"))


  class SafeUser private(val name: String, val geneder: String, val age: Int, val salary: Int)

  object SafeUser {

    private val validation1: UserForm => Boolean =
      form => form.geneder == "M" || form.geneder == "K"

    private val validation2: UserForm => Boolean =
      form => form.age.toInt > 15 && form.age.toInt <  67

    private val validations = List(validation1,validation2)

    def apply(form:UserForm): Try[SafeUser] = {
      val validationResult=validations.map(_(form)).reduce(_ && _)

      if(validationResult) Success(new SafeUser(form.name,???,???,???))
      else Failure(???)
    }
  }

  sealed trait BusinessResult

  //easy to unit test
  def domainLogic(u:SafeUser) : BusinessResult  = ???
  def errorRecovery(e:Throwable) : BusinessResult = ???

  val userForm : UserForm = ???

  val result: BusinessResult =SafeUser(userForm).fold(errorRecovery,domainLogic)


  val resultAlternative: BusinessResult = SafeUser(userForm) match {
    //total
    case Success(user) => domainLogic(user)
    case Failure(e) => errorRecovery(e)
  }

}
