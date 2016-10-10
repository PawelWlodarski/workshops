package jug.lodz.workshops.implicits

/**
  * Created by pawel on 26.09.16.
  */
object ImplicitsPart2ComputationState {

  def main(args: Array[String]): Unit = {
      //two apis
  }

  class Basket {
    //show that longer name may not be a better choice
    private var ps=List[Product]()

    def put(p:Product) :Unit = ps=p :: ps
    def remove(p:Product) : Unit = ps = ps filter (_ != p)
    def products = ps
  }



  object Basket{
    case class Product(name:String,price:Int)
  }

}
