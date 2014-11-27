package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */
object Case extends App {
  val b = new Book("java")
  val bb = Book("scala")
  val tb = new TecBook("c plus")
  println(b.name)
  println(bb.name)
  // println(tb.name) 无法访问name

  b match {
    case Book("java") => println("case2 book name java")
    case Book(_) => println("case2 book name " + b.name)
//    case b1: Book => println("case book name " + b1.name)
    case _ => println("none")
  }

  bb match {
    case Book("java") => println("case2 book name java")
    case Book(_) => println("case2 book name " + b.name)
//    case b1: Book => println("case book name " + b1.name)
    case _ => println("none")
  }

  val num = 1
  val ret = num match {
    case 1 => "first"
    case 2 => "second"
    case _ => "none"
  }
  println(ret)

  val ret2 = num match {
    case i if i == 1 => "first"
    case number if number == 2 => "second"
    case _ => "none"
  }
  println(ret2)

  def Ty(t: Any) = t match {
    case p: Int => println("int " + p)
    case v: String => println("str " + v)
    case _ => println("none")
  }

  Ty(1)
  Ty("asia")
  Ty(null)

}

// 参数默认val，没有类体 {}，一般用于传递数据和消息，所以不变
// 另外默认实现了apply方法
// 和模式匹配 结合，功能无穷
case class Book(name: String)

// 没有指定参数type，则为private
class TecBook(name: String) {
}



