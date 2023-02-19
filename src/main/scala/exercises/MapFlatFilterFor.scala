package exercises

object MapFlatFilterFor extends App {
  val numbers = List(1,2,3,4)
  val chars = List('a', 'b', 'c', 'd')

  val toPair = (x: Int, c: List[Char]) => c.map(_ + x.toString)
  val toCharPair = (x: Int, c: Char) => x + c.toString
  println(numbers.flatMap(toPair(_, chars)))
  println(numbers.flatMap(x => chars.map(_.toString + x)))

  val colours = List("black", "white")
  println(numbers.flatMap(x => chars.flatMap(c => colours.map(col => "" + c + x + "-" + col))))

  //for comprehensions
  val forCombinations = for {
    x <- numbers if x % 2 == 0 // can add filter!
    c <- chars
    col <- colours
  } yield ("" + c + x + "-" + col)

  println(forCombinations)

  for { // foreach
    n <- numbers
  } println(n)
}
