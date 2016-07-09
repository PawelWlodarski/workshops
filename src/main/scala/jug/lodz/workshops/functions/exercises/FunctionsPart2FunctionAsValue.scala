package jug.lodz.workshops.functions.exercises

/**
  * Created by pawel on 04.07.16.
  */
object FunctionsPart2FunctionAsValue {


  def main(args: Array[String]) {
    println("\n\n FUNCTIONS AS VALUE \n")

    println(" * DRY & MODULARISATION")

    val es=Seq(1,2,3,4,5)

//CODE
//    def sum(init:Int, es:Seq[Int]): Int ={
//        var result=init //var is bad but here it is encapsulated and fucntion is short
//        for(e <- es) result=result+e
//        result
//    }
//
//    println("    * sum : "+sum(0,es))    // why init == 0  ?


//CODE : almost identical
//    def multiply(init:Int, es:Seq[Int]): Int ={
//      var result=init
//      for(e <- es) result=result*e   //only one difference
//      result
//    }
//
//    println("    * multiply : "+multiply(1,es))   // why init == 1 ?


//CODE : ABSTRACTION
//    def reduce(init:Int,es:Seq[Int], op: (Int,Int)=>Int): Int ={
//      var result=init
//      for(e <- es) result=op(result,e)   //only one difference
//      result
//    }
//
//    println("    * sum by reduce : "+reduce(0,es,(e1,e2)=>e1+e2))   // underscore-emoticon version reduce(0,es,_+_)
//    println("    * multiply by reduce : "+reduce(1,es,(e1,e2)=>e1*e2))   // underscore-emoticon version reduce(0,es,_*_)
//
//    println("    * from library : "+es.reduce((e1,e2)=>e1+e2))
//    println("    * from library : "+es.reduce((e1,e2)=>e1*e2 ))


//CODE : ABSTRACTION and GENERICS
//    def reduceGeneric[A](es:Seq[A],init:A, op: (A,A)=>A): A ={
//      var result:A=init
//      for(e <- es) result=op(result,e)   //only one difference
//      result
//    }
//
//    val joinString:(String,String) => String =  (s1,s2) => s1+" : "+s2
//    val strings=Seq("1","2","3")
//
//    println("    * reduce generic : "+reduceGeneric(strings,"",joinString))

    println(" * A FUNCTION RECEIVING A FUNCTION")

    val ints=Seq(1,2,3,4,5)

//CODE : FUNCTION RECEIVING FUNCTION
//    val mapReduceClosure: (Int=>Int) => Int = f=>ints.map(f).reduce(_+_)
//
//
//    println("    * mapReduceClosure (x+1) "+mapReduceClosure(e=>e+1))
//    println("    * mapReduceClosure (x*x) "+mapReduceClosure(e=>e*e))


//CODE : WITHOUT CLOSURE - Dependency injection?
//    val mapReduce: Seq[Int] => (Int=>Int) => Int = elements=> f=>elements.map(f).reduce(_+_)
//
//    println("    * mapReduce (1,2,3,4,5) (x+1) "+mapReduce(ints)(e=>e+1))
//    println("    * mapReduce (10,20,30) (x+1) "+mapReduce(Seq(10,20,30))(e=>e+1))


    println(" * *** LOAN PATTERN ***")

//CODE = FINALLY A PATTERN!
//    //a simple function easy to test
//    val countLines:Iterator[String] => Int = it=>it.size
//
//    println(s"    * simple test counting lines : "+countLines(Iterator("line1","line2","line3")))
//
//    //loan method
//    def loanFile[A](path:String,f:Iterator[String]=>A): A ={
//      val source = scala.io.Source.fromFile(path)
//      try{
//            f(source.getLines())
//      }finally{
//        source.close()
//      }
//    }
//
//    //change path to your own
//    val linesInFile=loanFile("/tmp/file.txt",countLines)
//    println(s"    * lines in file : $linesInFile");


    //CODE - FOR EXERCISES EXPLAIN FOR-LOOP BASIC USAGE (WITHOUT YILED)
  }
}
