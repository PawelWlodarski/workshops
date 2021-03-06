package jug.lodz.workshops.starter.traits1.answers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.scalatest.{FlatSpec, MustMatchers}

class StarterTraits1ExerciseSpec extends FlatSpec with MustMatchers{

//EXERCISE1
  "Events" should "be created for operation on an Account" in {
    class AccountHistory extends Account with EventStore{
      private var state:Int=0

      override def transfer(amount: Int): Unit = {
        save(s"TRANSFER : $amount")
        state=state+amount
      }

      override def take(amount: Int): Unit = {
        save(s"TAKE : $amount")
        state=state-amount
      }

      override def amount: Int = state
    }

    //given
    val accountHistory=new AccountHistory()

    accountHistory.transfer(100)
    accountHistory.take(50)
    accountHistory.transfer(200)
    accountHistory.take(70)

    accountHistory.amount mustBe 180
    accountHistory.events() must contain inOrder(
      "TAKE : 70",
      "TRANSFER : 200",
      "TAKE : 50",
      "TRANSFER : 100"
    )
  }

  //EXERCISE2
  "Events" should "have date info" in {
    class AccountDateHistory extends Account with DateStore with MockCalendar{
      override def transfer(amount: Int): Unit = save(s"TRANSFER : $amount")
      override def take(amount: Int): Unit = save(s"TAKE : $amount")
      override def amount: Int = 0
    }

    //given
    val accountdataHistory= new AccountDateHistory


    accountdataHistory.transfer(500)
    accountdataHistory.take(30)

    accountdataHistory.events() must contain inOrder(
      "TAKE : 30 : 2017-01-01",
      "TRANSFER : 500 : 2017-01-01"
    )
  }

}

//EXERCISE 1
trait Account {
  def transfer(amount:Int)
  def take(amount:Int)
  def amount:Int
}

trait EventStore{
  private var store:List[String]= List()

  protected def save(event:String) = { store = event ::  store }
  def events() : List[String] = store  // why we can share reference here?
}

//EXERCISE 2
trait Calendar{
  def now():java.time.LocalDateTime
}


trait MockCalendar extends Calendar{
  def now() = LocalDateTime.of(2017,1,1,12,0)
}


trait DateStore extends EventStore{this:Calendar =>

  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  override def save(event:String) =
    super.save(event+" : "+formatter.format(now()))
}