package jug.lodz.workshops.quality

object WartRemoverExamples {

  final val dupa="a"

  def main(args: Array[String]): Unit = {
    //arrayAreNotEqualRule()
    //inferAny()
    //instanceChecking()
//    defaultArguments()
//    eitherProjection()
//    safeEquals()
//    finalCaseClass()
//    implicitTypeConversion()
    //nothingInference()

  }

  /***
    * ARRAYS ARE NOT EQUAL
    */
  def arrayAreNotEqualRule()={
    val arr1=Array(1,2,3)
    val arr2=Array(1,2,3)
    println(
      s"""ARRAYS ARE NOT EQUALS :
         |arr1 : ${arr1.mkString(",")}, arr2 : ${arr2.mkString(",")},
         |but : arr1 == arr2 ${arr1 == arr2}
         |at the same time List(1,2,3)==List(1,2,3) ${List(1,2,3)==List(1,2,3)}""".stripMargin)
  }

  /***
    * INFER ANY
    */
  def inferAny() = {
    println("Check types")
    val l1=List(1,2,"3")
    val l2=List(1,2,())
    val l3=List(1,2,if(false) 3 )
    val l4=List(1,true)
    val v5 = if(scala.util.Random.nextInt(100)>50) "aaa"

    def m5() = if(scala.util.Random.nextInt(100)>50) "aaa"

//    val s:String = v5 check type in error

    println(s"type of v5 = ${v5.getClass.getName}")
  }


  /***
    * INSTANCE CHECKING
    */
  def instanceChecking(): Unit ={
    trait SomethingSpecialized
    trait SomethingBusiness
    object SomeImplementation extends SomethingSpecialized with SomethingBusiness

    def hideBehindTwoInterfaces() : SomethingSpecialized with SomethingBusiness = SomeImplementation

    val value: SomethingSpecialized with SomethingBusiness = hideBehindTwoInterfaces()

    //polimorphism
    trait SomethingAbstract{
      def doAction:Int
    }
    class ImplementationA extends SomethingAbstract{
      override def doAction: Int = 1
    }

    class ImplementationB extends SomethingAbstract{
      override def doAction: Int = 2
    }

    val somethingAbstract : SomethingAbstract = new ImplementationB

    //pattern matching - mostly for records and ADT
    somethingAbstract match {
      case _ : ImplementationA => println("is implementation A")
      case _ : ImplementationB => println("is implementation B")
    }

  }


  //Controversial
  /***
    * DEFAULT ARGUMENT
    */
  def defaultArguments() = {
    def aMethod(input:Int=0) = input+1

    val aFunction: Int => Int = aMethod
    println("Function from default arguments : "+aFunction(3))
  }

  /***
    * EITHER PROJECTION
    */
  def eitherProjection() ={
    val e:Either[String,Int] = Left("exception")
//    e.right.get  //ERROR!
    println("left right projection to option : " + e.right.toOption)
  }

  /***
    * STRANGE ENUMERATION
    */
  def enumeration()= {
      //show reflection
      class SomeEnum extends Enumeration{
        val ONE,TWO= Value
      }

      sealed trait SomeEnum2
      object ONE extends SomeEnum2
      object TWO extends SomeEnum2
  }


  /***
    * Equals ??? Controversial
    */

  def safeEquals(): Unit ={
    {
      //    println("aaa" == 3) //it compi9les :(
      import cats.Eq
      val eq: Eq[String] =Eq.fromUniversalEquals[String]

      //    println(eq.eqv("aaa",3))  //it doesn't compiles
      println("aaa equals bbb : "+ eq.eqv("aaa","bbb"))  //it doesn't compiles
    }
  }

  /***
    * Final case class
    */
//  def finalCaseClass(): Unit ={
//    case class Record(id:Int,value:String)
//    case class RichRecord(override val id:Int, override val value:String, other:Double) extends Record(id, value)
//
//    val r1 = Record(1,"aa")
//    val r2 : Record = RichRecord(1,"aa",2.0)
//
//    println(r1 == r2)
//    println(r2 == r1)
//  }


  /***
    * final val - controversial
    */


  /**
    * implicit type conversion
    */
  def implicitTypeConversion() = {
    //example from docs
    // Won't compile: implicit conversion is disabled
    implicit def int2Array(i: Int) = Array.fill(i)("madness")

    def thidIsMadness(is:Array[String]) = is.foreach(println)

    thidIsMadness(3)
  }


  /**
    * implicit parameters - controversial
    */


  /**
    * implicit and explicit java conversions
    */
  def javaConversionsExample()={
    import scala.collection.JavaConversions._
    val scalaMap: Map[String, String] = Map()
    val javaMap: java.util.Map[String, String] = scalaMap //implicit

    import scala.collection.JavaConverters._
    val javaMap2: java.util.Map[String, String] = scalaMap.asJava // explicit
  }


  /**
    * JavaSerializable common type
    */
  def javaSerializableCommonType()={
    case class Something(v:String)
    val mistake = List("foo", "bar", Something("aaa")) // check infered type
  }

  /**
    * LeakingSealed -- this is good!
    */


  /**
    * MutableDataStructures = controversial
    */


  /**
    * Nothing inference
    */

  def nothingInference()={
    val l=List.empty

    class SomeClass[A](){
      def actOn(a:A) = ???
    }

    val c = new SomeClass()


  }


  /**
    * Null
    */

  /**
    * Option2Iterable
    */

  def optionToIterable()={
    val result=Option(2) zip Option(1)
    val grouped=Option(2).groupBy(identity)
  }


  /**
    * OptionPartial - very good!
    * scala.Option has a get method which will throw if the value is None.
    * The program should be refactored to use scala.Option#fold to explicitly handle both the Some and None cases.
    */


  /**
    * overloading controversial
    */


  /**
    * Product abstract type - never had this problem
    */


  /**
    * PublicInference
    */
  def publicInference = {

    trait TypeInterface
    object PrivateImplementation extends TypeInterface

    trait ModuleInterface {
      def returnSomething() = PrivateImplementation
    }
  }


  /**
    * Recursion - controversial
    */

  /**
    * Return - turn off
    */


  /**
    * Throw - controversial
    */

  /**
    * ToString - controversial
    */

}
