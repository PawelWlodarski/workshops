package poligon.cats.usecases

import cats.{Applicative, Id}

import scala.concurrent.Future

object Async {

  import cats.instances.future._
  import cats.instances.list._
  import cats.syntax.traverse._
  import cats.syntax.functor._

  trait UptimeClient[F[_]] {
    def getUptime(hostname: String): F[Int]
  }

  trait RealUptimeClient extends UptimeClient[Future]{
    override def getUptime(hostname: String): Future[Int]
  }


  class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id] {
    def getUptime(hostname: String): Int =
      hosts.getOrElse(hostname, 0)
  }

  class UptimeService[F[_] : Applicative](client:UptimeClient[F]){
    def getTotalUptime(hostnames: List[String]) : F[Int] =
      hostnames.traverse(client.getUptime).map(_.sum)
  }

  def main(args: Array[String]): Unit = {
    println("hello")
    testTotalUptime()
  }

  def testTotalUptime() = {
    val hosts = Map("host1" -> 10, "host2" -> 6)
    val client = new TestUptimeClient(hosts)
    val service = new UptimeService(client)
    val actual = service.getTotalUptime(hosts.keys.toList)
    val expected = hosts.values.sum
    assert(actual == expected)
  }
}
