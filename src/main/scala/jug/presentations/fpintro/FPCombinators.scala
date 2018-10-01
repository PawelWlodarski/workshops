package jug.presentations.fpintro

object FPCombinators {

  trait Optional[A]

  def unit[A](a: A): Optional[A] = ???
  def flatMap[A, B](fa: Optional[A])(f: A => Optional[B]): Optional[B] = ???


  def map[A, B](fa: Optional[A])(f: A => B): Optional[B] =
    flatMap(fa)(a => unit(f(a)))

  def map2[A, B, C](oa: Optional[A], ob: Optional[B])(f: (A, B) => C): Optional[C] =
    flatMap(oa)(a => map(ob)(b => f(a, b)))

  def compose[A, B, C](fa: A => Optional[B], fb: B => Optional[C]): A => Optional[C] =
    a => flatMap(fa(a))(fb)

  def sequence[A](lma: List[Option[A]]): Option[List[A]] = ???

  def traverse[A, B](la: List[A])(f: A => Optional[B]): Optional[List[B]] = ???


}