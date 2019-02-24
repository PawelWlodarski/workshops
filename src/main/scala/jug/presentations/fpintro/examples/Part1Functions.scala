package jug.presentations.fpintro.examples

object Part1Functions {

  ///ALIAS
  //simple
  type Title = String
  type Author = String


  //functions - give examples
  type TitlePredicate = Title => Boolean
  type AuthorTransformation = Author => String

  //generics - give examples
  type GenericPredicate[A] = A => Boolean
  type DatabaseLookup[K,V] = Map[K,V] => K => Option[V]   //<----- explain


  def main(args: Array[String]): Unit = {
    //type as documentation
    class Example1(val t:Title,val a:Author,val content:String)
    val example1= new Example1("Pan Tadeusz" , a = "Mickiewicz", "Litwo ...")

    //predicate example
//    val p1:TitlePredicate = ??? -- what is ???
//    println(p1(example1.t))

//    val t1:AuthorTransformation = ???
//    println(t1(example1.a))


    val database = Map(
      1 -> "one",
      2 -> "two"
    )

    //    val lookup :  DatabaseLookup<Int,String> = ???
    //
    //
    //    println("res1 : " + lookup(database)(1))


    //COMPOSITION
    val f1 : Title => Int = t => t.length
    val f2 : Int => Boolean = _ > 4


    val f3: Title => Boolean = f1 andThen f2
    println("title longer than 4 : " + f3(example1.t))
  }

}
