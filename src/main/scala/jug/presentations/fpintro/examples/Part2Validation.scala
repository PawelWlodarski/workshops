package jug.presentations.fpintro.examples

object Part2Validation {

  def main(args: Array[String]): Unit = {

    println(AgeValidator.check(5))
    println(AgeValidator.check(-5))

    val u=new User("John","M",30,5000)
    val getAge: User => Int = u => u.age
    val getAgeShort: User => Int = _.age

    val isMoreThanClassic : (Int,Int) => Boolean = (toCompare,candidate) => candidate > toCompare
    val isMoreThan: Int => Int =>  Boolean = toCompare => candidate => candidate > toCompare

    val isMoreThan17: Int => Boolean = isMoreThan(17)  //some kind of DI


    val isAdult=getAge andThen isMoreThan17   //radable ???
    val isAgePositive=getAge andThen AgeValidator.check   //radable ???


    //why we can create illegal user?
    val illegalUser=new User("","A",-30,-5000)
    println("isAdult : "  + isAdult(u))
    println("isAgePositive : "  + isAgePositive(u))
    println("isAgePositive : "  + isAgePositive(illegalUser))
    println("is Adult illegal : "  + isAdult(illegalUser))


    //EXPLAIN RESULt TYPE
    def choosePrefix(gender:String) =
      if(gender=="M") "Mr."
      else if(gender=="F") "Mrs."
      //else "this should never happen"

    val financialFormula : Int => Double =
      salary => (salary + 20.0) / salary

    println(financialFormula(50))
//    println(financialFormula(0)) //should never happen

  }


  trait Validator[A]{
    def check(a:A):Boolean
  }

  object AgeValidator extends Validator[Int]{ //no primitive int
    override def check(a: Int): Boolean = a > 0 // short method
  }

  class User(val name:String, val geneder:String,val age:Int, val salary:Int)

}
