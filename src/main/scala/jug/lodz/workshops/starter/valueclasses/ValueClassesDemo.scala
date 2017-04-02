package jug.lodz.workshops.starter.valueclasses

object ValueClassesDemo {

  def main(args: Array[String]): Unit = {
    //value classes
    println("\n *** value classes *** \n")
    val userId = new UserId(1)
    val age = new Age(28)
    val salary = new Salary(10000)

    val user=new User(1,1,1)

    val domainUser = new DomainUser(userId, age, salary)
    println(domainUser)

    //extension methods
    println("\n *** extension methods ***\n")
    import Extensions._
    "Standard text".display


    println("\n *** bottom borders of the type system ***\n")

    val a:Null=null
    val i:AnyRef=a

    println("class of null " + classOf[Null])

    def ???? : Nothing = throw new RuntimeException("not implemented")
//    val exercise1= ???
//    val exercise2= ????

    //mention about Nothing in generic types (ex. None)
  }
}

class User(val id: Int, val age: Int,val salary: Int)

class UserId(val value: Int) extends AnyVal

class Age(val number: Int) extends AnyVal

class Salary(val amount: Int) extends AnyVal

class DomainUser(val id: UserId, val age: Age, val salary: Salary){
  override def toString = s"DomainUser(id=${id.value}, age=${age.number}, salary=${salary.amount})"
}

// EXPLAIN WHY ANYVAL IS NEEDED!!
object Extensions{
  implicit class StringExtensions(base:String){
    def display=println(s"Extension : $base")
  }
}