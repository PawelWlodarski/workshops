package jug.lodz.workshops.old.intro


object Laziness {

  def main(args: Array[String]) {
    timerExample
  }


  def timerExample[A]: Unit = {
    def timer[A](f: => A): A = {
      def now = System.currentTimeMillis
      val start = now;
      val a = f;
      val end = now
      println(s"Executed in ${end - start} ms")
      a
    }

    val czas = timer {
      for (i <- 1 to 900000) yield i+1
    }
  }
}
