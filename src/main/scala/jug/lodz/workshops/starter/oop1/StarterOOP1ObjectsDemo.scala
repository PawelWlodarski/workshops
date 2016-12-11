package jug.lodz.workshops.starter.oop1

object StarterOOP1ObjectsDemo {

  def main(args: Array[String]): Unit = {
    println(" *** SCALA STARTER OOP - TRAITS & SINGLETON OBJECTS")

    println("   * You can implement java interfaces with scala classes")
    val scalaImplementation:JavaInterface=new ScalaImplementation("scalaValue")  // what will happen without explicit type??
    println("   * Scala implementation : \n"+scalaImplementation.toJson)

    //CALLING JAVA STATIC METHODS
    println("   * STATIC FROM JAVA 1 : "+StaticMethods.prefix("scala","printing"))
    println("   * STATIC FROM JAVA 2 (2*3): "+StaticMethods.multiply(2,3))

    //no 'static' keyword in scala, explain 'object' concept
    // TOME - show bytecode

    val created: ScalaInterface =FactoryExample.factoryMethod("factoryValue")
    println("   * standard factory method "+created.toJson())

    val fromApply: ScalaInterface =FactoryExample.apply("applyValue")
    val fromApplySugar=FactoryExample("applySynthaticSugar")
    println("   * apply factory method "+fromApply.toJson())
    println("   * synthatic sugar factory method "+fromApplySugar.toJson())
  }
}

class ScalaImplementation(value:String) extends JavaInterface{
  override def toJson: String =
    s"""{
       | "value" : '$value'
       |}""".stripMargin
}

trait ScalaInterface{
  def toJson():String
}

class SecondImplementation(value:String) extends ScalaInterface{
  override def toJson(): String = s"""{"value" : '$value'}"""
}

object FactoryExample{
  def factoryMethod(v:String):ScalaInterface = new SecondImplementation(v) //single letter variables and "clean code"
  def apply(v:String):ScalaInterface = new SecondImplementation(v)
}
