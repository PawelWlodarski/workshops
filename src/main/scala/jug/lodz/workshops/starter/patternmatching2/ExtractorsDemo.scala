package jug.lodz.workshops.starter.patternmatching2

object ExtractorsDemo {

  def main(args: Array[String]): Unit = {
    println(" ** SCALA STARTER EXTRACTORS")

    val instance = ClassWithPrivateState("initial") //no 'new' -> companion object

    instance.add("_added")
    instance.drop(3)

    instance match {
      case ClassWithPrivateState(extracted) => println(s"extracted state $extracted")
    }

    println("\n ** Simple extractor ** ")
    val i = 2
    i match {
      case SimpleEvenExtractor(i) => println(s"$i is even")
      case other => println(s"$other is not even")
    }

    println("\n ** Name Extractor ** ")

    "Jan Kowalski" match {
      case NameExtractor(name, surname) => println(s"extracted name=$name, surname=$surname")
    }

    "single" match {
      case NameExtractor(name, surname) => println(s"extracted name=$name, surname=$surname")
      case other => println(s"not a name $other")
    }

    println("\n ** Boolean Extractor ** ")

    "lower" match {  //change to LOWER
      case UpperCase() => println("lower is UPPER?")
      case _ => println("lower is lower")
    }
  }


  //exercises
  // email extractor
  //def unapply(str: String): Option[(String, String)]
  //snapshot extractor
  // is email from domain : Domain("com"
}


class ClassWithPrivateState(initial: String = "") {
  private var mutableState: String = initial

  def add(chars: String): Unit = mutableState = mutableState + chars
  def drop(howMany: Int) = mutableState = mutableState.drop(howMany)

}

object ClassWithPrivateState {
  def apply(initial: String = ""): ClassWithPrivateState = new ClassWithPrivateState(initial)
  def unapply(arg: ClassWithPrivateState): Option[String] = Some(arg.mutableState)
}

object SimpleEvenExtractor {
  def unapply(i: Int) = if (i % 2 == 0) Some(i) else None
}

object NameExtractor {
  def unapply(fullName: String): Option[(String, String)] = {
    val parts: Array[String] = fullName.split(" ")
    if (parts.length == 2) Some(parts(0), parts(1)) else None //tuple synthatic sugar!
  }
}

object UpperCase {
  def unapply(s: String): Boolean = s.toUpperCase == s
}