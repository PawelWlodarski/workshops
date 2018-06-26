package jug.presentations.fpintro

object FPCombinators {

  trait Optional[A]

  def unit[A](a:A) :Optional[A] = ???
  def flatMap[A,B](fa:Optional[A])(f : A=>Optional[B] ) : Optional[B] = ???



//    def  map[A,B](fa:Optional[A])(f : A=>B ) : Optional[B] =
//      flatMap(fa)(a=>unit(f(a)))
//
//      fun <A,B,C> map2(oa:Optional<A>,ob:Optional<B>)(f : (A,B) ->C ) : Optional<C> =
//        flatMap(oa)(a->map(b)(f(a,b)))
//
//        fun <A,B,C> compose(fa:A→Optional<B>,fb:B→Optional<C>):A→Optional<C> =
//          a->flatmap(fa(a))(fb)
//
//          fun  <A> sequence(lma: List<Option<A>>): Option<List<A>> = …
//
//            fun  <A,B> traverse(la: List<A>)(f: A => Optional<B>): Optional<List<B>> =
//
//              }
}