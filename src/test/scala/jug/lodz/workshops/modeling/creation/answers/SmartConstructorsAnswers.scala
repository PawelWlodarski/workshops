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

  import Accounts._

  "EXERCISE3" should {
    "create specific account" in {

      Money.dollars(20).map(newAccount).get mustBe a[Standard]
      Money.dollars(200).map(newAccount).get mustBe a[Premium]
      Money.dollars(-10).map(newAccount) mustBe a[Failure[_]]
    }


    "test transaction" in {
      //prepared data, can be safely obtain from effect
      val amountToTransfer=Money.dollars(100).get
      val a1: Account =Money.dollars(200).map(newAccount).get
      //also we could use package private constructors
      val a2: Account =Standard(new AccountId(2),Money.dollars(50).get)

      val result=transaction(a1,a2,amountToTransfer)

      result mustBe Success(Transaction(new AccountId(1),new AccountId(2),amountToTransfer))
    }


    "Perform unsuccessfull composition with map2" in {
      import cats.Apply
      import cats.instances.try_._
      val t1=Money.dollars(200).map(newAccount)
      val t2=Money.dollars(20).map(newAccount)
      val amountToTransfer=Money.dollars(100)
      val r1: Try[Try[Transaction]] =Apply[Try].map3(t1,t2,amountToTransfer){ (a1, a2, m) =>
        transaction(a1,a2,m)
      }
      //or just
      val r2: Try[Try[Transaction]] =Apply[Try].map3(t1,t2,amountToTransfer)(transaction)

      //embedded type
      r1 mustBe a[Success[_]]
      r1.get mustBe a[Success[_]]
      r1.get.get mustBe a[Transaction]

      r2.get.get mustBe a[Transaction]
    }

    import FunctionalLibrary._

    "flatten effects" in {

    }

    //DEPENDANT EFFECTS
    "compose dependent effects 1 - TUPLE" in {
        def intoTuple[A,B](a:A)(b:B) : (A,B) = (a,b)
        val t1=Money.dollars(200).map(newAccount)
        val t2=Money.dollars(20).map(newAccount)

        val tryTuple: Try[(Account, Account)] =flatMap(t1)(v1=>t2.map(v2=>(v1,v2)))

        tryTuple.get mustBe a[(_,_)]

    }

    "compose dependent effects 2 Transaction/Failed Transaction" in {

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
  //factory state
  private val accountsCounter=new AtomicInteger(1)
  private val premiumAccountDebit=new Money(50)

  //Domain types
  class AccountId private[answers](val value:Int) extends AnyVal
  class Money private[answers](val value:Double) extends AnyVal{
    override def toString: String = s"Money($value)"
  }

  object Money{
    def dollars(d:Double):Try[Money] =
      if(d>0) Success(new Money(d)) else Failure(new IllegalArgumentException(s"$d is not a money value"))
  }

  //Experience consequences of subtype polymorphism
  sealed trait Account{
    def id:AccountId
  }

  //package private access allow testing
  final case class Standard private[answers](id:AccountId,m:Money) extends Account
  final case class Premium private[answers](id:AccountId,m:Money, debit:Money) extends Account

  final case class Transaction(from:AccountId,to:AccountId,amount:Money)


  private def generateId=new AccountId(accountsCounter.getAndIncrement)

  def newAccount(m:Money) : Account =
    if(m.value>100) Premium(generateId,m,premiumAccountDebit)
    else Standard(generateId,m)


  //is Try[Transaction] the best type here?
  def transaction(a1:Account,a2:Account,amount:Money) : Try[Transaction] = a1 match {
    case Premium(id,money,debit) =>
      val allowedTransfer=new Money(money.value + debit.value)
      transactionResult(allowedTransfer,amount,id,a2.id)
    case Standard(id,money) =>
      transactionResult(money,amount,id,a2.id)
  }

    //a1.m.value would be necessary with well defined Ordering or Eq - wait for type class workshops
  private def transactionResult(available:Money,toTransfer:Money,id1:AccountId,id2:AccountId)=
      if(available.value < toTransfer.value) Failure(new IllegalArgumentException(s"no sufficient funds"))
      else Success(Transaction(id1,id2,toTransfer))

}

//EXERCISE3 -
object FunctionalLibrary{

  def flatMap[A,B](t:Try[A])(f:A=>Try[B]) : Try[B] = t match {
    case Success(a) => f(a)
    case f=> f.asInstanceOf[Try[B]]
  }

}