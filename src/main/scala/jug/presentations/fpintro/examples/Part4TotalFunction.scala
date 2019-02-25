package jug.presentations.fpintro.examples

object Part4TotalFunction {

  sealed trait DomainOrder

  case class GenericOrder(id:Int, name : String, content:List[String]) extends DomainOrder
  case class SpecialOrder(id:Int, name : String, content:List[String],discount:Int) extends DomainOrder
  case class DeniedOrder(reason:String) extends DomainOrder

  val order = List(
    GenericOrder(1,"Generic",List("PC","Hard Drive")),
    SpecialOrder(1,"Special",List("PC","Hard Drive"),discount = 20),
    DeniedOrder("denied just because")
  )


  //SHOW exchaustive
  def processOrder(o:DomainOrder) = o match {
    case GenericOrder(id, name, content) => println(s"generic : $id, $name")
    case SpecialOrder(id, name, content, discount) => println(s"special : $id , $name")
    case DeniedOrder(reason) => println(s"denied : $reason")
  }

  def main(args: Array[String]): Unit = {
    order.foreach(processOrder)
  }

}


