package exercises

class OverflowException extends Exception
class UnderflowException extends Exception
class MathCalculationException extends Exception("Dev by 0!")

class PocketCalculator {

  def add(x: Int, y: Int): Int =
    val result = x + y
    if ( x > 0 && + y > 0 && result < 0) throw new OverflowException
    else if ( x < 0 && + y < 0 && result > 0) throw new UnderflowException
    else result

  def subtract(x: Int, y: Int): Int = add(x, -y)

  def multiply(x: Int, y: Int) =
    val result = x * y
    if ( x > 0 && + y > 0 && result < 0) throw new OverflowException
    else if ( x < 0 && + y < 0 && result > 0) throw new OverflowException
    else if ( x > 0 && + y < 0 && result > 0) throw new OverflowException
    else if ( x < 0 && + y > 0 && result > 0) throw new OverflowException
    else result

  def devide(x: Int, y: Int) =
    if (y == 0) throw new MathCalculationException
    else x / y

}
