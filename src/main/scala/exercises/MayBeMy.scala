package exercises

import scala.runtime.Nothing$

abstract class MayBeMy[+A] {
  def map[B](transformer: A => B): MayBeMy[B]
  def flatMap[B](transformer: A => MayBeMy[B]): MayBeMy[B]
  def filter(predicate: A => Boolean): MayBeMy[A]

}

case object MayBeMyEmpty extends MayBeMy[Nothing] {
  override def map[B](transformer: Nothing => B): MayBeMy[B] = MayBeMyEmpty
  override def flatMap[B](transformer: Nothing => MayBeMy[B]): MayBeMy[B] = MayBeMyEmpty
  override def filter(predicate: Nothing => Boolean): MayBeMy[Nothing] = MayBeMyEmpty
}

case class MayBeMyCons[+A](h: A) extends MayBeMy[A] {
  override def map[B](transformer: A => B): MayBeMy[B] = MayBeMyCons(transformer(h))
  override def flatMap[B](transformer: A => MayBeMy[B]): MayBeMy[B] = transformer(h)
  override def filter(predicate: A => Boolean): MayBeMy[A] =
    if (predicate.apply(h)) this
    else MayBeMyEmpty





}


object MayBeMyListTest extends App {
  val listInt = MayBeMyCons(3)
  val listString =  MayBeMyCons("a")

  println(listInt)
  println(listString)

  println(listInt.map(_ * 2))
  println(listInt.flatMap(x => MayBeMyCons(x % 2 == 0)))
  println(listInt.filter(_ % 2 == 0))


  val forComprehensionTest = for {
    l1 <- listInt
    l2 <- listString
  } yield l1 + l2

  println(forComprehensionTest)
}