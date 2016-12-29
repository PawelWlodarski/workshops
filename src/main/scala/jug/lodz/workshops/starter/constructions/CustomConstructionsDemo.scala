package jug.lodz.workshops.starter.constructions

object CustomConstructionsDemo {

  def main(args: Array[String]): Unit = {
    println(" *** SCALA STARTER CUSTOM CONSTRUCTIONS ***")

    //syntax evolution
    //1) Simple function ARG => RESULT
    val addOne:Int=>Int = i => i+1

    println(" *** simple function : addOne ***")
    println(addOne(1))
    println(addOne(5))

    //2) Standard method
    def add(a:Int,b:Int):Int = a+b

    println(" *** standarxd method : add ***")
    println(add(1,3))
    println(add(4,6))


    //3)Multiple parameters groups
    def add2(a:Int)(b:Int) = a+b

    println(" *** method with multiple parameters : add2 ***")
    println(add2(1)(3))
    println(add2(4)(6))

    //4) Method with function asa parameter
    println(" *** method with function as a parameter : highOrder ***")
    def highOrderMethod(f:Int=>Int) = f(1)

    println(highOrderMethod(addOne))
    println(highOrderMethod((i:Int)=>i+1))
    println(highOrderMethod(i=>i+1))
    println(highOrderMethod(_+1))


    //5) Function invocation context
    println("method invokes passed function with provided param")
    def invokef(a:Int,f:Int=>Int) = f(a)

    println(invokef(1,addOne))
    println(invokef(5,addOne))

    //6) syntax evolution
    def invokef2(a:Int)(f:Int=>Int) = f(a)

    invokef2(1)(addOne)
    invokef2(5)(addOne)

    //syntax

    invokef2(3)((i:Int)=>i*2)
    invokef2(3)({(i:Int)=>i*2})
    invokef2(3){(i:Int)=>i*2}

    invokef2(3){(i:Int)=>
      i*2
    }

    val result=invokef2(3){i=>
      i*2
    }
    println(s"result of invoked function inside method with changed syntax : $result")

    //7)LOAN PATTERN
    println("*** LOAN PATTERN ***")
    class CustomConnection{
      def open():Unit=println("openning connection...")
      def close():Unit=println("closing connection...")
      def query(q:String):List[String]={
        println(s"sending query :  $q")
        List("result1","result2")
      }
    }

    def withConnection(c:CustomConnection)(operation : CustomConnection=>List[String]):List[String] = {
      try{
        c.open()
        operation(c)
      }finally{
        c.close()
      }
    }

    val resultLoan=withConnection(new CustomConnection()){c=>
      c.query("select * from results")
    }

    println("*** LOAN PATTERN RESULTS***")
    resultLoan.foreach(println)

  }

}
