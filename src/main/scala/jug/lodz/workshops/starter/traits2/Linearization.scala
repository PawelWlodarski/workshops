package jug.lodz.workshops.starter.traits2

object Linearization {

  def main(args: Array[String]): Unit = {
    println("\n *** traits linearization *** \n")

    val abStream = new BaseStream with StreamA with StreamB
    val baStream = new BaseStream with StreamB with StreamA

    println("  BA : " + abStream.method)
    println("  AB : "+baStream.method)

    println("\n *** traits linearization with Inheritence*** \n")

    val cbaStream = new BaseStream with StreamC with StreamB with StreamA
    println("  CBA : "+cbaStream.method)


    val abcStream = new BaseStream with StreamA with StreamB with StreamC
    println("  ABC : "+abcStream.method)


    //FIX
//    println("\n *** abstract override ?!?!?*** \n")
//    val reader=new StandardReader with DiagnosticRead
//    reader.read
  }
}

class BaseStream() {
  def method:String="Base"
}

trait StreamA extends BaseStream{
  override def method:String="A,"+super.method
}

trait StreamB extends BaseStream{
  override def method:String="B,"+super.method
}


trait StreamC extends StreamA{
  override def method:String="C,"+super.method
}


abstract class DemoReader{
  def read:Char
}

class StandardReader extends DemoReader{
  override def read: Char = scala.io.StdIn.readChar()
}

//FIX - abstract override
//trait DiagnosticRead extends DemoReader{
//   def read:Char={
//    val c=super.read
//    println(s"reading $c")
//    c
//  }
//}