package jug.presentations.fpintro.examples

object Part3SealedEnum {

  def main(args: Array[String]): Unit = {
    val g: Gender =Gender("M")

    println(s"prefix : '${prefix(g)}'")

    try{Gender("AAA")} catch{
      case e: Throwable => println(s"exception while creating gender type : $e")
    }

    val stringToPrefix=(Gender.apply _).andThen(prefix)  //example of strange scala syntax, ETA expansion


    println(s"composed from 'M': ${stringToPrefix("F")}")


//    println(s"why FP doesn't like runtime exceptions: ${stringToPrefix("AAAA")}") //composition broken


  }


  sealed trait Gender
  case object M extends Gender
  case object F extends Gender

  object Gender{
    def apply(s:String) = s match {
      case "M" => M
      case "F" => F
      case _ => throw new IllegalArgumentException(s"$s can not be mapped to gender")
    }
  }

  //COMMENT OUT ONE AND SHOW WARNING
  val prefix: Gender => String = {
    case M => "Mr."
    case F => "Ms."
  }
}
