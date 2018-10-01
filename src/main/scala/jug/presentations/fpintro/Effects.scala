package jug.presentations.fpintro

object Effects {
  def main(args: Array[String]): Unit = {
    type Time =Long
    type Key=Int

    var state:Map[Key,String] = Map()

    //implicit time parameter
    val f: (Time,Key) => String = (time,key) => {
      //emulating state occurance after some time
      if(time > 20 && time < 237) state=state + (key -> "value")

      state(key)
    }

    val curriedTime: Time => Key => String =f.curried


    val r: Key => String =curriedTime(20)  //Still not deterministic :(


    def swap[A,B,C](f:A=>B=>C) : B => A => C = b => a => f(a)(b)

    val swappedTime: Key => Time => String = swap(curriedTime)


    //DESCRIPTION
    // - total if domain is total
    // - no exception
    // - valid function
    val programDescription: Time => String =swappedTime(69)

    //EXECUTION!!! - non deterministic, non functional, real world
    try{
      val executionResult: String = programDescription(20)
    }catch{
      case e:Exception => println("jebło")
    }


    type Execution[A] = Time => A

    val programDescriptionAlias : Execution[String] = programDescription

    class ProgramEffect[A](unit : Execution[A]){
      def run(t:Time) = unit(t)
      def map[B](f:A=>B) : ProgramEffect[B] = new ProgramEffect(unit andThen f)
    }



    val key = 69
    val p =new Program[Map[Key,String],String](m => m(key))
    val programResult: String =p.run(Map(69 -> "result"))
    println(programResult)

    //Slick example

  }
}


//COST - COMPILATION TIME
class Program[INPUT,A](val unit : INPUT => A) extends AnyVal{
  def run(i:INPUT) = unit(i)
  def map[B](f:A=>B) : Program[INPUT,B] = new Program((i:INPUT) => f(unit(i)) )
  def flatMap[B](f:A=>Program[INPUT,B]) : Program[INPUT,B] = ???
  //(...)
}
