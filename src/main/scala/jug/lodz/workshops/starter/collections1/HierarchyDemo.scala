package jug.lodz.workshops.starter.collections1

import jug.lodz.workshops.WorkshopDisplayer

object HierarchyDemo extends WorkshopDisplayer{

  def main(args: Array[String]): Unit = {
    header("Collections")
    //show hierarchy picture

    title("traversable")
    //show aply and how complicated collection framework is inside but very usable outside
    val l: Traversable[Int] =Traversable(1,2,3)

    section("foreach - sideeffects")
    val sideEffect : Int => Unit = println
    l.foreach(sideEffect)

    section("map - gateway to Fucntors")
    val pure:Int=>Int = _+1
    l.map(pure).foreach(sideEffect)


    section("collect")
    val partialCollect:PartialFunction[Int,Int]={
      case i:Int if i>1 => i*2
    }

    l.collect(partialCollect).foreach(sideEffect)

    section("copy to array")
    val a=Array[Int]()
    l.copyToArray(a)

    a.foreach(sideEffect)  //foreach here is implicit , explain implicits on array

    section("subcollections")

    section("filter",l.filter(_>1).mkString(","))
    section("groupBy",l.groupBy(_>1).mkString(","))
    section("partition",l.partition(_>1))


    section("checking")
    section("exists _>1",l.exists(_>1))
    section("forall _>1",l.forall(_>1))


    title("iterable")

    section("grouped")
    val it=Iterable(1,2,3,4,5)  //switch to worksheet
    println(it.grouped(3))
    println(it.grouped(3).next())
    println({val gr=it.grouped(3);gr.next();gr.next()})

    section("sliding")
    val slid=it.sliding(3)
    println(slid.next())
    println(slid.next())
    println(slid.next())


    title("Seq - Iterable with length and indexing")
    val s:Seq[Int]= 1 to 10

    println(s.mkString(","))
    section("length",s.length)
    section("last indexWhere even",s.lastIndexWhere(_ % 2 ==0))
    section("+:, :+", 0 +: s :+11)
    section("update",s.updated(5,100))
    section("sort reverse",s.sortWith(_ > _))
    section("padding",s.padTo(15,0))
    section("s(3)",s(3))

  }

}
