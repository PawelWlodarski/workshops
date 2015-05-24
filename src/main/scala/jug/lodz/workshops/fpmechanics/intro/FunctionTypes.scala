package jug.lodz.workshops.fpmechanics.intro

object FunctionTypes {


  def main(args: Array[String]) {
    types
    typesRevealed
  }

  def types: Unit = {
    def metoda(a:Int)(b:Int)=a+b
    metoda _

    def metoda2(a:Int)(b:Int)(c:Int)(d:Int)=a+b+c+d
    metoda2 _

    val f0=1
    val f1=(a:Int)=>f0
    val f2=(a:Int)=>f1
    val f3=(a:Int)=>f2
    val f4=(a:Int)=>f3

    f4(1) // zwraca f3
    f4(1)(2) //zwraca f2
    f4(1)(2)(3) //zwraca f1
    f4(1)(2)(3)(4) //zwraca f0
  }

  def typesRevealed[A]: Unit = {
    def m1[A](a: A, f: A => String) = f(a)
    def m2[A](a: A)(f: A => String) = f(a)
    // m1(100, i => s"$i + $i") //error: missing parameter type
    m2(100)(i => s"$i + $i") //res0: String = 100 + 100
  }



}
