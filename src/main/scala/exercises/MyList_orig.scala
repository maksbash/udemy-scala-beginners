//package exercises
//
//abstract class MyList_orig[+A] {
//
//  /*
//     head = first element of  the  list
//     tail = remainder of the list
//     isEmpty = is this list empty
//     add(int) => new list with this element added
//     toString => a string representation of the list
//   */
//
//  def head: A
//  def tail: MyList_orig[A]
//  def isEmpty: Boolean
//  def add[B >: A](element: B): MyList_orig[B]
//  def printElements: String
//  // polymorphic call
//  override def toString: String = "[" + printElements + "]"
//
//  // higher-order functions
//  def map[B](transformer: A => B): MyList_orig[B]
//  def flatMap[B](transformer: A => MyList_orig[B]): MyList_orig[B]
//  def filter(predicate: A => Boolean): MyList_orig[A]
//
//  // concatenation
//  def ++[B >: A](list: MyList_orig[B]): MyList_orig[B]
//
//  // hofs
//  def foreach(f: A => Unit): Unit
//  def sort(compare: (A, A) => Int): MyList_orig[A]
//  def zipWith[B, C](list: MyList_orig[B], zip:(A, B) => C): MyList_orig[C]
//  def fold[B](start: B)(operator: (B, A) => B): B
//
//  def reverse: MyList_orig[A] = {
//    def reverseTailrec(input: MyList_orig[A], result: MyList_orig[A]): MyList_orig[A] =
//      if (input.isEmpty) result
//      else reverseTailrec(input.tail, Cons(input.head, result))
//
//    reverseTailrec(this, Empty)
//  }
//}
//
//case object Empty extends MyList_orig[Nothing] {
//  def head: Nothing = throw new NoSuchElementException
//  def tail: MyList_orig[Nothing] = throw new NoSuchElementException
//  def isEmpty: Boolean = true
//  def add[B >: Nothing](element: B): MyList_orig[B] = new Cons(element, Empty)
//  def printElements: String = ""
//
//  def map[B](transformer: Nothing => B): MyList_orig[B] = Empty
//  def flatMap[B](transformer: Nothing => MyList_orig[B]): MyList_orig[B] = Empty
//  def filter(predicate: Nothing => Boolean): MyList_orig[Nothing] = Empty
//
//  def ++[B >: Nothing](list: MyList_orig[B]): MyList_orig[B] = list
//
//  // hofs
//  def foreach(f: Nothing => Unit): Unit = ()
//  def sort(compare: (Nothing, Nothing) => Int) = Empty
//  def zipWith[B, C](list: MyList_orig[B], zip: (Nothing, B) => C): MyList_orig[C] =
//    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
//    else Empty
//  def fold[B](start: B)(operator: (B, Nothing) => B): B = start
//
//}
//
//case class Cons[+A](h: A, t: MyList_orig[A]) extends MyList_orig[A] {
//  def head: A = h
//  def tail: MyList_orig[A] = t
//  def isEmpty: Boolean = false
//  def add[B >: A](element: B): MyList_orig[B] = new Cons(element, this)
//  def printElements: String =
//    if(t.isEmpty) "" + h
//    else s"$h ${t.printElements}" 
//
//  /*
//    [1,2,3].filter(n % 2 == 0) =
//      [2,3].filter(n % 2 == 0) =
//      = new Cons(2, [3].filter(n % 2 == 0))
//      = new Cons(2, Empty.filter(n % 2 == 0))
//      = new Cons(2, Empty)
//   */
//  def filter(predicate: A => Boolean): MyList_orig[A] =
//    if (predicate(h)) new Cons(h, t.filter(predicate))
//    else t.filter(predicate)
//
//  /*
//    [1,2,3].map(n * 2)
//      = new Cons(2, [2,3].map(n * 2))
//      = new Cons(2, new Cons(4, [3].map(n * 2)))
//      = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
//      = new Cons(2, new Cons(4, new Cons(6, Empty))))
//   */
//  def map[B](transformer: A => B): MyList_orig[B] =
//    new Cons(transformer(h), t.map(transformer))
//
//  /*
//    [1,2] ++ [3,4,5]
//    = new Cons(1, [2] ++ [3,4,5])
//    = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
//    = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
//   */
//  def ++[B >: A](list: MyList_orig[B]): MyList_orig[B] = new Cons(h, t ++ list)
//  /*
//    [1,2].flatMap(n => [n, n+1])
//    = [1,2] ++ [2].flatMap(n => [n, n+1])
//    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
//    = [1,2] ++ [2,3] ++ Empty
//    = [1,2,2,3]
//   */
//  def flatMap[B](transformer: A => MyList_orig[B]): MyList_orig[B] =
//    transformer(h) ++ t.flatMap(transformer)
//
//  // hofs
//  def foreach(f: A => Unit): Unit = {
//    f(h)
//    t.foreach(f)
//  }
//
//  def sort(compare: (A, A) => Int): MyList_orig[A] = {
//    def insert(x: A, sortedList: MyList_orig[A]): MyList_orig[A] =
//      if (sortedList.isEmpty) new Cons(x, Empty)
//      else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
//      else new Cons(sortedList.head, insert(x, sortedList.tail))
//
//    val sortedTail = t.sort(compare)
//    insert(h, sortedTail)
//  }
//
//  def zipWith[B, C](list: MyList_orig[B], zip: (A, B) => C): MyList_orig[C] =
//    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
//    else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))
//
//  /*
//    [1,2,3].fold(0)(+) =
//    = [2,3].fold(1)(+) =
//    = [3].fold(3)(+) =
//    = [].fold(6)(+)
//    = 6
//   */
//  def fold[B](start: B)(operator: (B, A) => B): B =
//    t.fold(operator(start, h))(operator)
//
//}
//
//object ListTest extends App {
//  val listOfIntegers: MyList_orig[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
//  val cloneListOfIntegers: MyList_orig[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
//  val anotherListOfIntegers: MyList_orig[Int] = new Cons(4, new Cons(5, Empty))
//  val listOfStrings: MyList_orig[String] = new Cons("Hello", new Cons("Scala", Empty))
//
//  println(listOfIntegers.toString)
//  println(listOfStrings.toString)
//
//  println(listOfIntegers.map(_ * 2).toString)
//
//  println(listOfIntegers.filter(_ % 2 == 0).toString)
//
//  println((listOfIntegers ++ anotherListOfIntegers).toString)
//  println(listOfIntegers.flatMap(elem => new Cons(elem, new Cons(elem + 1, Empty))).toString)
//
//  println(cloneListOfIntegers == listOfIntegers)
//
//  listOfIntegers.foreach(println)
//  println(listOfIntegers.sort((x, y) => y - x))
//  println(anotherListOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))
//  println(listOfIntegers.fold(0)(_ + _))
//
//  // for comprehensions
//  val combinations = for {
//    n <- listOfIntegers
//    string <- listOfStrings
//  } yield n + "-" + string
//  println(combinations)
//
//  def sort(list: MyList_orig[Int]): MyList_orig[Int] = {
//    def insertSort(sortedList: MyList_orig[Int], elem: Int, lessThanElement: MyList_orig[Int] = Empty): MyList_orig[Int] =
//      if (sortedList.isEmpty || elem < sortedList.head) lessThanElement.reverse ++ Cons(elem, sortedList)
//      else insertSort(sortedList.tail, elem, Cons(sortedList.head, lessThanElement))
//
//    list.fold[MyList_orig[Int]](Empty)((sorted, elem) => insertSort(sorted, elem))
//  }
//
//  println(sort(Cons(4, Cons(2, Cons(5, Cons(1, Cons(3, Empty)))))))
//}
