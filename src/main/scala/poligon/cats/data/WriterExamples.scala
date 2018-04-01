package poligon.cats.data

object WriterExamples {

  import cats.data.Writer
  import cats.instances.vector._
  import cats.syntax.applicative._
  import cats.syntax.writer._

  type Logged[A] = Writer[Vector[String],A]

  def main(args: Array[String]): Unit = {
    Writer(Vector("aaa","bbb"),1900)

    val p1 = 123.pure[Logged]
    val p2 = Vector("m1","m2","m3").tell
    val p3 = 123.writer(Vector("msg1", "msg2", "msg3"))

    println(p1)
    println(p2)
    println(p3)


    val compResult=for {
      a <- 10.pure[Logged]
      _ <- Vector("a","b","c").tell
      b <- 32.writer(Vector("x","y","z"))
    } yield a+b


    println(compResult.mapWritten(_.map(_.toUpperCase)))
    println(compResult.mapWritten(_.map(_.toUpperCase)).run)
  }

}
