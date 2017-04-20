package jug.lodz.workshops.modeling.creation.answers

import org.apache.commons.codec.binary.Hex
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.util.{Failure, Success, Try}

class SeparateDomainFromEffectsAnswers extends WordSpecLike with MustMatchers {

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
      case class User(id: Int, name: String, salary: Salary)

      val database = Map[Int, User] {
        1 -> User(1, "Mirek", 50)
      }

      val getSalary: User => Salary = u => u.salary
      val createMessage: Salary => String = s => s"SALARY : $s" //exercise

      val effectFunction: Option[Report] => String = { //exercise
        case Some(m) => s"RESULT : $m"
        case None => "NOT ENOUGH DATA"
      }

      createMessage(50) mustBe "SALARY : 50"

      val u1 = database.get(1).map(getSalary andThen createMessage)
      val u2 = database.get(2).map(getSalary andThen createMessage)

      effectFunction(u1) mustBe "RESULT : SALARY : 50"
      effectFunction(u2) mustBe "NOT ENOUGH DATA"

    }

    //in this exercise display result when calculating tax or display proper error when configuration is missing
    "compose single option with currying" in {
      type Money = Double
      type Tax = Double

      val config = Map {
        "taxes.gross" -> 0.19
      }

      val calculateGross: Money => Tax => Money = input => tax => input + (input * tax) //exercise
      val input: Money = 10.0

      val tax: Option[Tax] = config.get("taxes.gross")

      val toDecimalFormat: Option[Money] => BigDecimal = { //exercise
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
      def swap[A, B, C](f: A => B => C): B => A => C =
        b => a => f(a)(b)

      type Money = Double
      type Tax = Double

      val calculateGross: Tax => Money => Money = tax => input => input + (input * tax) //exercise

      val swappedGross: Money => Tax => Money = swap(calculateGross)
      val m: Money = 100
      val t: Tax = 0.23

      swappedGross(m)(t) mustBe 123

    }

    // in this exercise we will train work on multiple effects
    // we will use cryptography domain - usable in IoT and such things
    "EXERCISE2" should {
      "create safe hex converter" in {

        import HexParser._
        parseHex("00FF") mustBe Success(255)
        parseHex("MIREK").isFailure mustBe true

      }

      "add two effects by pattern matching" in {
        def add(i1: Try[Int], i2: Try[Int]): Try[Int] = (i1, i2) match {
          case (Success(a), Success(b)) => Success(a + b)
          case (Success(a), f: Failure[_]) => f
          case (f: Failure[_], Success(b)) => f
          case (f, _) => f
        }

        val i1 = HexParser.parseHex("A")
        val i2 = HexParser.parseHex("B")
        val i3 = HexParser.parseHex("Z")

        add(i1, i2) mustBe Success(21)
        add(i1, i3).isFailure mustBe true
        add(i2, i3).isFailure mustBe true
        add(i3, i3).isFailure mustBe true
      }

      //OPTIONAL EXERCISE :
      //this is just to ilustrate problems and difficulties when using simple map
      "try to add two effects with simple map" in {
        def pure(a: Int, b: Int) = a + b

        val i1 = HexParser.parseHex("A")
        val i2 = HexParser.parseHex("B")
        val i3 = HexParser.parseHex("Z")
        //attempt1
        val r1: Try[Try[Int]] = i1.map(v1 => i2.map(v2 => pure(v1, v2)))
        val r2: Try[Try[Int]] = i1.map(v1 => i3.map(v3 => pure(v1, v3)))

        r1 mustBe Success(Success(21))
        r2.isSuccess mustBe true
        r2.get.isFailure mustBe true //Success(Failure(e))

        val r3 = i1.map { v1 =>
          i2 match {
            case Success(v2) => pure(v1, v2)
            case Failure(_) => 0 //we should fail but how?
          }
        }

        r3 mustBe Success(21)

      }

      //more ways of combining multiple effects during monad workshops
      // HARD : use map2 from cats library
      "combine pure function with multiple effects using 'Applicative' and map2 from cats " in {
        import cats.Apply
        import cats.instances.try_._

        def pure(a: Int, b: Int) = a + b

        val i1 = HexParser.parseHex("A")
        val i2 = HexParser.parseHex("B")
        val i3 = HexParser.parseHex("Z")

        Apply[Try].map2(i1, i2)(pure) mustBe Success(21)
        Apply[Try].map2(i1, i3)(pure).isFailure mustBe true
      }

      //write map2 yourself to see that it is not magic
      "write map2 yourself" in {
        def map2[A, B, C](a: Try[A], b: Try[B])(f: (A, B) => C): Try[C] = (a, b) match {
          case (Success(a), Success(b)) => Success(f(a, b))
          case (Success(a), f: Failure[_]) => f.asInstanceOf[Try[C]]
          case (f: Failure[_], Success(b)) => f.asInstanceOf[Try[C]]
          case (f, _) => f.asInstanceOf[Try[C]]
        }

        def pure(a: Int, b: Int) = a + b

        val i1 = HexParser.parseHex("A")
        val i2 = HexParser.parseHex("B")
        val i3 = HexParser.parseHex("Z")

        map2(i1, i2)(pure) mustBe Success(21)
        map2(i1,i3)(pure).isFailure mustBe true

      }
    }


    //exercise Future
  }

}

object HexParser {
  def parseHex(s: String): Try[Int] = Try { //exercise
    Integer.parseInt(s, 16)
  }
}

