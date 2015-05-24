package jug.lodz.workshops.FP.introduction


object ThinkInTypes {

  def main(args: Array[String]) {

    //change value, preserve type
    val f1: Int=>Int = i=>i+1
    //check logical condition, change Type
    val f2: Int=>Boolean = _>5

    //high order function with generic Type
    //what is the type of cond?
    def f3[A](a:A,cond:A=>Boolean)= cond(a)

    //If function can throw exception don't hide this fact from user, it's part of the type
    val f4:(Int,Int)=>Option[Int]= (a,b) => if(b==0) None else Some(a/b)

    //Or even better
    import scala.util.Try
    val f5:(Int,Int)=>Try[Int]= (a,b)=>Try(a/b)

    //and our OOP friends are just special functions with special types
    //getter
    val get:Unit=>Int= _=>7
    //setter
    val set:Int=>Unit = a=>println(a)

    //and we can go even further
    phantomTypesExample
  }

  def phantomTypesExample: Unit ={
    sealed trait Status
    trait Created extends Status
    trait Notified extends Status

    case class User[A <: Status](name:String)
    def newUser(name:String)=User[Created](name)

    def notify(u:User[Created]):User[Notified]= u.copy[Notified]()
    def securedLogic(u:User[Notified])=println(s"this is secured during compilation : $u")

    val u=newUser("Roman")

   // this lines doesn't compile = check why
   // securedLogic(u)
    val notifiedUser=notify(u)
    securedLogic(notifiedUser)

  }
}
