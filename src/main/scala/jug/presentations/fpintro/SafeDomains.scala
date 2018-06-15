package jug.presentations.fpintro

object SafeDomains {


  def main(args: Array[String]): Unit = {
    println("test")
  }

  sealed trait Gender
  case object M extends Gender
  case object F extends Gender

  object Gender{
    def apply(s:String) = s match {
      case "M" => M
      case "F" => F
      case _ => throw new IllegalArgumentException(s"$s is wrong")
    }
  }

  def genderTypeSafe(g:Gender) = {


    val prefix: Gender => String = {
      case M => "Mr."
      case F => "Ms."
    }

    prefix(g)
  }

  //can not be local
  class Money private(val value:Int) extends AnyVal

  object Money{
    def apply(i:Int) : Money = {
      require(i > 0 , "some meaningful message")
      new Money(i)
    }
  }

  class Tax private(val value:Double) extends AnyVal

  object Tax{
    def apply(d:Double) : Tax = {
      require(d>0)
      new Tax(d)
    }
  }

  def moneyTypeSafe(m:Money)={

    val financialFormula : Money => Tax =
      m => Tax((m.value + 20) / m.value)


    financialFormula(m)
  }

}
