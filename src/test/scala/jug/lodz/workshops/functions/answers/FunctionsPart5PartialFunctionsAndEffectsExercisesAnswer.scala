package jug.lodz.workshops.functions.answers

import java.util

import org.scalatest.{Matchers, FunSuite}

/**
  * Created by pawel on 15.07.16.
  */
class FunctionsPart5PartialFunctionsAndEffectsExercisesAnswer extends FunSuite with Matchers {

  //LEVEL 1

  /**
    * Don't change test - implement 'safeGet'
    */
  test("safe hash map") {
    val map = new util.HashMap[String, Int]()
    map.put("one", 1)

    safeGet(map, "one") shouldBe Some(1)
    safeGet(map, "two") shouldBe None
  }

  //EXERCISE - key present -> Some(value), key missing -> None
  def safeGet[A, B](map: java.util.HashMap[A, B], key: A): Option[B] = {
    val potentialResult = map.get(key)
    if (potentialResult != null) Some(potentialResult) else None //or just Option(potentialResult) checks for null
  }

  //LEVEL2

  /**
    * Don't change test - implement 'partialToTotal'
    */
  test("convert partial function to total") {
    val parse = new PartialFunction[String, Int] {
      override def isDefinedAt(s: String): Boolean = s.forall(char => char.isDigit)
      override def apply(s: String): Int = s.toInt
    }

    val total = partialToTotal(parse)

    total("10") shouldBe Some(10)
    total("aaa") shouldBe None
  }

  //EXERCISE - if defined -> Some(f(x)) else None
  def partialToTotal[A, B](f: PartialFunction[A, B]): A => Option[B] =
    a => if (f.isDefinedAt(a)) Some(f(a)) else None


  /**
    * Don't change test - implement 'withNullCheck'
    */
  test("decorate with nullcheck - handle null argument") {
    val parse = (s: String) => s.toInt
    val parseNullCheck = withNullCheck(parse)

    parseNullCheck("10") shouldBe Some(10)
    parseNullCheck(null) shouldBe None
  }

  //if null return None
  def withNullCheck[A, B](f: A => B): A => Option[B] = a => if (a == null) None else Some(f(a))

  //LEVEL 4 - BOSS

  /**
    * .____                      .__       _____   __________
    * |    |    _______  __ ____ |  |     /  |  |  \______   \ ____  ______ ______
    * |    |  _/ __ \  \/ // __ \|  |    /   |  |_  |    |  _//  _ \/  ___//  ___/
    * |    |__\  ___/\   /\  ___/|  |__ /    ^   /  |    |   (  <_> )___ \ \___ \
    * |_______ \___  >\_/  \___  >____/ \____   |   |______  /\____/____  >____  >
    *              \/          \/            |__|          \/           \/     \/
    * .                                           ____    ____  ____________________
    * /\|\/\     /\|\/\     /\|\/\     /\|\/\   |    |   |   \_   _____/\__    ___/  /\|\/\     /\|\/\     /\|\/\     /\|\/\
    * _)    (__  _)    (__  _)    (__  _)    (__ |    |   |   ||    __)    |    |    _)    (__  _)    (__  _)    (__  _)    (__
    * \_     _/  \_     _/  \_     _/  \_     _/ |    |___|   ||     \     |    |    \_     _/  \_     _/  \_     _/  \_     _/
    * )      \     )    \     )    \     )    \  |_______ \___|\___  /     |____|      )    \     )    \     )    \     )    \
    * \/\|\/     \/\|\/     \/\|\/     \/\|\/          \/        \/                  \/\|\/     \/\|\/     \/\|\/     \/\|\/
    */


  test("fight BOSS! Lift Pure function to Option level"){
    val parseTotal : String=>Option[Int] = s=>
      try{Option(s.toInt)}
      catch{case e:Exception => None}

    val tax=0.23

    val gross:Int=>Double = net => net+net*0.23
    val displayCurrency:Double=>String= amount => amount+"$"

    val grossLifted = lift(gross)
    val displayCurrencyLifted = lift(displayCurrency)

    val parseCurrencyWithTax= parseTotal andThen grossLifted andThen displayCurrencyLifted

    parseCurrencyWithTax("100") shouldBe Some("123.0$")
    parseCurrencyWithTax("10") shouldBe Some("12.3$")
    parseCurrencyWithTax("aaaaaaaaaaaaaaaaaaaaarg") shouldBe None
    parseCurrencyWithTax("") shouldBe None

  }

  def lift[A,B](f:A=>B) : Option[A] => Option[B] = a=>a map f

}

