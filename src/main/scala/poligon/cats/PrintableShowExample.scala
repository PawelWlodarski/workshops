package poligon.cats

import cats.Show
import cats._
import cats.implicits._

object PrintableShowExample {

  trait Printable[A]{
    def format(a:A):String
  }

  object Printable{
    def format[A](a:A)(implicit p:Printable[A]):String={
      p.format(a)
    }

    def print[A](a:A)(implicit p:Printable[A]):Unit = {
      println(p.format(a))
    }
  }

  object PrintableInstances{
    implicit val stringPrintable=new Printable[String]() {
      override def format(a: String): String = a
    }

    implicit val intPrintable= new Printable[Int] {
      override def format(a: Int): String = a.toString
    }

    implicit  val catPrintable=new Printable[Cat] {
      override def format(c: Cat): String = s"${c.name} is a ${c.age} tear old ${c.color}"
    }
  }

  object PrintableSyntax{
    implicit class PrintableOps[A](a:A){
      def format(implicit p:Printable[A]):String = p.format(a)
      def print(implicit p:Printable[A]):Unit = println(p.format(a))
    }
  }


  final case class Cat(name: String, age: Int, color: String)

  implicit val catsShow:Show[Cat]=Show.show(cat => s"kot ${cat.name}")


  def main(args: Array[String]): Unit = {
    import PrintableInstances._
    import PrintableSyntax._

    val c=Cat("Roman",20,"black")

    println(c.show)
  }

}
