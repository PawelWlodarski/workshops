package jug.lodz.workshops.modeling.creation.answers

import org.scalatest.{MustMatchers, WordSpecLike}

class SeparateDomainFromEffectsExercises extends WordSpecLike with MustMatchers{

  //Option represent potential missing of value
  //in java for example when you want to retrieve value from map and it is not there -
  // you will receive null but in scala the result is Option[A] for MAP[K,A]
  "EXERCISE1" should {

    //you need to read user from database
    // * complete business function to create report message
    // * complete side effect function which emulates displaying
    "display user salary" in {
      type Salary = Int
      type Report = String
      case class User(id:Int,name:String,salary:Salary)

      val database = Map[Int,User]{
        1 -> User(1,"Mirek",50)
      }

      val getSalary : User => Salary = u => u.salary
      val createMessage : Salary => String = s => s"SALARY : $s" //exercise

      val  effectFunction : Option[Report] => String = { //exercise
        case Some(m) => s"RESULT : $m"
        case None => "NOT ENOUGH DATA"
      }

      createMessage(50) mustBe "SALARY : 50"

      val u1=database.get(1).map(getSalary andThen createMessage)
      val u2=database.get(2).map(getSalary andThen createMessage)

      effectFunction(u1) mustBe "RESULT : SALARY : 50"
      effectFunction(u2) mustBe "NOT ENOUGH DATA"

    }

    //in this exercise display result when calculating tax or display proper error when configuration is missing
    "compose single option with currying" in {
      type Money=Double
      type Tax=Double

      val config=Map{
        "taxes.gross" -> 0.19
      }

      val calculateGross : Money => Tax =>  Money = input => tax => input+(input*tax)  //exercise
      val input:Money= 10.0

      val tax: Option[Tax] =config.get("taxes.gross")

      val toDecimalFormat : Option[Money] => BigDecimal = {   //exercise
        case Some(result) => BigDecimal(result)
        case None => BigDecimal(0)
      }

      toDecimalFormat(tax.map(calculateGross(input))) mustBe BigDecimal(11.9)
      toDecimalFormat(config.get("not.existing").map(calculateGross(input))) mustBe BigDecimal(0)
    }

    //swap exercise - HARD BONUS
    //Money => Tax =>  Money from previous exercise is less usable than Tax => Money =>  Money
    //because injecting tax give us more usability than injecting concrete sum first
    //write swap high kind method to change places of arguments
    "swap arguments" in {
      def swap[A,B,C](f:A=>B=>C):B=>A=>C =
        b => a => f(a)(b)

      type Money=Double
      type Tax=Double

      val calculateGross : Tax => Money =>  Money = tax => input => input+(input*tax)  //exercise

      val swappedGross: Money => Tax => Money =swap(calculateGross)
      val m:Money=100
      val t:Tax=0.23

      swappedGross(m)(t) mustBe 123

    }

    //exercise TRY
    //exercise Future
  }

}
