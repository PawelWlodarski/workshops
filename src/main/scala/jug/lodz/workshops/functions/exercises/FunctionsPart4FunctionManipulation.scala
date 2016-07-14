package jug.lodz.workshops.functions.exercises

/**
  * Created by pawel on 04.07.16.
  */
object FunctionsPart4FunctionManipulation {


  def main(args: Array[String]) {
    println("\n  * MANIPULATE A FUNCTION \n\n")


    println("\n  *** SECTION : CREATE A FUNCTION IN METHOD")

    //at the begining this is similar to what we did in section 3
    // you can declare internal method
    def createIncrementFunction():Int=>Int = i=>i+1

//CODE    //create function and assign it to value
//    val increment:Int=>Int = createIncrementFunction()
//
//    println("   * invoke just created increment function f(1): "+increment(1))

    println("\n  ***SECTION :  TYPE ALIASES")

    //easier to read types?
    type AliasToInt=Int
    type Incr = Int=>Int

//    CODE
//    def createIncrementFunctionAlias():Incr = i=>i+1
//    println("   * calling function with alias type f(1): "+createIncrementFunctionAlias()(1))

    println("\n  ***SECTION :  INJECT VALUE AND CREATE METHOD")

    type Currency = String

    def injectCurrency(curr : Currency): Double => Currency = amount => amount+curr

// CODE - inject dollar sign
//    val dollars: Double => String = injectCurrency("$")
//
//    println("    *  amount (12.73) in dollars "+dollars(12.73))


    println("\n  ***SECTION :  MODIFY FUNCTION ***")

    println("\n   * :  LOGGING") //PRACTICE WRITING RETURN FUNCTION FROM METHOD
//    def withLogging[A,B](f:A=>B) : A=>B = //A=>B - the same type
//      a=>{
//        println("INFO : invoking with arg : "+a)
//        f(a)
//      }
//
//    val inc=(i:Int) => i+1
//    val incWithLogging = withLogging(inc)

    //SHOULD LOG ARG
//    incWithLogging(5)

    println("\n   * :  PARTIAL APPLICATION")

    def partial[A,B,C](f:(A,B)=>C,arg:A) : B=>C  = // no A in return type
      b=>f(arg,b)

    //partially apply argument to function which looks for argument
    val containsElement = (l:List[Int],element: Int) => l.contains(element)
    val someList=List(1,2,3,4,5)
    val containsInSomeList = partial(containsElement, someList)

    println("contains with some list applied (3): "+containsInSomeList(3))
    println("contains with some list applied (7): "+containsInSomeList(7))

    //partial application  ---> swap to apply different argument

//    println("\n   * :  SWAP")
//    def swap[A,B,C](f:(A,B)=>C):(B,A) =>C =    //SWAP - small function - packed logic
//        (b,a) => f(a,b)
//
//    val containsElementSwapped: (Int, List[Int]) => Boolean = swap(containsElement)  //compare with original
//
    //PARTIALLY APPLY OTHER ARGUMENT
//    val containsSeven = partial(containsElementSwapped, 7)
//
//    println("contains seven List(1,2,3,4,5): "+containsSeven(List(1,2,3,4,5)))
//    println("contains seven List(1,2,7,4,5): "+containsSeven(List(1,2,7,4,5)))


//CODE : DOMAIN EXAMPLE
//    println("\n  ***SECTION :  DOMAIN EXAMPLE ***")
//
//    println("     *  Domain example : strict bank with 1000 revenue : bank(10) : "+DomainExample.strictBank(10))
//    println("     *  Domain example : strict bank with 1000 revenue : bank(100) : "+DomainExample.strictBank(100))

  }
}

//ETA expansion - method in place of functions
object DomainExample{
  //Money domain
  type Cash=Int
  type Policy= Cash=>Boolean
  type BusinessLogic= Cash=>Cash

  //most general logic - DI?
  def cashService(p:Policy)(l:BusinessLogic)(c:Cash):Cash={
    if(p(c)) l(c) else 0
  }

  //independent functions easy to test
  // Strict policy accepts only amounts larger than 50
  def strictPolicy(c:Cash):Boolean=c>50
  def moneyMakesMoney(c:Cash):Cash=c*1000

  //concrete service
  val strictBank=cashService(strictPolicy)(moneyMakesMoney)_
  strictBank(100)  //res0: Cash = 100000
  strictBank(10)    //res1: Cash = 0
}