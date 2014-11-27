package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */
class Override {

}

class Desk(val name: String) {
  val id = 1

  def where(where: String) {
    println(name + "-" + where)
  }
}

class HomeDesk(name: String) extends Desk(name) {
  // 属性字段的重载
  override val id = 11

  // 方法的重载
  override def where(where: String) {
    print("homeDesk " + where)
  }
}

object HomeDesk {
  def apply(name: String) = {
    new HomeDesk(name)
  }
}

object Override extends App {

  val hd = HomeDesk("chris's ")
  hd.where("home")
}