package poligon.cats

import cats.Monad
import cats.data.Validated

object SemigrupalExample {

  import cats.Semigroupal
  import cats.instances.option._
  import cats.instances.list._
  import cats.instances.string._
  import cats.instances.vector._
  import cats.syntax.validated._
  import cats.syntax.apply._
  import cats.data.NonEmptyVector
  import cats.syntax.either._

  type AllErrorsOr[A] = Validated[List[String],A]
  type AllStringErrorsOr[A] = Validated[String,A]

  case class User(name:String, age: Int)

  type FormData = Map[String,String]
  type FailFast[A] = Either[List[String],A]
  type FailSlow[A] = Validated[List[String],A]
  type NumE = NumberFormatException

  def getValue(name:String)(data:FormData): FailFast[String] =
    data.get(name).toRight(List(s"$name not specified"))

  def parseInt(name:String)(input:String) : FailFast[Int] =
    Either.catchOnly[NumE](input.toInt).leftMap(_ => List(s"$name must be an integer"))

  def nonBlank(name:String)(data:String): FailFast[String] =
    Right[List[String],String](data).ensure(List(s"$name cannot be blank"))(_.nonEmpty)

  def nonNegative(name:String)(data:Int): FailFast[Int] =
    Right[List[String],Int](data).ensure(List(s"$name cannot be blank"))(_ >= 0)


  def readName(data: FormData): FailFast[String] = getValue("name")(data).flatMap(nonBlank("name"))
  def readAge(data: FormData): FailFast[Int] =
    getValue("age")(data)
        .flatMap(nonBlank("age"))
        .flatMap(parseInt("age"))
        .flatMap(nonNegative("age"))

  def readUser(data:FormData): FailSlow[User] =
    (
      readName(data).toValidated,
      readAge(data).toValidated
    ).mapN(User.apply)



  def main(args: Array[String]): Unit = {
    val t1=(
      NonEmptyVector.of("Error 1").invalid[Int],
      NonEmptyVector.of("Error 2").invalid[Int]
    ).tupled

    println(t1)

    val r=Semigroupal[Option].product(Some(1),Some("abc"))

    println(r)

    println(Semigroupal.tuple3(Option(1),Option(2),Option(3)))
    println((Option(123),Option("abc")).tupled)


    val applied=(
      Option("Garfield"),
      Option(1978),
      Option("Orange & black")
    ).mapN(Cat.apply)

    println(applied)


    val errors=Semigroupal[AllErrorsOr].product(
      Validated.invalid(List("Error 1")),
      Validated.invalid(List("Error 2"))
    )

    println(errors)

    println(Semigroupal[AllStringErrorsOr])



  }




  import cats.syntax.flatMap._
  import cats.syntax.functor._

  def product[M[_]: Monad, A, B](x: M[A], y: M[B]): M[(A, B)] =
    x.flatMap(xp => y.map(yp => (xp,yp)))

  case class Cat(name: String, born: Int, color: String)
}
