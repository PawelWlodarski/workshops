package poligon.cats.usecases

import scala.concurrent.Future

object MapReduce {

  import cats.Monoid
  import cats.instances.int._
  import cats.instances.string._
  import cats.syntax.semigroup._

  def foldMap[A,B:Monoid](values:Vector[A])(func : A => B): B =
    values.map(func).foldLeft(Monoid[B].empty)(_ |+| _)

  def parallelFoldMap[A, B : Monoid](values: Vector[A])
  (func: A => B): Future[B] = {
    val numCores = Runtime.getRuntime.availableProcessors
    val groupSize = (1.0 * values.size / numCores).ceil.toInt

    val groups: Iterator[Vector[A]] = values.grouped(groupSize)

    val futures: Iterator[Future[B]] =
      groups map { group =>
        Future {
          group.foldLeft(Monoid[B].empty)(_ |+| func(_))
        }
      }

    // foldMap over the groups to calculate a final result:
    Future.sequence(futures) map { iterable =>
      iterable.foldLeft(Monoid[B].empty)(_ |+| _)
    }
  }

}
