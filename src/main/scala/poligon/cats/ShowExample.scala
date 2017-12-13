package poligon.cats

import cats.Show
import cats.syntax.show._
import cats.instances.int._

object ShowExample {


  def main(args: Array[String]): Unit = {
    val d=Klaska("zawartosc klaski")

    println(123.show)
    println(d.show)
  }


  case class Klaska(value:String)

  implicit val dupaShow: Show[Klaska] = Show.show(k=>s"klaska is ${k.value}")
}
