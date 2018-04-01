package poligon.cats

import cats.data.EitherT

import scala.concurrent.{Await, Future}


object MonadTransformersExample {

  import cats.data.OptionT
  import cats.instances.list._
  import cats.instances.either._
  import cats.instances.future._
  import cats.syntax.applicative._
  import cats.syntax.flatMap._
  import scala.concurrent.ExecutionContext.Implicits.global

  type ListOption[A] = OptionT[List, A]

  type ErrorOr[A] = Either[String, A]
  type ErrorOrOptional[A] = OptionT[ErrorOr, A]

  def main(args: Array[String]): Unit = {

    val r1: ListOption[Int] = OptionT(List(Option(10)))
    val r2: ListOption[Int] = 32.pure[ListOption]

    val r3 = r1.flatMap { x =>
      r2.map(y => x + y)
    }

    println(r3)

    val a = 10.pure[ErrorOrOptional]
    val b = 32.pure[ErrorOrOptional]
    val c = a.flatMap(x => b.map(y => x + y))
    println(a)
    println(c)


  }

  //  type Response[A] = Future[Either[String,A]]
  type Response[A] = EitherT[Future, String, A]

  def getPowerLevel(ally: String): Response[Int] = {
    powerLevels.get(ally) match {
      case Some(avg) => EitherT.right(Future(avg))
      case None => EitherT.left(Future(s"$ally unreachable"))
    }
  }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
    for{
      power1 <- getPowerLevel(ally1)
      power2 <- getPowerLevel(ally2)
    }  yield (power1 + power2) > 15

  import scala.concurrent.duration._
  def tacticalReport(ally1: String, ally2: String): String = {
    val stack: Future[Either[String, Boolean]] = canSpecialMove(ally1, ally2).value

    Await.result(stack,1 second) match {
      case Left(msg) => s"Comms error: $msg"
      case Right(true) =>
        s"$ally1 and $ally2 are ready to roll out!"
      case Right(false) =>
        s"$ally1 and $ally2 need a recharge."
    }

  }


  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )

}
