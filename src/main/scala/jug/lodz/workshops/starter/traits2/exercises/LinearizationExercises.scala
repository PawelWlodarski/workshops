package jug.lodz.workshops.starter.traits2.exercises

object LinearizationExercises {

  class Auth(val login:String,val  password:String)
  class Request(val url:String,val browser:String,val location:String,val auth:Auth){
    def login=auth.login
    def password=auth.password
  }
  class Response(val body:String,val status:Int)


  trait Controller{
    def handle(r:Request):Response
  }

  //EXERCISE 1
  class ShopController extends Controller{
    override def handle(r: Request): Response = ???
  }

  object ShopController{
    def notFound(url:String)=new Response(s"Page $url not found.",404)
    def forbidden(url:String)=new Response(s"Page $url is forbidden.",403)
  }

  //EXERCISE2
  trait LocationFilter extends Controller{
    abstract override def handle(r: Request): Response = ???
  }

  trait LocationMaskingFilter extends Controller{
    abstract override def handle(r: Request): Response = ???

  }


  //EXERCISE3
  trait SystemCredentials{
    val credentials:Map[String,String]=Map("user1"->"password1")
  }

  trait SecurityFilter extends Controller with SystemCredentials{
    abstract override def handle(r: Request): Response = ???
  }


}
