package poligon.cats

object MonadExample {

  import cats.Monad
  import cats.instances.option._
  import cats.instances.list._

  def main(args: Array[String]): Unit = {
    val opt1 = Monad[Option].pure(3)
    val opt2 = Monad[Option].flatMap(opt1)(a=>Some(a+2))
    val opt3 = Monad[Option].map(opt2)(a=>100*a)

    val l1=Monad[List].pure(1)
    val l2=Monad[List].flatMap(List(1,2,3))(a=>List(a,a*10))
    val l3=Monad[List].map(l2)(a=>a+123)

    println(opt3)
    println(l1)
    println(l3)
  }

}
