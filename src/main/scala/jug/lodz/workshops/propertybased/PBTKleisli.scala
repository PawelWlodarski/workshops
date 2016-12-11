package jug.lodz.workshops.propertybased


/**
  * Created by pawel on 06.03.16.
  */
object PBTKleisli {



  def main(args: Array[String]) {
    import cats.data.Kleisli
    import cats.implicits._
    val split:String=>List[String] = s=>s.split(" ").toList
    val variants:String=>List[String] = s=>List(s,s.toUpperCase,s.toLowerCase)

    val variantsK = Kleisli(split).andThen(variants)

    println(variantsK.run("this is a sentence"))
    //List(this, THIS, this, is, IS, is, a, A, a, sentence, SENTENCE, sentence)

  }
}
