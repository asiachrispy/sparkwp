package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/11/13
 */
class ApplyDemo {
  def apply() = "aa"

  def test = {
    println("test")
  }
}

// 伴生对象
object ApplyDemo {
  def stat {
    println("static method")
  }

  def apply() = {
    new ApplyDemo
  }

  var count = 0

  def inc {
    count += 1
  }
}

object Main extends App {
  // 访问静态方法
  ApplyDemo.stat//static method

  // 类名后面加括号，相当于调用伴生对象的apply方法
  val a = ApplyDemo()
  a.test //test

  //对象加括号相当于调用对象的apply方法
  println(a())//aa
  val b = ApplyDemo.apply()
  b.test //test
  println(a.apply())//aa

  for (i <- 0 until 10) {
    ApplyDemo.inc
  }

  println(ApplyDemo.count)
}
