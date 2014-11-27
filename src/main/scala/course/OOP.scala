package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */

class OOP {

}

class Person {
  var name: String = _
  var age = 27

  private[this] var gender = "male"

  def setName(name: String) {
    this.name = name
  }

  // 指定方法调用范围为 this表示仅当前类内可以调用
  private[this] def setAge(age: Int) {
    this.age = age
  }

  // 指定方法调用范围为 course包内可以调用
  private[course] def setGender(gender: String) {
    this.gender = gender
  }

  // 私有方法，默认仅当前类内可使用
  private def getGender() {
    gender
  }

  override def toString = (name + "-" + age)
}

class Stu extends Person {
  val claz: Int = 0

  def show() {

  }
}

// 主构造器,带有val表示public；,没有val，则为private 只能在类内部使用
class Worker(val name: String, val age: Int, gender: String) {
  println("main construct.")

  var comp: String = _

  def this(name: String, age: Int, gender: String, comp: String) {
    this(name, age, gender) //必须调用主构造方法
    this.comp = comp
  }
}

object OOP {

  def main(args: Array[String]) {
    val p = new Person
    p.name = "chris"

    println(p)
    p.setName("asia")
    println(p)
    p.setGender("female")

    val w = new Worker("cici", 29, "female")
    println(w.name)
    //    print(w.gender )

    val ww = new Worker("chris", 20, "male", "liepin")
    println(ww.comp)
  }
}
