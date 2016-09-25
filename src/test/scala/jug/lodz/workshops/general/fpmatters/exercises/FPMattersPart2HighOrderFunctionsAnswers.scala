package jug.lodz.workshops.general.fpmatters.exercises

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by pawel on 19.09.16.
  */
class FPMattersPart2HighOrderFunctionsAnswers extends FunSuite with Matchers {

  //implement those customCurry and customUncurry - don't change test
  test("custom curry & uncurry") {
    val original: (Int, Int) => Int = (i1, i2) => i1 + i2
    val curried = customCurry(original)
    val uncurried = customUnCurry(curried)

    original(3, 4) shouldBe 7
    curried(3)(4) shouldBe 7
    uncurried(3, 4) shouldBe 7
  }

  def customCurry[A, B, C](f: (A, B) => C): A => B => C = ???
  def customUnCurry[A, B, C](f: A => B => C): (A, B) => C = ???


  /** ** FOLDR  *****/

  //notice changed parameters order - first function then collection so you need
  //to explicitly declare function types
  def customFoldR[A, B](f: (A, B) => B)(zero: B)(l: List[A]): B = l match {
    case Nil => zero
    case head :: tail => f(head, customFoldR(f)(zero)(tail))
  }


  //implement filterByFoldR - don't change the test
  test("filter by custom foldr") {
    import ProductsDao._
    val products = ProductsDao.selectProducts()
    filterByFoldR((p: Product) => p.price > 30)(products) shouldBe List(car, pc)
  }

  //use customFolR to implement filter
  def filterByFoldR[A](p: A => Boolean)(l: List[A]): List[A] = customFoldR(???)(???)(???)


  //implement mapByFoldR - don't change the test
  test("map by custom foldr") {
    val products = ProductsDao.selectProducts()

    mapByFoldR((p: Product) => p.name)(products) shouldBe List("Car", "Pc", "Banana")
  }

  //use customFolR to implement map
  def mapByFoldR[A, B](f: A => B)(l: List[A]): List[B] = customFoldR(???)(???)(???)


  //define missin piece and add it to the composition
  //notice how having function as a first parameter of high order make easier to build chain of computation
  test("higher kind functions composition") {
    val getName: Product => String = _.name
    val getPrice: Product => BigDecimal = _.price
    val greaterThan: BigDecimal => BigDecimal => Boolean = threeshold => x => x > threeshold
    val joinString: String => (String, String) => String = delimiter => (a, b) => if (b.nonEmpty) a + delimiter + b else a
    val toProductsJson: String => String = productsLine => s"""{"products" : [${productsLine}]}"""

    val premiumProducts: List[Product] => List[Product] = filterByFoldR(getPrice andThen greaterThan(30))
    val productsNames: List[Product] => List[String] = mapByFoldR(getName)
    val joinstringLine: List[String] => String = customFoldR(joinString(","))("")

    //run this test once before uncommenting this line
    //    val missingPiece : List[String] => List[String] = ???

    val listPremiumProducts = premiumProducts andThen productsNames andThen joinstringLine andThen toProductsJson


    listPremiumProducts(ProductsDao.selectProducts()) shouldBe """{"products" : ["Car","Pc"]}"""
  }

  //intro to part 3 - working with infinity
  //implement iterate - don't change the test
  test("towards infinity") {
    val oddNumbersSequence: (Int) => List[Int] = iterate { (i: Int) => i + 2 }(1)

    oddNumbersSequence(7) shouldBe List(1, 3, 5, 7, 9, 11, 13)
  }

  //simple if is enough
  def iterate[A](f: A => A)(a: A)(n: Int): List[A] = ???

  //Domain
  case class Product(name: String, price: BigDecimal)

  object ProductsDao {
    val car = Product("Car", BigDecimal(1000.0))
    val pc = Product("Pc", BigDecimal(50.0))
    val banana = Product("Banana", BigDecimal(10.0))

    def selectProducts(): List[Product] = List(car, pc, banana)

  }

}
