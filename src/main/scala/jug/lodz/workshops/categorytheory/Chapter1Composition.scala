package jug.lodz.workshops.categorytheory

import jug.lodz.workshops.WorkshopDisplayer

object Chapter1Composition extends WorkshopDisplayer{

  def main(args: Array[String]): Unit = {
    header("Composition")

    //no generics in function type

    case class User(name:String,email:String)
    type A = User
    type B = String
    type C = Int
    val f1:A=>B = u =>u.email
    val f2:B => C = e=>e.length

    //composition A=>C
    val composed: A => C =f1 andThen f2
    val composed2: A => C =f2 compose f1

    val u=User("Roman","roman@gmail.com")
    section("composed email length f1 andThen f2", composed(u))
    section("composed email length f1 composed f2", composed2(u))

    section("properties of composition")
    type D= Boolean
    val f3: C=>D = l=>l > 5

    //EQUALITY
    val e1=f3 compose ( f2 compose f1)
    val e2=(f3 compose  f2) compose f1
    val e3=f3 compose  f2 compose f1

    section("eqality", e1(u) == e2(u) == e3(u))

    //IDENTITY

    val incr = (i:Int) => i+1

    section("identity compose", incr.compose(identity[Int])(1) == incr(1))
    section("identity andThen", incr.andThen(identity)(1) == incr(1))


  }

}
