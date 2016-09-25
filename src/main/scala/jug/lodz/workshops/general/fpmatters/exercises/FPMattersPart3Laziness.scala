package jug.lodz.workshops.general.fpmatters.exercises

/**
  * Created by pawel on 18.09.16.
  */
object FPMattersPart3Laziness {

  def main(args: Array[String]): Unit = {
    println("\n\n Functional Programming Matters Laziness \n")

    println("  *** Infinite Stream ***")

    val fromOne: Stream[Int] = Stream.from(1)
    //CODE - checking infinite stream
    //    println(s"    * Infinite Stream 5 elements ${fromOne.take(5)} ***")
    //    println(s"    * Infinite Stream 5 elements ${fromOne.take(5).force} ***")
    //    println(s"    * Infinite Stream 15 elements ${fromOne.take(15).force} ***")


    val firstNSum: Int => Int = n => Stream.from(1).take(n).foldRight(0)(_ + _)

    println(s"    * First n sum ${firstNSum} ***")
    //CODE - laziness and high order functions
    //    println(s"    * Odd numbers stream ${Stream.from(1).filter(_%2==1).take(10).force} ***")
    //    println(s"    * divisions stream ${Stream.from(1).map(i=>s"1/$i").take(10).force} ***")

    println(" \n *** Mixing infinity into collections ***")

    //CODE LAZY and EAGER
    //    List("Car","Tv","Banana").zip(Stream.from(1)).map{
    //      case (name,index) => s"<li id='product$index'>$name </li>"
    //    }.foreach(println)

    println(" \n  *** How to create stream ***")

    val list: List[Int] = 1 :: 2 :: 3 :: Nil
    val stream: Stream[Int] = 1 #:: 2 #:: 3 #:: Stream.empty

    //CODE - STREAM and LIST
    //    println(s"    * list vs stream ${list} vs ${stream} ***")

    println(" \n  *** Recursive infinite stream ***")

    //CODE - ITERATE STREAM VALUES
    //    println(s"    * odd numbers stream ${Stream.iterate(1)(_+2).take(10).force} ***")
    //    println(s"    * power of two stream ${Stream.iterate(1)(_*2).take(10).force} ***")

    println(" \n  *** A recursive stream withing an infinite stream (we need to go deeper)***")

    lazy val inceptionStream: Stream[Int] = 0 #:: 1 #:: inceptionStream.zip(inceptionStream.tail).map {
      case (n1, n2) =>
        println(s"inception stream phase ($n1,$n2)")
        n1 + n2
    } //is this sequence familiar?

    //CODE - RECURSIVE STREAM
    //    println(s"    * inception stream (take 15) ${inceptionStream.take(15).force} ***")


    println(" \n  *** Why functional programming matters - final example***")
    //square roots algorithm
    val next: Double => Double => Double = n => a => (a + n / a) / 2
    //notice currying
    val generateCandidates: (Double => Double) => Stream[Double] = Stream.iterate(1.0)

    lazy val rootCandidates = generateCandidates(next(2))
    println(s"    * square root 2 candidates : ${rootCandidates.take(10).force}")


    val withinEpsilon: (Double) => (Double, Double) => Boolean = eps => (n1, n2) => Math.abs(n2 - n1) < eps
    val negate: Boolean => Boolean = b => !b

    val rootOfTwo = rootCandidates.zip(rootCandidates.tail)
      .dropWhile(withinEpsilon(0.01).tupled andThen negate) // manipulate  epsilon
      .map(_._2)
      .head

    println(s"    * root of two (epsilon) : ${rootOfTwo}")

    val withinRelativeEpsilon: (Double) => (Double, Double) => Boolean = eps => (n1, n2) => Math.abs(n1 / n2 - 1) < eps

    val relativeEpsilonStream = rootCandidates.zip(rootCandidates.tail)
      .dropWhile(withinRelativeEpsilon(0.001).tupled andThen negate) // manipulate epsilon

    println(s"    * root of two (relative epsilon) not evaluated: ${relativeEpsilonStream}")
    println(s"    * root of two (relative epsilon) take(5) : ${relativeEpsilonStream.take(3).force}")

    //A SHORT INFO ABOUT MEMORY LEAKS
  }

  //foldRight i lazy eval  -- pierwszy element, który znajdzie warunek

}
