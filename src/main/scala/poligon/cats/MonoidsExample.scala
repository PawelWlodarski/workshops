package poligon.cats

object MonoidsExample {

  import cats.Monoid
  import cats.instances.string._
  import cats.instances.int._
  import cats.instances.option._
  import cats.syntax.semigroup._

  def main(args: Array[String]): Unit = {
    val r1=Monoid[String].combine("Hi", " there")
    val r2=Monoid[Int].combine(1, 4)
    val r3=Monoid[Option[Int]].combine(Some(1), Some(7))

    println("\nsimple elements")
    println(r1)
    println(r2)
    println(r3)
    println(Monoid[String].empty)


    println("\noptions")
    val rs1=1 |+| 2
    val rs2=Option(1) |+| Option(2)

    println(rs1)
    println(rs2)

    println("\nadding lists")
    println(add(List(1,2,3)))
    println(add(List(Option(1),Option(3))))




  }

  def addInts(items:List[Int]) = items.foldLeft(Monoid[Int].empty)(_ |+| _)
  def add[A: Monoid](items:List[A]) = items.foldLeft(Monoid[A].empty)(_ |+| _)

}
