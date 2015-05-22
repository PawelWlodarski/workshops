package jug.lodz.workshops.FP.introduction

object Mutability {


  def main(args: Array[String]) {
    val u=new User("Stefan")
    u.name="Zdzichu"

    println(u.name)


    /* Example1
    val two=new Number(2)
    val three=new Number(3)

    val result=(two + three)* two + two
    println(result)
    */
  }

  class User(var name:String) // var generates both getter and setter

  class Number(var value:Int){  // <- mutable state, just like User
  def +(other:Number)={
    this.value=this.value+other.value
    this
  }
    def *(other:Number)={
      this.value=this.value*other.value
      this
    }

    override def toString()=s"${value}"
  }
}
