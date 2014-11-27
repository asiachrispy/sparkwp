package course

import scala.io.Source

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */
object Funs {

  def fileops(path: String) {
    val f = Source.fromFile(path)

    for (line <- f.getLines()) {
      proLine(line)
    }

    def proLine(line: String) {
      println(line)
    }
  }

  def main(args: Array[String]) {
    def addTen(x: Int) = x + 10
    // 将函数付给一个变量
    val add = addTen _
    // 匿名函数
    val add2 = (x: Int) => x + 100
    println(add(1))
    println(add2(11))

    def addEv(x: Int, y: Int) = x + y
    //    val ev = addEv(x, y)
    val ev = addEv(_, _)
    println(ev(1, 2))
    val evv = addEv _
    println("evv:" + evv(1, 2))

    // 闭包，函数并没有y参数，y来做函数外
    val y = 10
    def sum(x: Int) = x + y

    // 柯里化函数
    def ss(x: Int)(y: Int) = x * y
    println(ss(1) _) //<function1>
    println(ss(1)(3))

    //
    val wd = Array(2, 3, 4)
    wd.map(_ + 1) // 各项+1
    wd.reduce(_ + _) // 求和

  }
}
