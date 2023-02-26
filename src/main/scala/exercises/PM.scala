package exercises

object PM extends App {
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def t(e: Expr): String = e match
    case Sum(ex1, ex2) => s"${t(ex1)} + ${t(ex2)}"
    case Prod(e1, e2) => {
      def show(e: Expr) = e match
        case Prod(_, _) => t(e)
        case Number(_) => t(e)
        case _ => "(" + t(e) + ")"
        show(e1) + " * " + show(e2)
    }
//    case Prod(ex1, ex2) if ex1.isInstanceOf[Sum] => s"(${t(ex1)}) * ${t(ex2)}"
//    case Prod(ex1, ex2) if ex2.isInstanceOf[Sum] => s"${t(ex1)} * (${t(ex2)})"
//    case Prod(ex1, ex2) if ex1.isInstanceOf[Sum] && ex2.isInstanceOf[Sum] => s"(${t(ex1)}) * (${t(ex2)})"
//    case Prod(ex1, ex2) => s"${t(ex1)} * ${t(ex2)}"
    case Number(n) => s"$n"

  println(t(Prod(Sum(Number(3), Number(2)), Number(4))))
}
