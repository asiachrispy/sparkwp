package course

import scala.reflect.ClassTag

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/14
 */
object Fanxin extends App {

  val c = new Cheet("asia", 100)
  println(c.name + "-" + c.content)

  val cc = new Cheet[Int, Int](1, 100)
  println(cc.name + "-" + cc.content)

  val ccc = new Cheet[String, Any]("chris", new Address("beijing"))
  val ad = ccc.content.asInstanceOf[Address]
  println(ad.name)


  // key 和value 都是Nothing类型
  val mp = Map()
  //类型判断 key是any类型，value是String类型
  val m = Map("asia" -> "beijing", 110 -> "nanchang")
  println(m)

  //限定类型
  val mm = Map[String, Int]("beijing" -> 110, "nanc" -> 794)
  println(mm)

  val ck = new Cook("asia", "beijing")
  val ckk = new Cook[String]("asia", "beijing")
  println(ck.name)


  // 范型函数
  def met[T](param: T) {
    println(param)
  }

  met[String]("china")
  // 类型推导
  met("china")


}

class Address(val name: String)

class Cheet[T: ClassTag, C](val name: T, val content: C)

class Cook[@specialized +T](val name: String, value: T)

//// 范型类型必须是Comparable的子类
//class Compra[T <: Comparable](val num1: T, val num2: T) {
//  def comp = if (num1.compareTo(num2) < 0) num1 else num2
//
//  // F必须是t的父类
//  def rep[F >: T](f: F) = new Compra(f, num2)
//}