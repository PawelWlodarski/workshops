package jug.lodz.workshops.starter.adt

object SealedDemo {

  def main(args: Array[String]): Unit = {
    println(" *** Sealed Traits & ADTS")

    val example:SomeAbstractType = UniqueSubtype

    example match {
      case Implementation1(number) => println(s"number=$number")
      case OtherImplementation(text) => println(s"tekst=$text")
      case UniqueSubtype => println("unique") //show and explain warning
    }

    println(" *** closed/closed principle")
    //new way of designing types
    val some:MyOption[Int] = MySome(1)
    val result1=some.getOrElse(5)

    val none:MyOption[Int] = MyNone()
    val result2=none.getOrElse(100)

    println(s"result1 : $result1")
    println(s"result2 : $result2")

  }
}

//DIFFERENT CONSTRUCTORS SO MORE POWERFUL THAN ENUM!!!
sealed trait SomeAbstractType
final case class Implementation1(number: Int) extends SomeAbstractType
final case class OtherImplementation(text: String) extends SomeAbstractType
case object UniqueSubtype extends SomeAbstractType

sealed abstract class MyOption[A]{
  def getOrElse(other:A):A = this match {
    case MySome(a) => a
    case _ => other
  }
}
final case class MySome[A](a:A) extends MyOption[A]
final case class MyNone[A]() extends MyOption[A]  //to use objects we need understand more complex topics