package jug.lodz.workshops.fp2.exercises

/**
  * Created by pwlodarski on 2016-03-14.
  */
object FP2LoggerModule {

  //LAB
  val createLogger: Logger => String => Unit = ???

  val logger: (String) => Unit = createLogger(DefaultLogger)

  trait Logger {
    def info(msg:String):Unit=println(s"INFO : $msg")
    def error(msg:String):Unit=println(s"ERROR : $msg")
  }

  object DefaultLogger extends Logger

  //ADDITIONAL
  sealed trait LogLevel
  object InfoLevel extends LogLevel
  object ErrorLevel extends LogLevel

  def createFullLogger(logger:Logger)(level: LogLevel)(msg:String)= {
    ???
  }

  val logLevelFunction: (LogLevel) => (String) => Unit = createFullLogger(DefaultLogger)_
  val infoLevelFunction: (String) => Unit = createFullLogger(DefaultLogger)(InfoLevel)_


  def main(args: Array[String]) {
    //pattern matching example
    val a=1

    a match {
      case 0 => println("ZEEERO")
      case 1 => println("ONEEE")
    }
  }



}
