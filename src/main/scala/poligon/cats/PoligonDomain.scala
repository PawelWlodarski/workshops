package poligon.cats

final case class Cat(name: String, age: Int, color: String)

object Cat{

  def main(args: Array[String]): Unit = {
    val inc:Int => Int = i=>i+1

    val decorate : (Int =>Int) => Int=>Int = f => i =>{
      println("decorated")
      f(i)
    }

    println(decorate(inc)(2))

    println(createDecorator()(inc)(2))
  }

  def decorate[A,B](f:A=>B): A=>B = { i =>
  println("decorated")
    f(i)
  }

  type Decorator[A,B] = (A=>B) => A => B

  def createDecorator[A,B]() : Decorator[A,B] = f=>{ i =>
    println("decorated with Alias")
    f(i)
  }
}