package jug.lodz.workshops.fp2.answers

import org.scalatest.{Matchers, FlatSpec}

import scala.collection.mutable.ListBuffer

/**
  * Created by pwlodarski on 2016-03-14.
  */
class FP2LoggerModuleAnswerSpec extends FlatSpec with Matchers{
  import FP2LoggerModuleAnswer._

  "Each logged message" should "be logged with INFO prefix" in {
      val loggerWithMemory=new TestableLogger()
      val logger=createLogger(loggerWithMemory)

      logger("msg1")
      logger("msg2")

      loggerWithMemory.messages() should contain allOf("INFO : msg1", "INFO : msg2")
  }

  "General logger" should "log messages with proper prefix" in {
    val loggerWithMemory=new TestableLogger()
    val logger= createFullLogger(loggerWithMemory) _

    logger(InfoLevel)("msg1")
    logger(ErrorLevel)("msg2")

    loggerWithMemory.messages() should contain allOf("INFO : msg1", "ERROR : msg2")
  }


  class TestableLogger extends Logger{
    private val _messages: ListBuffer[String] = ListBuffer()

    override def info(msg: String): Unit = _messages += s"INFO : $msg"

    override def error(msg: String): Unit = _messages += s"ERROR : $msg"

    def messages():List[String] = _messages.toList
  }
}
