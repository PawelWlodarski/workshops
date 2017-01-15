package jug.lodz.workshops.starter.traits1

//http://www.scala-lang.org/blog/2016/07/08/trait-method-performance.html
object StarterTraits1Demo {

  def main(args: Array[String]): Unit = {
    println(" *** SCALA STARTER TRAITS - MIXINS")

    val simpleImplementation7 = new StandardJava7Interface {
      override def methodToImplement(arg:String) = println(s"like in java 8 : $arg")
    }

    val simpleImplementation8 = new StandardJava8Interface {}

    val hasLogger=new SomeClass

    hasLogger.logger.debug("message to debug")
    hasLogger.logger.info(" message  with info")
  }

}

trait StandardJava7Interface{
  def methodToImplement(arg:String):Unit
}

trait StandardJava8Interface{
  def methodToImplement(arg:String):Unit={
    println(s"hello $arg")
  }
}


//orthogonal
trait Logger{
  def debug(message:String):Unit
  def info(message:String):Unit
}

class ConsoleLogger extends Logger{
  override def debug(message: String): Unit = println(s"[DEBUG] : $message")
  override def info(message: String): Unit = println(s"[INFO] : $message")
}

trait HasLogger{
    val logger= new ConsoleLogger
}

class SomeClass extends HasLogger
