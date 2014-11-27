package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */
class AbsOop {

}

abstract class Shoot {
  // 抽象属性
  val size: Int

  // 不需要abstract，只要没有函数实现体就是抽象方法
  def walk(way: String): Int

  def logo() = {
    println("lining")
  }
}

class SportShoot extends Shoot {
  val size = 10

  def walk(way: String): Int = {
    way match {
      case "china" => 100
      case "america" => 1000
      case _ => 0
    }
  }
}

object SportShoot {
  def apply() = {
    new SportShoot
  }
}

object AbsOop extends App {

  val sp = SportShoot()
  println(sp.size + "--" + sp.walk("china"))
}