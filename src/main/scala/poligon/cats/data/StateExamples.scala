package poligon.cats.data

object StateExamples {

  import cats.data.State

  object Parser{
    type CalcState[A] = State[List[Int],A]
    def evalOne(sym: String): CalcState[Int] =
      sym match {
        case "+" => operator(_ + _)
        case "-" => operator(_ - _)
        case "*" => operator(_ * _)
        case "/" => operator(_ / _)
        case num => operand(num.toInt)
      }


    def operand(num: Int): CalcState[Int] =
      State[List[Int], Int] { stack =>
        (num :: stack, num)
      }


    def operator(func: (Int, Int) => Int): CalcState[Int] =
      State[List[Int], Int] {
        case a :: b :: tail =>
          val ans = func(a, b)
          (ans :: tail, ans)
        case _ =>
          sys.error("Fail!")
      }

  }

  def main(args: Array[String]): Unit = {
    val a=State[Int,String]{state =>
      (state,s"the state is $state")
    }

    println(a.run(3).value)
    println(a.runS(3).value)
    println(a.runA(3).value)

    val step1=State[Int,String]{ num =>
      val ans = num + 1
      (ans,s"Result of step1 : $ans")
    }

    val step2=State[Int,String]{ num =>
      val ans = num + 1
      (ans,s"Result of step2 : $ans")
    }

    val both = for{
      a <- step1
      b <- step2
    } yield (a,b)

    println(both.run(20).value)

    import Parser._
    println(evalOne("42").runA(Nil).value)
  }

}
