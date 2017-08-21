package jug.lodz.workshops.modeling.storage.exercises

import monocle.macros.GenLens
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.util.{Failure, Success, Try}

class RepositoryExercises extends WordSpecLike with MustMatchers{

  import RepositoryExercisesLibrary._

  //Take a look at InMemoryAccountRepository to see test fixture
  "EXERCISE 1" should {
    "create in memory repository" in {
        val a=Account(10,1000.0)
      InMemoryAccountRepository.store(a)

      InMemoryAccountRepository.query(10) mustBe Success(Account(10,1000.0))
    }

    "return Failure when account is missing" in {
      an[RuntimeException] must be thrownBy InMemoryAccountRepository.query(101).get
    }

    "add domain operations for money manipulations" in {
      val result: Try[Account] =InMemoryAccountRepository.addMoney(1,20)
      result.foreach(InMemoryAccountRepository.store)

      InMemoryAccountRepository.query(1) mustBe Success(Account(1,120.0))
    }
  }


  "EXERCISE 2" should {
    "implement custom reader with proper map" in {
      type ExternalContext=Map[Int,String]
      val externalContext:ExternalContext= Map(
        1 -> "first",
        2 -> "second"
      )

      val reader  = MyCustomReader[ExternalContext,String]{ctx=>
        ctx(1)
      }

      reader.map(_.toUpperCase).run(externalContext) mustBe "FIRST"
    }

    "implement custom reader with proper flatMap" in {
      type ExternalContext=Map[Int,String]
      val externalContext:ExternalContext= Map(
        1 -> "first",
        2 -> "second"
      )

      val reader  = MyCustomReader[ExternalContext,String]{ctx=>
        ctx(1)
      }

      val afterFlatMap=reader.flatMap(first =>
        MyCustomReader[ExternalContext,String]{ctx=>
         first+"_"+ctx(2)
        }
      )

      afterFlatMap.run(externalContext) mustBe "first_second"
    }
  }


  "EXERCISE 3" should {
    "create service with custom reader" in {
      val transferReader=ReaderService.transfer(2,3,15.0)

      transferReader.run(InMemoryAccountRepository) mustBe Success(ReaderService.Done)

      InMemoryAccountRepository.query(2) mustBe Success(Account(2,185.0))
      InMemoryAccountRepository.query(3) mustBe Success(Account(3,315.0))
    }
  }

}

//EXERCISE1

object RepositoryExercisesLibrary {
  trait Repository[A, IdType] {
    def query(id: IdType): Try[A]  //or Try[Option[A]]
    def store(a: A): Try[A]
  }

  type Money = Double

  case class Account (id:Int, amount: Money)


  trait AccountRepository extends Repository[Account,Int]{

    lazy val moneyLens=GenLens[Account](_.amount)

    //EXERCISE1
    def addMoney(id:Int, howMuch:Money) : Try[Account] = ???


    def borrowMoney(id:Int, howMuch:Money) : Try[Account] =
      query(id) flatMap { account =>
          val modified=moneyLens.modify(_ - howMuch)(account)
          if(modified.amount>=0) Success(modified) else Failure(new IllegalArgumentException("not enough money"))
      }
  }

  object InMemoryAccountRepository extends AccountRepository {
    private var data: Map[Int, Account] = Map(
      1 -> Account(1, 100.0),
      2 -> Account(2, 200.0),
      3 -> Account(3, 300.0)
    )

    //EXERCISE1
    override def store(a: Account): Try[Account] = ???


    //EXERCISE1
    override def query(id: Int): Try[Account] = ???
  }

}

//EXERCISE2

case class MyCustomReader[R, A](run: R => A) {
  def map[B](f: A => B): MyCustomReader[R, B] =  ???
  def flatMap[B](f: A => MyCustomReader[R, B]): MyCustomReader[R, B] = ???
}

//EXERCISE3
import RepositoryExercisesLibrary._
object ReaderService {
  case object Done

  //EXERCISE3
  private def addMoney(id:Int,howMuch:Money): MyCustomReader[AccountRepository,Try[Account]] = MyCustomReader{r=>
    ???
  }

  //EXERCISE3
  private def borrowMoney(id:Int,howMuch:Money): MyCustomReader[AccountRepository,Try[Account]] = ???

  //EXERCISE3
  private def update(account: Account) : MyCustomReader[AccountRepository,Try[Account]] = ???

  //no transaction scope, this is just for education
  def transfer(from:Int,to:Int,howMany:Money) : MyCustomReader[AccountRepository,Try[Done.type]] =
    for{
      borrowResult <-borrowMoney(from,howMany)
      addResult  <-addMoney(to,howMany)
      acc1 = borrowResult.get
      acc2 = addResult.get
      _ <- update(acc1)
      _ <- update(acc2)
    } yield Try(Done)

}
