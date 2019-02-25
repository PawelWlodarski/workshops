package jug.presentations.fpintro.examples

object Part3DomainTypes {

  def main(args: Array[String]): Unit = {
    val m1=Money(7)

//    Money(-100) RuntimeException - not perfect but no illegal Money during runtime
//    businessOperation(-100) //illegal

    println("business operation : " + businessOperation(m1).display())


  }


  class Money private(val value:Int) extends AnyVal{
    def add(other:Money) = new Money(this.value+other.value)
    def display():String = s"Money($value)"

    override def toString: String = s"Money($value)"
  }

  object Money{
    def apply(i:Int) : Money = {
      require(i > 0 , "some meaningful message")
      new Money(i)
    }
  }


  def businessOperation(m:Money):Money = m.add(Money(2))
}
