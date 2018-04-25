package poligon.cats

final case class Cat(name: String, age: Int, color: String)

object Cat{
  import cats.Show
  import cats.Eq
  import cats.syntax.show._
  import cats.syntax.eq._
  import cats.instances.int._
  import cats.instances.string._

  implicit val catShow= Show.show[Cat]{cat =>
    val name = cat.name.show
    val age = cat.age.show
    val color = cat.color.show
    s"$name is a $age year-old $color cat."
  }

  implicit val catEq = Eq.instance[Cat]{(cat1,cat2)=>
    (cat1.name === cat2.name) && (cat1.age === cat2.age)
  }


  val cat1=Cat("Garfield", 38, "orange and black")
  val cat2=Cat("Heathcliff", 32, "orange and black")
}

final case class Order(totalCost: Double, quantity: Double)

object Order{
  import cats.Monoid

  implicit val orderMonoid=new Monoid[Order]{
    override def empty: Order = Order(0,0)

    override def combine(x: Order, y: Order): Order = Order(x.totalCost+y.totalCost,x.quantity+y.quantity)
  }
}