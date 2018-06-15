package jug.presentations.fpintro

object TypeConversions {

  def main(args: Array[String]): Unit = {

    println("testy")
  }


  def domains() = {
    type Domain1=String
    type Codomain1 = Int
    type Domain2 = Int
    type Codomain2= Boolean

    val F1 : Domain1 => Domain2 = s => s.length
    val F2 : Domain2 => Codomain2 =i => i <10


    val F12 = F1 andThen F2

    println(F12("aaa"))
    println(F12("aaaaaaaaaaaaaaaaaaaaaa"))
  }

  def partialProblems() = {
    class UserForm(val name:String, val geneder:String,val age:String, val salary:Int)

    class User(val name:String, val geneder:String, val age:Int, val salary:Int)

    trait Validator[A]{
      def check(a:A) : Boolean
    }

    object UserValidator extends Validator[UserForm]{
      override def check(form: UserForm): Boolean = {
        require(form.geneder == "M" || form.geneder == "K")
        require(form.age.toInt > 15 && form.age.toInt <  67)
        require(form.salary > 0)
        ???
      }
    }

    val domainUser:UserForm => User = ???


    ///logic
    val form:UserForm = ???

    if(UserValidator.check(form)){
      val user = domainUser(form)

      //domain logic
    }else{
      throw new RuntimeException("return 500")
    }


    //example 1
    val getAge: User => Int = _.age
    //explain currying
    val isMoreThan : Int => Int => Boolean = toCompare => arg => arg > toCompare

    val isAdult: User => Boolean = getAge andThen isMoreThan(17)

    val canSellBeer : User => Boolean = u => u.age > 17


    //example 2 - is as expression
    def choosePrefix(gender:String) : String =
      if(gender == "K") "Ms."
      else if(gender == "M") "Mr."
      else ???  //<- is it possible? What happen without this case? Why Any ?


    //example 3 salary
    //is this safe or we need to validate int?
    val financialFormula : Int => Double =
    salary => (salary + 20) / salary

    //
    def coreLogic(u:User) =  //are we safe here?
      financialFormula(u.salary)


    if(UserValidator.check(form)){
      val user = domainUser(form)

      coreLogic(user) //can this part break ?
    }else{
      throw new RuntimeException("return 500")
    }
  }


}
