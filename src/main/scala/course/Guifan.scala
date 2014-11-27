package course

import java.util

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/25
 */
object Guifan extends App {

  val l = List("zero", "one", "two", "three")
  /*
  0--zero
  1--one
  2--two
  3--three
   */
  // 默认下标从0开始
  for ((number, index) <- l.zipWithIndex) yield println(index + "--" + number)

  l.zipWithIndex.map(x => println(x._2 + "--" + x._1))

  /*
  1--zero
  2--one
  3--two
  4--three
   */
  // 下标从1开始
  l.zip(Stream from 1).map(x => println(x._2 + "--" + x._1))

  // no leading spaces on 2nd and 3rd lines
  val foo1 = """Line 1.
Line 2.
Line 3."""
  println(foo1)
  /*
  Line 1.
  Line 2.
  Line 3.
   */
  // has leading spaces on 2nd and 3rd lines
  val foo = """Line 1.
                Line 2.
                Line 3."""
  println(foo)
  /*
  Line 1.
                Line 2.
                Line 3.
   */


  import scala.collection.mutable

  var fruits = mutable.ArrayBuffer[String]()
  fruits += "Apple"
  fruits += "Banana"
  fruits += "Orange"

  val fruitss = new Array[String](3)
  fruitss(0) = "Apple"
  fruitss(1) = "Banana"
  fruitss(2) = "Orange"

  //  val str = "asia is chris-python, a good man."

  val str = "oauth_token=FOO&passwd=BAR&expires=3600"
  str split "&" foreach println
  val vs = str split "&"

}
