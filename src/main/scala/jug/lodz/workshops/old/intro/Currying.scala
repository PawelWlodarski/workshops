package jug.lodz.workshops.old.intro


object Currying {



  def main(args: Array[String]) {
    domainExample

    customStructure
  }


  def domainExample  = {
    //To na tę chwilę jedynie aliasy
    type Cash=Int
    type Policy= Cash=>Boolean
    type BusinessLogic= Cash=>Cash

    //Logika abstrakcyjna
    def cashService(p:Policy)(l:BusinessLogic)(c:Cash):Cash={
      if(p(c)) l(c) else 0
    }

    //niezależne i łątwe do testów funkcje
    def strictPolicy(c:Cash)=c>50
    def moneyMakesMoney(c:Cash)=c*1000

    //konkretna usługa
    val bank=cashService(strictPolicy)(moneyMakesMoney)_
    bank(100)  //res0: Cash = 100000
    bank(10)    //res1: Cash = 0
  }

  def customStructure = {
    //Przykład długi jedynie do analizy
    trait Resource{
      def read:String
    }

    trait SystemResource extends Resource{
      def open:Unit
      def close:Unit
    }

    //currying i high order fucntion w jednym
    def HandleResource[A](sr:SystemResource)(f:Resource=>A)={
      try {
        sr.open
        f(sr)
      }finally{
        sr.close
      }
    }


    def readResource(r:Resource):String=r.read

    val resource=new SystemResource {
      override def close: Unit = println("close")
      override def open: Unit = println("open")
      override def read: String = "data"
    }

    //Teraz samodzielnie przechodzimy przez wszystkie 5 opcji
    //opcja 1
    HandleResource(resource)(readResource)
    //opcja 2
    HandleResource(resource)((r:Resource)=>r.read)
    //opcja 3 - tu się dzieje bardzo ważna rzecz. jeśli to by było
    //(sr:SystemResource,f:Resource=>A) czyli jedna para nawiasów to trzeba by podać typ readera
    //podyskutowac dlaczego?
    HandleResource(resource)(r=>r.read)
    //opcja 4- własna struktura
    HandleResource(resource){r=>
      r.read
    }
    //opcja 5 - wytłumaczyć po co ten [String]
    val concreteResource=HandleResource[String](resource)_
    concreteResource{
      _.read
    }
  }
}
