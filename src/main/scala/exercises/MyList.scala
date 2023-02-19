package exercises

import lectures.part2oop.Generics.MyList

import scala.runtime.Nothing$

//import lectures.part2oop.Generics.MyList

abstract class MyList[+A] {

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]

  def map[B](transformer: A => B): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]

  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]
  def fold[B](start: B)(f: (B, A) => B): B

  def ++[B >:A](list: MyList[B]): MyList[B]

  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

case object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException
  override def tail: MyList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  override def printElements: String = ""

  override def map[B](transformer: Nothing => B): MyList[B] = Empty
  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list

  override def foreach(f: Nothing => Unit): Unit = ()
  override def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty
  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty) throw new RuntimeException("lists does not have same elements")
    else Empty

  override def fold[B](start: B)(f: (B, Nothing) => B): B = start


}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) "" + h
    else "" + h + " " + t.printElements

  def ++[B >:A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  override def filter(predicate: A => Boolean): MyList[A] =
    if (predicate.apply(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)

  override def map[B](transformer: A => B): MyList[B] =
    new Cons(transformer(h), t.map(transformer))

  override def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer)

  override def foreach(f: A => Unit): Unit = {
    f(h)
    tail.foreach(f)
  }

  override def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) new Cons[A](x, Empty)
      else if (compare(x, sortedList.head) <= 0) Cons(x, sortedList)
      else Cons(sortedList.head, insert(x, sortedList.tail))
    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] =
    if (list.isEmpty) throw new RuntimeException("lists does not have same elements")
    else Cons(zip(h, list.head), t.zipWith(list.tail, zip))

  override def fold[B](start: B)(f: (B, A) => B): B = {
    val newStart = f(start, h)
    t.fold(newStart)(f)
  }

}
//
//trait MyPredicate[-T] {
//  def test(p: T): Boolean
//}
//
//trait MyTransformer[-A, B] {
//  def transform(element: A): B
//}
//
//class EvenPredicate extends MyPredicate[Int] {
//  override def test(p: Int): Boolean = p % 2 == 0
//}
//
//class StringToIntTransformer extends MyTransformer[String, Int] {
//  override def transform(element: String): Int = element.toInt
//}

object ListTest extends App {
  val list = new Cons(1, new Cons(2, new Cons("a", Empty)))
  val dbllist = new Cons(1, new Cons(2, new Cons("a", Empty)))
  val listOfInt = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val anotherlistOfInt = new Cons(4, new Cons(5, new Cons( 9, Empty)))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.isEmpty)
  println(list.toString)
  println(listOfInt.map(x => x * 2).toString)
  println(listOfInt.filter(_ % 2 == 0).toString)
  println(listOfInt ++ anotherlistOfInt)
  println(listOfInt.flatMap(x => new Cons(x, new Cons(x +1, Empty))).toString)

  //case class
  println(list == dbllist)

  val concatinator: (String, String) => String = _ + _

  println(concatinator("Hello ", "Scala"))

  val superAdder = new Function[Int, Int => Int] {
    override def apply(x: Int): Int => Int = new Function[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val superAdder2 = (x: Int) => (y: Int) => x + y

  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4)) //curried function
  println(superAdder2(3)(4)) //curried function

  //hofs
  println("---")
  anotherlistOfInt.foreach(println)
  println("---")

  anotherlistOfInt.sort((x, y) => y - x).foreach(println)
  anotherlistOfInt.zipWith(listOfInt, _ + _).foreach(println)

  println("---")
  println(anotherlistOfInt.fold(0)(_ + _))

  def toCurry(f: (Int, Int) => Int): Int => Int => Int = (x: Int) => (y: Int) => f(x, y) // x => y => f(x, y)
  def fromCurry(f: Int => Int => Int): (Int, Int) => Int = (x: Int, y: Int) => f(x)(y) // (x, y) => f(x)(y)

  def compose[A,B,T](f: A => B, g: T => A): T => B = x => f(g(x))
  def andThen[A,B,C](f: A => B, g: B => C): A => C = x => g(f(x))

  val forComprehensionTest = for {
    l1 <- listOfInt
    l2 <- anotherlistOfInt
  } yield l1 + l2

  println(forComprehensionTest)
}