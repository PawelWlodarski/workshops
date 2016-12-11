package jug.lodz.workshops.starter.oop1

object StarterOOP1ClassesInScalaDemo {

  def main(args: Array[String]): Unit = {
    println(" *** SCALA STARTER OOP - DECLARING VALUES")

    //DECLARING JAVA CLASS
    //CODE: EXPLAIN TYPE POSITION AND INTERFERENCE (remove type)
    val userJava:UserJava=new UserJava("Zdzislawa", 25)  //no semicolon

//    userJava=new UserJava("Stanislaw", 30) ILLEGAL, reasignment to 'val' -> CODE; change to var

    println("\n *** Displaying java class : "+userJava)

    //CODE : create equivalent scala class- ScalaUser at the bottom of the file

    //TOME : show javap source ,
    val scalaUser: UserScala =new UserScala("Bogumila",18)

    println(s"\n *** Displaying java class : $scalaUser")


    class Example(_variable1:String){  //no val
      println("\n *** this is constructor in Example class")

      def variable=_variable1  //getter
      //TOME - explain method basics
      def getVariable={
        println("log")
        _variable1 //last statement is return , don't use return build in scala!!!
      }
    }

    //More similar to classical java
    class ExamplePrivateVariables{
      private var variable:String = ""
      def setVariabl(v:String):Unit= variable = v
    }

    // CODE : EXAMPLE CASES - show javap again
    val example=new Example("variable1")
    example.variable
//    example._variable1 //ILLEGAL


    //SHOW REPL
  }

}

//CODE : change val to var and show bytecode
class UserScala(val name: String, val age: Int) {
  override def toString = s"ScalaUser($name, $age)" // String interpolation!
}
