package poligon.cats

import java.util.Date
import java.util.concurrent.TimeUnit

object EqExample {

  import cats.Eq
  import cats.instances.int._
  import cats.instances.option._
  import cats.syntax.eq._
  import cats.syntax.option._


  def main(args: Array[String]): Unit = {
//    val eqInt=Eq[Int]



    checkDate()

    import Cat._
    import cats.instances.option._

    val o1=Option(cat1)
    val o2=Option.empty[Cat]

    println(o1 === o2)
    println(o1 =!= o2)


  }


  (Some(1):Option[Int]) === Option.empty[Int]


  1.some === none[Int]
  1.some =!= none[Int]

  def checkDate(){
    implicit val dateEq:Eq[Date] = Eq.instance[Date]{
      (date1,date2) => date1.getTime == date2.getTime
    }

    val x=new Date()
    TimeUnit.MILLISECONDS.sleep(100)
    val y = new Date()

    println(x === y)
    println(x === x)
  }
}


