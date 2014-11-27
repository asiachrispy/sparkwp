package course

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/14
 */
object Impli extends App {
  val a = new A
  // a则可以调用RichA的方法
//  a.rich()

  // 隐式值
  implicit val valname: String = "java"
  implicit val valname2 = "java2"

  def param(implicit name: String) {
    println("implict " + name)
  }

//  param // 会把valname传递给函数
//  param("scala") 自己的参数

  def params(age: Int)(implicit name: String) {
    println(age + " -- " + name)
  }

  //  params(10)
  //  params(20)("c")


  def smaller[T](a: T, b: T)(implicit sort: T => Ordered[T]): T = {
    if (a < b) a else b
  }

  println(smaller(1, 2))
  println(smaller("b", "a"))

  // 隐式类，隐式类的参数x类型的变量会被隐式的被创建为隐式类，然后可以调用内部的方法
  implicit class cal(x: Int) {
    def add(a: Int) = a + x
  }

  println(100.add(1))

  implicit class calcu(name: String) {
    def append(value: String): String = name + value
  }

  println("i love ".append("you"))
}

class A {
}

object Conver {
  // 将A隐式转换为RichA
  implicit def a2RichA(a: A) = new RichA(a)
}

//效果应该很明显。字符串在调用 sayHello 方法时，发现自己没有这个方法，就试图用某种方式转型成具有 sayHello 方法的类型，这里它找到了 implicit class 这个隐式类可以用来转型成 Person 类，最后调用了生成的 Person 实例的  sayHello 方法。
// 那什么是主构造函数呢，就是跟在 class 关键字后面那部分，因为 Scala 比 Java 更省事，声明类的时候把主构造函数也一同声明了。
// implicit class 类名后的参数列表必定是一个参数的，否则会报错：
//error: implicit classes must accept exactly one primary constructor parameter  因为这种类型的类非常大的一个用途就是用作隐式转型 。
// A作为RichA的参数
class RichA(val a: A) {
  def rich {
    println("a to RichA")
  }
}


