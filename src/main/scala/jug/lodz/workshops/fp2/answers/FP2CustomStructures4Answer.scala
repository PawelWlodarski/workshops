package jug.lodz.workshops.fp2.answers

/**
  * Created by pwlodarski on 2016-03-16.
  */
object FP2CustomStructures4Answer {

  def demonstration1(value:String)(action : String => Unit)={
    val calculationResult=s"result of some calcualtions based on '$value'"
    action(calculationResult)
  }

  def demonstration2[A](value: => String)(action : String => A)={
    val calculationResult=s"result of some calcualtions based on '$value'"
    action(calculationResult)
  }

  //Exercise
  def customFold(seq:Seq[Int])(op:(Int,Int)=>Int):Int=seq.foldLeft(0)(op)

  case class SuperHero(name:String, power:String)
  val heroes:Map[String,SuperHero] = Map("Spiderman"->SuperHero("Spiderman","Web"),"J23" -> SuperHero("J23","Being Undercower"))

  def heroFight(heroName:String)(action:SuperHero => Unit)=
    heroes.get(heroName).fold(println(s"hero $heroName is unavailable :("))(action)


  //ADDITIONAL
  def customWhile(condition: => Boolean)(action : =>Unit):Unit={
    if(condition) {
      action
      customWhile(condition)(action)
    }
  }


  def customIfExpression[A](condition : => Boolean)(action: =>A)(elseAction: =>A): A ={
    if(condition) action else elseAction
  }

  def customIfStatement(condition : => Boolean)(action: =>Unit)(elseAction: =>Unit): Unit ={
    if(condition) action else elseAction
  }

  def main(args: Array[String]) {
    //DEMONSTRATION
    println("-----------DEMONSTRATION------------------")
    demonstration1("someValue")((result:String)=>println(result))
    demonstration1("someValue")(result=>println(result))
    demonstration1("someValue")({result=>println(result)})
    demonstration1("someValue"){result=>println(result)}
    demonstration1("someValue"){result=>
      println(result)
    }

    println("-----------DEMONSTRATION 2------------------")
    val result:Int=demonstration2("startValue" + " in secondPart"){result=>
      println(result)
      result.length
    }
    println(s"demonstration2 $result")

    //Exercise
    println("-----------EXERCISES------------------")
    println("-------customFold--------")
    val customFoldResult=customFold(List(1,2,3,4,5)){(a:Int,b:Int) =>
      a+b
    }
    println(s"custom fold $customFoldResult")
    println(customFoldResult==15)

    println("-------hero fight----------")
    heroFight("J23"){hero:SuperHero =>
      println(s"hero ${hero.name} is fighting with ${hero.power}")
    } // should display : hero J23 is fighting with Being Undercower

    //ADDITIONAL
    println("-----------custom while-------------")
    //customWhile(i<5)({println(s" i = $i");i=i+1})
    var i=0
    customWhile(i<5){
      println(s" i = $i")
      i=i+1
    }


    println("-------customIf----------")
    val a=5
    val ifResult=customIfExpression(a>4){
      println("custom if when true"); 17+3
    }{
      println("custom if when false")
      20+3
    }
    println(s"if expression result $ifResult")

  }

}
