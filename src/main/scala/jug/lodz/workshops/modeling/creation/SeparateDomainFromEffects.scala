package jug.lodz.workshops.modeling.creation

import java.util.concurrent.TimeUnit

import jug.lodz.workshops.WorkshopDisplayer._

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object SeparateDomainFromEffects{

  def main(args: Array[String]): Unit = {
    header("Separate Functions from effects")

    title("pure object function")

    type Input = Int
    type Money = BigDecimal
    type Message = String


    val createMoney : Input => Money = i => BigDecimal(i)
    val displayAccount : Money => Message = m => s"account status : "+m

    val composed: Input => Message =createMoney andThen displayAccount

    section("composed(3)",composed(3))

    title("real world problems")
    type RawInput = String
    val rawInput : String = "100"
    val rawInput2 : String = "ab"

    val read : RawInput => Input = ri => ri.toInt

   section("read('100')",read(rawInput))
  //   section("read('ab')",read(rawInput2))  -> exception


  //assumption without proof : exceptions break RT and introduces non determinism
  val throwsExceptions=read andThen createMoney andThen displayAccount
  throwsExceptions("100") //workds
//  throwsExceptions("ab") //breaks program, needs additional logic, doesn't compose

  title("representing effects")

  val effectOfMissingValue:Option[String] = None
  val effectOfFailure:Try[Input] = Try{read(rawInput2)}
  import scala.concurrent.ExecutionContext.Implicits.global
  val effectOfTime : Future[Input] = Future{TimeUnit.MILLISECONDS.sleep(100);read(rawInput)}


   val safeRead : RawInput => Try[Input] = ri=>Try(ri.toInt)

  title("composing business functions with side effects")

  val display1: Try[Message] =safeRead(rawInput).map(createMoney).map(displayAccount)
  val display2: Try[Message] =safeRead(rawInput2).map(createMoney).map(displayAccount)

  def sideEffectAwareBorderMethod : Try[Message] => Unit = {
    case Success(message) => println(s"RESULT OF OPERATION : $message")
    case Failure(e) => println(s"ERROR : $e")
  }

  sideEffectAwareBorderMethod(display1)
  sideEffectAwareBorderMethod(display2)

  //or you can compose full program -
  //better composition will come with Kleisli
  val processInput:RawInput => Try[Message]=raw => safeRead(raw).map(createMoney).map(displayAccount)
  val program: (RawInput) => Unit =processInput andThen sideEffectAwareBorderMethod


  title("composing pure functions and why RT matters")

  //explain why it is possible and how RT of 'map' allow this to return the same result
  //explain why it is better especially for collections and futures
  val display1b: Try[Message] =safeRead(rawInput).map(createMoney andThen displayAccount)
  sideEffectAwareBorderMethod(display1b)

  }

}
