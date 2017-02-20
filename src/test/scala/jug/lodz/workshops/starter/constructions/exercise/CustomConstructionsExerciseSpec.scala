package jug.lodz.workshops.starter.constructions.exercise

import jug.lodz.workshops.starter.constructions.CustomConnection
import org.scalatest.{FlatSpec, Matchers}

class CustomConstructionsExerciseSpec extends FlatSpec with Matchers {


  def withConnection(testCode: CustomConnection => Any): Unit = {
    val conn = new CustomConnection(List("test1", "test2"))
    try {
      conn.open()
      testCode(conn)
    } finally {
      conn.close()
    }
  }


  "Custom connection" should "be provided " in withConnection{conn=>
    conn.query("customQuery") should contain allOf("test1","test2")  //REM : change result and show error
  }




   "simple functions" should "be properly implemented" in {
     //FIX FUNCTIONS DECLARATION
    val inc : Int=>Int= ???
    val dec : Int=>Int= ???

    inc(3) shouldBe 4
    inc(4) shouldBe 5
    dec(3) shouldBe 2
    dec(4) shouldBe 3
   }

// UNCOMMENT AND FIX
//  "methods with splitted params" should "be implemented" in {
//    def multiply ???
//    def addPrefix ???
//
//
//    multiply(2)(3) shouldBe 6
//    multiply(4)(7) shouldBe 28
//    addPrefix("base")("prefix_") shouldBe "prefix_base"
//    addPrefix("value")("label:") shouldBe "label:value"
//  }

  //UNCOMMENT AND FIX
//  "high order methods" should "receive function as parameter" in {
  // you need to compose function with itself by applying arg to the first invocation
//    def composeTwice(arg:Int,f:Int=>Int):Int = ???
//    def composeTwiceSeparated ???
//
//    composeTwice(1,i=>i*2) shouldBe 4
//    composeTwice(5,i=>i*2) shouldBe 20
//    composeTwiceSeparated(1)(i=>i*2) shouldBe 4
//    composeTwiceSeparated(5){ i=>
//      val r1=i*2
//      r1+1
//    } shouldBe 23
//
//  }

  //FIX METHOD FIXTURE
  //you need to create a map with values expected by test below
  //and after that pass it to 'test' function similarly to the 'withConnection' example at the top of this file
  def withSomeMap(test:Map[Int,String]=>Any): Unit = ???

  it should "use fixture" in withSomeMap{map=>
    map(1) shouldBe "one"
    map(2) shouldBe "two"
  }


  "custom while" should "be implemented" in {
    //FIX FUNCTION DECLARATION
    //implement only with one 'if' , don't use scala 'while'
    def customWhile(condition:  => Boolean)(logic: =>Unit):Unit = ???

    var i=4
    val iterations =scala.collection.mutable.ListBuffer[Int]()

    customWhile(i>0){
      iterations.append(i)
      i=i-1
    }

    i shouldBe 0
    iterations should contain inOrder(4,3,2,1)

  }


}
