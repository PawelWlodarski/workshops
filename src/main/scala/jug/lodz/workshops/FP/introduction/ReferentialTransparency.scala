package jug.lodz.workshops.FP.introduction

import scala.StringBuilder

object ReferentialTransparency {

  def main(args: Array[String]) {
    testingReferentialTransparency
    //lackOfReferentialTransparency
    //stringRealLifeRF
    //stringRealLifeLackOfRF
  }

  def testingReferentialTransparency: Unit = {
    val a = 2
    val b = a + 1
    val c = a + 2

    println(s"result b=$b, c=$c")

    val b2 = 2 + 1
    val c2 = 2 + 2

    println(s"after substitution everything is the same : result b2=$b2, c2=$c2")
  }
  def lackOfReferentialTransparency: Unit = {
    def a = {println("sideeffect");2}
    val b = a + 1

    val c = a + 2
    println(s"result b=$b, c=$c")

    val b2 = 2 + 1
    val c2 = 2 + 2

    println(s"after substitution we don't have side effects: result b2=$b2, c2=$c2")
  }

  def stringRealLifeRF: Unit = {
    val s1 = "aaa"
    val s2 = s1 + "bbb"
    val s3 = s1 + "ccc"

    println(s"result s2=$s2,s3=$s3")

    val s22 = "aaa" + "bbb"
    val s32 = "aaa" + "ccc"

    println(s"result s22=$s22,s32=$s32")
  }

  def stringRealLifeLackOfRF: Unit = {
    val s1 = new StringBuilder("aaa")
    val s2 = s1.append("bbb")
    val s3 = s1.append("ccc")

    println(s"result s2=$s2,s3=$s3")

    val s22 = new StringBuilder("aaa") append "bbb"
    val s32 = new StringBuilder("aaa") append "ccc"

    println(s"result s22=$s22,s32=$s32")
  }
}