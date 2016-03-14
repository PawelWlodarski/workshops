package jug.lodz.workshops.fp2.answers

/**
  * Created by pwlodarski on 2016-03-14.
  */
object FP2LoggerModuleAnswer {

  //LAB
  val createLogger: Logger => String => Unit = logger => msg => logger.info(msg)

  val logger: (String) => Unit = createLogger(DefaultLogger)

  trait Logger {
    def info(msg:String)=println(s"INFO : $msg")
    def error(msg:String)=println(s"ERROR : $msg")
  }

  object DefaultLogger extends Logger

  //ADDITIONAL
  sealed trait LogLevel
  object InfoLevel extends LogLevel
  object ErrorLevel extends LogLevel

  def createFullLogger(logger:Logger)(level: LogLevel)(msg:String)=level match{
    case InfoLevel => logger.info(msg)
    case ErrorLevel => logger.error(msg)
  }

  val logLevelFunction: (LogLevel) => (String) => Unit = createFullLogger(DefaultLogger)_
  val infoLevelFunction: (String) => Unit = createFullLogger(DefaultLogger)(InfoLevel)_



}
