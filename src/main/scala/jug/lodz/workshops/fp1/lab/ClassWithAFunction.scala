package jug.lodz.workshops.fp1.lab

/**
  * Created by pawel on 21.02.16.
  */
class ClassWithAFunction {

  val fun:Int=>Int = x=>x+1

  def add(x:Int):Int = fun(x)
}
