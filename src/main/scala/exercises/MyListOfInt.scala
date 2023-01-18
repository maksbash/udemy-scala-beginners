package exercises

abstract class MyListOfInt {

  /*
   head = first element of  the  list
   tail = remainder of the list
   isEmpty = is this list empty
   add(int) => new list with this element added
   toString => a string representation of the list
 */

  def head: Int
  def tail: MyListOfInt
  def isEmpty: Boolean
  def add(element: Int): MyListOfInt

  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

object EmptyInt extends MyListOfInt {
  override def head: Int = throw new NoSuchElementException
  override def tail: MyListOfInt = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def add(element: Int): MyListOfInt = new ConsInt(element, EmptyInt)
  override def printElements: String = ""
}

class ConsInt(h: Int, t: MyListOfInt) extends MyListOfInt {
  override def head: Int = h
  override def tail: MyListOfInt = t
  override def isEmpty: Boolean = false
  override def add(element: Int): MyListOfInt = new ConsInt(element, this)

  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
}

object ListTestInt extends App {
  val list = new ConsInt(1, new ConsInt(2, new ConsInt(3, EmptyInt)))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.isEmpty)
  println(list.toString)
}