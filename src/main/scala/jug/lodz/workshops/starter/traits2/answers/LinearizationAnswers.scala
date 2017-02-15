package jug.lodz.workshops.starter.traits2.answers

object LinearizationAnswers {

  class Auth(val login:String,val  password:String)
  class Request(val url:String,val browser:String,val location:String,auth:Auth){
    def login=auth.login
    def password=auth.password
  }
  class Response(val body:String,val status:Int)


  trait Controller{
    def handle(r:Request):Response
  }

  //EXERCISE 1
  class ShopController extends Controller{
    override def handle(r: Request): Response =
      if(r.url=="/shop") new Response("<h1>Welcome in Shop</h1>",200)
      else ShopController.notFound(r.url)
  }

  object ShopController{
    def notFound(url:String)=new Response(s"Page $url not found.",404)
    def forbidden(url:String)=new Response(s"Page $url is forbidden.",403)
  }



  //EXERCISE3
  trait SystemCredentials{
    val credentials:Map[String,String]=Map("user1"->"password1")
  }

  trait SecurityFilter extends Controller with SystemCredentials{
    abstract override def handle(r: Request): Response = {
      if(credentials.get(r.login).forall(password => password == r.password))
        super.handle(r)
      else
        ShopController.forbidden(r.url)
    }
  }


}
