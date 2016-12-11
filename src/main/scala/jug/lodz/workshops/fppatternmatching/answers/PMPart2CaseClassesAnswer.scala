package jug.lodz.workshops.fppatternmatching.answers

/**
  * Created by pawel on 22.04.16.
  */
object PMPart2CaseClassesAnswer {


  object Demonstration {

    case class SomeClass(value: String)

    def demo() = {
      println("***[DEMONSTRATION]***")
      println("\n *[Matching Simple Case Classes]")
      val record = SomeClass("someValue")

      record match {
        case SomeClass("someValue") => println("  --> exact match : case SomeClass(\"someValue\")")
        case _ => println("something else")
      }

      record match {
        case SomeClass(value) => println(s"  --> value match $value : SomeClass(value)")
      }

      record match {
        case c@SomeClass(value) => println(s"  --> full class match $c :  c @ SomeClass(value)")

      }

      println("\n *[Matching Embedded Case Classes]")
      case class City(name: String)
      case class Address(street: String, city: City)
      case class User(name: String, address: Address)

      val city = City("Lodz")
      val user = User("John", Address("Piotrkowska", city))

      user match {
        case User(userName, Address(_, City(cityName))) => println(s"  --> user $userName lives in $cityName")
      }


      println("\n *[Matching Case Classes List]")
      val city2 = City("Kielce")
      val user2 = User("Jane", Address("Przybyszewskiego", city2))
      val users = List(user, user2)

      users match {
        case User(_, Address(_, City(city1))) :: User(_, Address(_, City(city2))) :: Nil =>
          println(s"  --> there are two cities : [$city1,$city2]")
        case _ => println("something else")
      }

      println("\n *[Matching In Partial Functions]")
      val names = users.map {
        case User(name, _) => name
      }
      println(s"  --> user names $names")
    }


  }

  object ExerciseLevel1 {

    case class Wrapper(value: String)

    def exercise11() = {
      val result1 = Wrapper("value1") match {
        case Wrapper(value) => "value1"
      }

      println(s""" *EXERCISE11 : ${result1 == "value1"}""") // result1 should be "value1"
    }

    def exercise12() = {
      val result2 = List(Wrapper("v1"), Wrapper("v2")) match {
        case Wrapper(v1) :: Wrapper(v2) :: Nil => List(v1, v2)
        case _ =>
      }

      println(s""" *EXERCISE12 : ${result2 == List("v1", "v2")}""") //result2 should be a List(v1,v2)
    }

  }

  object ExerciseLevel2 {

    case class Price(value: BigDecimal)

    case class ProductInfo(name: String, price: Price)

    case class PurchaseLine(info: ProductInfo, amount: Int)

    case class Purchase(date: String, items: List[PurchaseLine])

    val tvInfo = ProductInfo("tv", Price(BigDecimal(300)))
    val consoleInfo = ProductInfo("console", Price(BigDecimal(200)))
    val mouseInfo = ProductInfo("mouse", Price(BigDecimal(30)))
    val bookInfo = ProductInfo("book", Price(BigDecimal(50)))

    val purchase1 = Purchase(
      date = "20-04-2016",
      items = List(
        PurchaseLine(tvInfo, 1),
        PurchaseLine(consoleInfo, 1),
        PurchaseLine(mouseInfo, 2)
      )
    )

    val purchase2 = Purchase("21-03-2016", List(PurchaseLine(bookInfo, 3)))

    val purchases = List(purchase1, purchase2)

    def exercise21() = {
      def extractDates(ps: List[Purchase]): List[String] = ps.map { case Purchase(date, _) => date }

      println(s" *EXERCISE21 : ${extractDates(purchases) == List("20-04-2016", "21-03-2016")}")
    }

    def exercise22() = {
      def extractPrice(p: PurchaseLine): BigDecimal = p match {
        case PurchaseLine(ProductInfo(_, price), amount) => price.value * amount
      }
      def sumPrices(ps: List[Purchase]): List[BigDecimal] = ps.map {
        case Purchase(_, items) => items.map(extractPrice).sum
      }

      println(s" *EXERCISE22 : ${sumPrices(purchases) == List(BigDecimal(560), BigDecimal(150))}")
    }

    def exercise23() = {
      def extractPrice(p: PurchaseLine): BigDecimal = p match {
        case PurchaseLine(ProductInfo(_, price), amount) => price.value * amount
      }
      def pricesInfo(ps: List[Purchase]): List[(String, BigDecimal)] = ps.map {
        case Purchase(date, items) => (date, items.map(extractPrice).sum)
      }

      println(s" *EXERCISE23 : ${
        pricesInfo(purchases) == List(
          ("20-04-2016", BigDecimal(560)),
          ("21-03-2016", BigDecimal(150)))
      }")
    }
  }

  object ExerciseLevel3 {

    case class Header(name: String, value: String)

    case class Method(name: String)

    case class Request[A](url: String, method: String, headers: List[Header], body: A)

    case class Response[A](code: Int, body: A)

    case class Page(content: String)

    val web: Map[String, Page] = Map(
      "www.juglodz.pl" -> Page("<h1>This is jug page</h1>"),
      "www.google.com" -> Page("<input>Enter phrase</input>")
    )


    def exercise31() = {
      def getServer[A, B](request: Request[A]): Response[String] = request match {
        case Request(url, method, _, _) if (method == "GET") => web.get(url)
          .map(page => Response(200, page.content))
          .getOrElse(Response(404, "Not_Found :("))

        case Request(_, _, _, _) => Response(405, "only GET allowed")

        case _ => Response(501, "What is this?")
      }

      println(s""" *EXERCISE31 - correct request: ${getServer(Request("www.juglodz.pl", "GET", List(), "")).code == 200}""")
      println(s""" *EXERCISE31 - wrong url: ${getServer(Request("www.jglodz.pl", "GET", List(), "")).code == 404}""")
      println(s""" *EXERCISE31 - wrong method: ${getServer(Request("www.juglodz.pl", "POST", List(), "")).code == 405}""")
      val hack: Null = null
      println(s""" *EXERCISE31 - hacked: ${getServer(hack).code == 501}""")


    }
    def exercise32() = {
      def converter[A, B](ra: Request[A])(f: A => B): Request[B] = ra match {
        case Request(url, method, headers, body) => Request(url, method, headers, f(body))
      }

      case class JsonString(value: String)
      case class Json(content: JsonString)

      val jsonRequest = Request(url = "someUrl",
        method = "POST",
        headers = List(Header("Content-Type", "application/json")),
        body = Json(JsonString("requestBody"))
      )

      val resultRequest = converter(jsonRequest) { case Json(JsonString(value)) => value }
      println(s""" *EXERCISE32 : ${resultRequest.body == "requestBody"}""")

    }
  }

  def main(args: Array[String]) {
    Demonstration.demo()

    println("\n\n[------EXERCISES------]")
    println("\n[------EXERCISE LEVEL 1------]")
    ExerciseLevel1.exercise11()
    ExerciseLevel1.exercise12()

    println("\n[------EXERCISE LEVEL 2------]")
    ExerciseLevel2.exercise21()
    ExerciseLevel2.exercise22()
    ExerciseLevel2.exercise23()


    println("\n[------EXERCISE LEVEL 3------]")
    ExerciseLevel3.exercise31()
    ExerciseLevel3.exercise32()
  }
}

