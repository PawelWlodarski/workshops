package jug.lodz.workshops.fpeffects.exercises

import scala.util.Try

/**
  * Created by pawel on 10.04.16.
  */
object EffectsPart6TheoryEffectsAndComposition {

  def main(args: Array[String]) {
    val toInt:String=>Int = _.toInt
    val addOne:Int=>Int=_+1
    val multiplyByTwo:Int => Int=_*2

    println("[SIMPLE/PURE COMPOSITION]")

    val simpleComposition=toInt andThen addOne andThen multiplyByTwo
    println("  -- pure result : "+simpleComposition("5"))

    println("\n[BREAK RT WITH EXCEPTION]")

    val resultWithSideEffect=try{

      try{
        simpleComposition("notANumber")  //1
      }catch {
        case e:Exception => -1
      }
//      simpleComposition("notANumber") //2
    }catch{
      case e:Exception=> 0
    }
//    simpleComposition("notANumber") //3

    println("  -- exception side effect : "+resultWithSideEffect)

    println("\n[COMPOSING WITH EXCEPTION]")
    val toIntWithException:String=>(Int,Exception)=input=>try{
      (input.toInt,null)
    } catch{
      case e:Exception => (null.asInstanceOf[Int],e)
    }

    def andThenWithException[A,B,C](first:A=>(B,Exception),second:B=>C):A=>(C,List[Exception])={
      (input:A) =>
        val (result,exception)=first(input)
        if(result==null)
          // val secondException=should we handle exception here??
          (null.asInstanceOf[C],List(exception))
        else
          try{
            (second(result),List())
          }catch{
            case e:Exception => (null.asInstanceOf[C],List(e))
          }
    }

    println("\n[TYPESYSTEM - EXPLICIT EXCEPTION]")
    val toIntPartialFunction:String=>Int=(input:String) => input.toInt
          // isDefinedAt("1) == true  ,
          //  isDefinedAt("notANumber") == false

    val toIntTotal:String=>Try[Int] =input =>  Try(input.toInt)
    val composed=toIntTotal.andThen(input=>input.map(addOne))

    println("  -- composed type correct : "+composed("1"))
    println("  -- composed type incorrect : "+composed("notANumber"))

  }

}

// sequence(a map (i => Try(i.toInt)))