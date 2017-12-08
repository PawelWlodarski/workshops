package poligon.cats

object ShowExample {

//  import cats.syntax.show._
//  import cats.instances.int._

  import cats._
  import cats.implicits._

  def main(args: Array[String]): Unit = {
    println(123.show)

    val d=Klaska("zawartosc klaski")

    println(d.show)
  }


  case class Klaska(value:String)

  implicit val dupaShow: Show[Klaska] = Show.show(k=>s"klaska is ${k.value}")
}
