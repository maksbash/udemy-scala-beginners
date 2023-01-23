package exercises

//import lectures.part2oop.Generics.MyList

abstract class MyList[+A] {

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]

  def map[B](transformer: A => B): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]

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
}

trait MyPredicate[-T] {
  def test(p: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

class EvenPredicate extends MyPredicate[Int] {
  override def test(p: Int): Boolean = p % 2 == 0
}

class StringToIntTransformer extends MyTransformer[String, Int] {
  override def transform(element: String): Int = element.toInt
}

object ListTest extends App {
  val list = new Cons(1, new Cons(2, new Cons("a", Empty)))
  val dbllist = new Cons(1, new Cons(2, new Cons("a", Empty)))
  val listOfInt = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val anotherlistOfInt = new Cons(4, new Cons(5, Empty))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.isEmpty)
  println(list.toString)
  println(listOfInt.map(new Function1[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }).toString)
  println(listOfInt.filter(new Function1[Int, Boolean] {
    override def apply(p: Int): Boolean = p % 2 == 0
  }).toString)
  println(listOfInt ++ anotherlistOfInt)
  println(listOfInt.flatMap(new Function1[Int, MyList[Int]] {
    override def apply(element: Int): MyList[Int] = new Cons(element, new Cons(element +1, Empty))
  }).toString)

  //case class
  println(list == dbllist)

  val concatinator = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

  println(concatinator("Hello ", "Scala"))

  val superAdder = new Function[Int, Int => Int] {
    override def apply(x: Int): Int => Int = new Function[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4)) //curried function

}