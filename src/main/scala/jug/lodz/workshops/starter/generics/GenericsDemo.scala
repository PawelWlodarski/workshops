package jug.lodz.workshops.starter.generics

object GenericsDemo {

  def main(args: Array[String]): Unit = {

    println("\n *** Generics ***\n")
    val strings=List[String]("aaa","bbb")
 //    val strings2=List[String](1,"bbb")  <--- ERROR

    var map:Map[String,Int]=Map()

    map = map + ("key" -> 7)

    println(s" **** map : $map")

    def genericToString[T](arg : T) : String = arg.toString

    println(s" **** generic 7 ${genericToString(7)} ")
    println(s" **** generic false ${genericToString(false)} ")
    println(s" **** generic List ${genericToString(List(1,2,3))} ")

    //CUSTOM GENERIC CLASS wITH HIGH ORDER FUNCTION
    class Wrapper[A](element:A){
      def modify[B](f:A=>B):Wrapper[B] = new Wrapper[B](f(element))
    }

    val w=new Wrapper[String]("inside")
    val modified: Wrapper[Int] =w.modify(string => string.length)


    println("\n **** INVARIANCE & COVARIANCE ****")
    //SHOW JAVA EXAMPLE
    val s:List[String] = List("aaa","bbb")
    val i:List[Int]=List(1,2)

    val o1:List[AnyRef] = s
    val o2:List[Any] = i

    val sum: Seq[Any] =s :+ 1

    println(s" **** sum list $sum")

    class CovariantWrapper[+A](element:A){
      def modify[B](f:A=>B):Wrapper[B] = new Wrapper[B](f(element))
    }

    val cw=new CovariantWrapper[Int](100)
    val anyWrapper:CovariantWrapper[Any]=cw

    val covariantWrapperResult: Wrapper[String] =anyWrapper.modify(_.toString)

    println("\n **** HIGH KINDED TYPES ****")
    def printEither(e:Either[String,Int]) = println("printing either : "+e)

    def createRight:Either[Nothing,Int]=Right(500)
    def createLeft:Either[String,Nothing]=Left("AN_ERROR")

    printEither(createRight)
    printEither(createLeft)

    //ARRAYS - invariant
    println("\n **** ARRAYS ****")
    val arrayString=Array("aaa","bbb")
//    val arrayAny:Array[Any]=arrayString
    arrayString(0)="zzz"
    println(arrayString.mkString(","))

    //exercise - implement generic pair
    //exercise - implement def getPath(:Class[???]) : String
    //exercise - implement option
    //exercise b - option with map

  }

}
