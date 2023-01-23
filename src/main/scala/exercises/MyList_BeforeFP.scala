//package exercises
//
//import lectures.part2oop.Generics.MyList
//
//abstract class MyList[+A] {
//
//  def head: A
//  def tail: MyList[A]
//  def isEmpty: Boolean
//  def add[B >: A](element: B): MyList[B]
//
//  def map[B](transformer: MyTransformer[A, B]): MyList[B]
//  def filter(predicate: MyPredicate[A]): MyList[A]
//  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
//
//  def ++[B >:A](list: MyList[B]): MyList[B]
//
//  def printElements: String
//  override def toString: String = "[" + printElements + "]"
//}
//
//case object Empty extends MyList[Nothing] {
//  override def head: Nothing = throw new NoSuchElementException
//  override def tail: MyList[Nothing] = throw new NoSuchElementException
//  override def isEmpty: Boolean = true
//  override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
//  override def printElements: String = ""
//
//  override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
//  override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
//  override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
//  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
//}
//
//case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
//  override def head: A = h
//  override def tail: MyList[A] = t
//  override def isEmpty: Boolean = false
//  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)
//
//  override def printElements: String =
//    if (t.isEmpty) "" + h
//    else "" + h + " " + t.printElements
//
//  def ++[B >:A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)
//
//  override def filter(predicate: MyPredicate[A]): MyList[A] =
//    if (predicate.test(h)) new Cons(h, t.filter(predicate))
//    else t.filter(predicate)
//
//  override def map[B](transformer: MyTransformer[A, B]): MyList[B] =
//    new Cons(transformer.transform(h), t.map(transformer))
//
//  override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
//    transformer.transform(h) ++ t.flatMap(transformer)
//}
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
//
//object ListTest extends App {
//  val list = new Cons(1, new Cons(2, new Cons("a", Empty)))
//  val dbllist = new Cons(1, new Cons(2, new Cons("a", Empty)))
//  val listOfInt = new Cons(1, new Cons(2, new Cons(3, Empty)))
//  val anotherlistOfInt = new Cons(4, new Cons(5, Empty))
//  println(list.tail.head)
//  println(list.add(4).head)
//  println(list.isEmpty)
//  println(list.toString)
//  println(listOfInt.map(new MyTransformer[Int, Int] {
//    override def transform(element: Int): Int = element * 2
//  }).toString)
//  println(listOfInt.filter(new MyPredicate[Int] {
//    override def test(p: Int): Boolean = p % 2 == 0
//  }).toString)
//  println(listOfInt ++ anotherlistOfInt)
//  println(listOfInt.flatMap(new MyTransformer[Int, MyList[Int]] {
//    override def transform(element: Int): MyList[Int] = new Cons(element, new Cons(element +1, Empty))
//  }).toString)
//
//  //case class
//  println(list == dbllist)
//
//  val conc = new Function2[String, String, String] {
//    override def apply(v1: String, v2: String): String = v1 + v2
//  }
//}