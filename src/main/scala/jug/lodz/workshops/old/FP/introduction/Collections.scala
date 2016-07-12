package jug.lodz.workshops.old.FP.introduction

object Collections {

  def main(args: Array[String]) {
    //testingImmutableCollections
    //testingMutableCollections
  }


  def testingImmutableCollections: Unit = {
    //Generally
    val list1 = List(4, 5, 6)
    val list2 = 3 :: list1

    //memory space is reused

    println(s"eq ${list2 eq list1}") //false
    println(s"eq tail : ${list2.tail eq list1}") //true

    //and this is completely different list

    val list1b = List(4, 5, 6)
    println(s"different list : ${list1 eq list1b}") //false
  }
  def testingMutableCollections: Unit = {
    val mutableList = scala.collection.mutable.ListBuffer(2, 3, 4, 5) //ListBuffer(2, 3, 4, 5)
    val mutableList2 = mutableList.tail //ListBuffer(3, 4, 5)
    println(mutableList2.prepend(1)) //res3: Unit = () !!!!
    println(mutableList2.tail eq mutableList) //Boolean = false
  }
}
