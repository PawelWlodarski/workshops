package jug.lodz.workshops.fpeffects.exercises

import java.util.concurrent.TimeUnit

/**
  * Created by pawel on 10.04.16.
  */
object EffectsPart8CombiningEffects {

    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.Future


    case class Company(id:Int,name: String, info:String)

    def service1(name:String) : Future[Option[Int]] ={
      val companiesIds:Map[String,Int] = Map(
        "company1" -> 1,
        "company2" -> 2
      )

      Future{
        TimeUnit.MILLISECONDS.sleep(500)
        companiesIds.get(name)
      }
    }


    def service2(id:Int) : Future[Option[Company]] ={
      val companies:Map[Int,Company] = Map(
        1 -> Company(1, "company1", "some info about company1"),
        2 -> Company(2, "company2", "some info about company2")
      )

      Future{
        TimeUnit.MILLISECONDS.sleep(500)
        companies.get(id)
      }
    }

    def main(args: Array[String]) {
      import cats.data.OptionT
      import cats.std.future._

      val result=for{
        id<-OptionT(service1("company1"))
        company<-OptionT(service2(id))
      } yield company.info

      val finalValue: Future[Option[String]] = result.value

      finalValue.onSuccess{
        case Some(v) => println(v)
      }
      TimeUnit.SECONDS.sleep(2)
    }

}
