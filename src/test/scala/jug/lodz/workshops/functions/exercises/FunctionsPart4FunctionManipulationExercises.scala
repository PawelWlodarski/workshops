package jug.lodz.workshops.functions.exercises

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by pawel on 14.07.16.
  */
class FunctionsPart4FunctionManipulationExercises extends FunSuite with Matchers {

  //LEVEL1
  /**
    * Don't change test - implement 'createDecrement' method
    */
  test("create decrement function in method") {
    val dec = createDecrement()

    dec(10) shouldBe 9
    dec(100) shouldBe 99
  }

  //EXERCISE
  private def createDecrement(): Int => Int = ???


  /**
    * Don't change test - implement 'injectParser' method
    */
  type ParsingStrategyServiceBean = String => Int

  test("create function with injected logic") {
    val parser: ParsingStrategyServiceBean = (s: String) => s.toInt

    val decrementString = injectParser(parser)

    decrementString("10") shouldBe 9
    decrementString("100") shouldBe 99
  }

  // first parse input parameter (assume that it's valid number) and decrement
  def injectParser(parser: ParsingStrategyServiceBean): String => Int = ???

  /**
    * Don't change test - implement 'decorateWithNullCheck'
    */
  test("decorate with null check"){
      val parse : String => Int = s=>s.toInt

      val parseNullCheck=decorateWithNullCheck(parse,"0")


      parseNullCheck("55") shouldBe 55
      parseNullCheck(null) shouldBe 0

  }

  // create a function :
  // if argument is not null then invoke f with it
  // if argument IS null then invoke f with default argument
  def decorateWithNullCheck(f: String => Int,default:String) : String=>Int = ???


  //LEVEL2
  /**
    * Don't change test - implement 'curry' method
    */
  test("convert function with two args to curried version"){
    type Parser= String => Int
    val parser : Parser = s=>s.toInt

    val incrementParsed:(Parser,String) => Int = (parser,arg) => parser(arg)+1

    val curriedFunction = curry(incrementParsed)

    val incrementNumericString: String => Int = curriedFunction(_.toInt)

    incrementNumericString("5") shouldBe 6
    incrementNumericString("11") shouldBe 12
  }

  def curry[A,B,C](f:(A,B)=>C): A=>B=>C = ???

  /**
    * don't change test - implement uncurry
    */
  test("implement uncurry"){
    val addCurried: Int=>Int=>Int = a=>b=>a+b

    val addUncurried = uncurry(addCurried)

    addUncurried(1,3) shouldBe 4
    addUncurried(8,7) shouldBe 15
  }

  def uncurry[A,B,C](f:A=>B=>C): (A,B) => C = ???


  /**
    * don't change tets - implement 'reduce' method with provided skeleton
    */
  test("reduce curried"){

    val addition = reduce(List(1,2,3,4,5))(_+_)
    val multiplication = reduce(List(1,2,3,4,5))(_*_)


    addition shouldBe 15
    multiplication shouldBe 120
  }

  //assume that seq is not empty
  def reduce[A](l:List[A])(f:(A,A)=>A): A ={
    var result=l.head

    for(e <- l.tail)  ???

    result
  }


  //LEVEL3

  test("domain example"){
    import DomainExample._

    //policy allow customers with cash higher than 10
    val lightPolicy : Policy = (cash:Cash) => ???
    val longTermBusiness : BusinessLogic = cash => cash + Math. ceil(cash * 0.1).toInt

    val bankWithLightPolicy = cashService(???) _
    val longTermfinancialService = bankWithLightPolicy(???)


    longTermfinancialService(5) shouldBe 0
    longTermfinancialService(100) shouldBe 110
    longTermfinancialService(18) shouldBe 20
  }



}
