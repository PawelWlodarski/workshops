package jug.lodz.workshops.modeling.creation.answers

import java.util.concurrent.atomic.AtomicInteger

import org.apache.commons.codec.binary.Hex
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.util.{Failure, Success, Try}

class SmartConstructorsAnswers extends WordSpecLike with MustMatchers{

  "EXERCISE1" should {

    //complete code at the bottom of the file
    "create positive salary" in {
      Salary.create(-100) mustBe None
      Salary.create(20) mustBe Some(Salary(20))
    }

    "create tax in range with config exceptions" in {
      val taxFactory: (Int) => Option[Tax] =Tax.inRange(10,50)

      taxFactory(23) mustBe Some(Tax(23))
      taxFactory(60) mustBe None

      an [Exception] must be thrownBy Tax.inRange(100,0)(5)
      noException must be thrownBy Tax.inRange(100,0) _
    }


    "Create Tax with domain Range Object" in {
      val r: Option[TaxInRange.Range] =TaxInRange.createRange(10,50)
      val taxFactory: (Int) => Option[Tax] =TaxInRange.inRangeOption(r)

      taxFactory(23) mustBe Some(Tax(23))
      taxFactory(60) mustBe None

      TaxInRange.createRange(100,0) mustBe None
      TaxInRange.inRangeOption(TaxInRange.createRange(100,0))(5) mustBe None
    }
  }

  //information transfer domain
  //Try represents offect of unfinished computation which ended exceptionally and
  //therefore can not be represented as a value
  "EXERCISE2" should {

    //here we will model hexadecimal type - used heavily in so called 'IoT'
    //we will create HexType which will represent values as "FFA00B10"
    //HexType can be created only through safe constructors
    "safely decode and serialize hex format" in {
      val input: String =255.toHexString  //explain method extension
      //        println(input) //check your self - ff

      HexType.decode(input) mustBe Success(HexType createFrom Array(255.toByte) )
      HexType.decode("MIREK") mustBe a[Failure[_]]
    }

  }

  "EXERCISE3" should {
    "create specific account" in {
        ///map2
        // compose effect
    }
  }
}

//EXERCISE1 - SALARY
case class Salary private (amount:Int)

object Salary{
  def create(amount: Int): Option[Salary] =
    if(amount>0) Some(Salary(amount)) else None
}

//EXERCISE1B - TAX
case class Tax private (amount : Int)

object Tax{
  def inRange(from:Int,to:Int)(value:Int) : Option[Tax] = {
    require(from > 0 , "from must be greater than 0")
    require(to <100, "to must be smaller than 100")
    require(from < to, "from must be smaller than to")

    if(value>=from && value <= to)
      Some(Tax(value))
    else
      None

  }
}


object TaxInRange {
  case class Range private (from : Int, to:Int){
    def contains(i:Int) : Boolean = i >= from && i<=to
  }

  def createRange(from:Int,to:Int) : Option[Range] =
    if(from <0 || to > 100 || from>to) None
    else Some(Range(from,to))

  def inRange(r:Range)(v:Int): Option[Tax] =
    if(r.contains(v)) Some(Tax(v)) else None

  def inRangeOption(r:Option[Range])(v:Int):Option[Tax] = r match {
    case Some(r) => inRange(r)(v)
    case _ => None
  }

}






//EXERCISE 2 recall relation beteen class and companion object
class HexType private(private val bytes:Array[Byte]){

  override def hashCode(): Int = bytes.hashCode
  override def equals(o2: scala.Any): Boolean =
    o2.isInstanceOf[HexType] && this.bytes.sameElements(o2.asInstanceOf[HexType].bytes)
  override def toString: String = bytes.mkString
}

object HexType{

  //smart constructor - more in level2
  def decode(input:String) : Try[HexType] = Try{
    val bytes=Hex.decodeHex(input.toCharArray)
    new HexType(bytes)
  }

  def serialize(h:HexType): String = Hex.encodeHex(h.bytes,false).mkString

  def createFrom(bytes: Array[Byte]) = new HexType(bytes)
}


//EXERCISE3 - Account
object Accounts{
  class InterestRate private[Accounts](val value:Double) extends AnyVal
  class Money private[Accounts](val value:Double) extends AnyVal
  class AccountId private[Accounts](val value:Int) extends AnyVal

  final case class Standard(m:Money) extends Account(generateId,standardInterestRate,m)
  final case class Premium(m:Money) extends Account(generateId,premiumInterestRate,m)
  final case class Transaction(from:AccountId,to:AccountId,amount:Money)

  private val standardInterestRate=new InterestRate(3.5)
  private val premiumInterestRate=new InterestRate(7.5)
  private val accountsCounter=new AtomicInteger(1)

  private def generateId=new AccountId(accountsCounter.getAndIncrement)

  sealed abstract class Account(id:AccountId,ir:InterestRate,m:Money)


  def newAccount(m:Money) : Account = if(m.value>100) Premium(m) else Standard(m)

  def transaction(a1:Account,a2:Account) : Try[Transaction] = ???

}