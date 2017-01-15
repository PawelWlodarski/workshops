package jug.lodz.workshops.starter.traits1

//AKKA TestKit example
object SelfAwareTypesDemo {

  def main(args: Array[String]): Unit = {

    val staticClockLogger=new DateLogger with StaticClock
    val randomClockLogger=new DateLogger with RandomClock

    staticClockLogger.debug("Static logger example")
    randomClockLogger.debug("Random Logger example")

    //show error
    //val error=new DateLogger {}
  }

}

trait BaseLogger{
  def debug(message:String):Unit = println("[DEBUG] : "+message)
}

trait Clock{
  def currentDate:String
}

trait StaticClock extends Clock{
  def currentDate:String = "12:59"
}

trait RandomClock extends Clock{
  private val random=new java.util.Random()

  def currentDate:String = "12:" + random.nextInt(60)
}

trait DateLogger extends BaseLogger { this:Clock =>
  override def debug(message: String): Unit = super.debug(message + " at "+ currentDate)
}
