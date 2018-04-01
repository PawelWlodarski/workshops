package poligon.cats

import cats.Eval

object FoldableExample {

  import cats.Foldable
  import cats.instances.list._
  import cats.instances.stream._

  def main(args: Array[String]): Unit = {
    val ints = List(1,2,3)
    val r=Foldable[List].foldLeft(ints,0)(_+_)

    val eval = Foldable[Stream].foldRight(bigData,Eval.now(0L)){(num,eval) =>
      eval.map(_ + num)
    }



    println(r)
    println(eval.value)
  }

  def bigData = (1 to 100000).toStream

}
