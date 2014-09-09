package chap

/**
 * Description : some words
 * Author: chris
 * Date: 2014/8/21
 */
object HelloWorld {
  def main(args: Array[String]) {
    if (args.length != 1) {
      System.exit(1)
    }

    // 注意以下2行代码的区别
    println("Hello " + args(0))
    // 这就是Lamdba表达式
    println ("Hello " + args(0))

    hello(args(0))

    args(0).foreach(chars(_))
  }

  def hello(name: String) = {
    println("method: hello " + name)
  }

  def chars(c: Char) = {
    println (c)
  }
}