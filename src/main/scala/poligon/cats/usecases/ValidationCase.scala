package poligon.cats.usecases


object ValidationCase {

  import cats.Semigroup
  import cats.syntax.either._
  import cats.syntax.semigroup._

  final case class Check2[E : Semigroup,A](func: A => Either[E,A] ){
    def apply(a:A): Either[E, A] = func(a)
    def and(that: Check2[E,A]) : Check2[E,A] = Check2{ a =>
      (this(a),that(a)) match {
        case (Left(e1), Left(e2)) => (e1 |+| e2).asLeft
        case (Left(e), Right(_)) => e.asLeft
        case (Right(_), Left(e)) => e.asLeft
        case (Right(_), Right(_)) => a.asRight
      }
    }
  }

  import cats.instances.list._

  val a:Check2[List[String],Int] = Check2{ v =>
    if(v>2) v.asRight
    else List("Must be > 2").asLeft
  }

  val b:Check2[List[String],Int] = Check2{ v =>
    if(v < -2) v.asRight
    else List("Must be < -2").asLeft
  }

  val check2: Check2[List[String], Int] = a and b


//  val c:Check[Nothing,Int] = Check{v=>
//    v.asRight
//  }

  sealed trait Check[E, A] {
    def and(that: Check[E, A]): Check[E, A] = And(this, that)


    def apply(a: A)(implicit s: Semigroup[E]): Either[E, A] =
      this match {
        case Pure(func) => func(a)
        case And(left, right) =>
          (left(a), right(a)) match {
            case (Left(e1), Left(e2)) => (e1 |+| e2).asLeft
            case (Left(e), Right(_)) => e.asLeft
            case (Right(_), Left(e)) => e.asLeft
            case (Right(_), Right(_)) => a.asRight
          }
      }
  }

  final case class And[E,A](left:Check[E,A],right:Check[E,A]) extends Check[E,A]
  final case class Pure[E,A](func: A => Either[E,A]) extends Check[E,A]

  val c: Check[List[String],Int] = Pure{ v =>
    if(v>2) v.asRight
    else List("Must be > 2").asLeft
  }

  val d: Check[List[String], Int] =
    Pure { v =>
      if(v < -2) v.asRight
      else List("Must be < -2").asLeft
    }
  val check: Check[List[String], Int] =
    c and d

  def main(args: Array[String]): Unit = {
    println(check(5))
    println(check(0))
  }
}
