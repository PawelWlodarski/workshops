package jug.lodz.workshops.FP.introduction

object HigherOrderFunctions {

  type Strategy = (Int,Int)=>Int
  type Comparator=(Int,Int)=>Boolean

  def main(args: Array[String]) {
    hofInDisquise()
    /*println{
      customMap(List(1,2,3,4,5),_*2)
    }*/
    //testHighOrderedFunction()
  }

  def hofInDisquise()={
    val list=List(1,2,3,4,5,6,7,8,9,10)

    val addStrategy:Strategy=_+_

    val reverseCompareStrategy:Comparator = (a,b) => a>b

    println(s"reduction strategy : ${list reduce addStrategy}")
    println(s"reversed comparator ${list sortWith reverseCompareStrategy}")
  }

  def customMap(l:List[Int],f:Int=>Int):List[Int]=
    if(l.isEmpty)
      List[Int]()
    else
      f(l.head) :: customMap(l.tail,f)


  def testHighOrderedFunction(): Unit ={
    val withLogging: ((Int,Int)=>Int) => ((Int,Int)=>Int) = f =>
      (a,b)=>{
        println(s"a=$a, b=$b")
        f(a,b)
      }

    type TwoParamFunction=(Int,Int)=>Int

    val max=(a:Int,b:Int)=> if (a>b) a else b

    val decoratedMax=withLogging(max)

    println(decoratedMax(5,7))
  }
}
