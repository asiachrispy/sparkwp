package play

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/16
 */

import scala.annotation.tailrec

/**
 * Different ways to calculate the product of a List[Int] recursion.
 */
object Product extends App {

  val list = List(1, 2, 3, 4)
  println(product(List(1, 2, 3, 4)))
  println(product2(List(1, 2, 3, 4)))

  // (1) basic recursion; yields a "java.lang.StackOverflowError" with large lists
  def product(ints: List[Int]): Int = ints match {
    case Nil => 1
    case x :: tail => x * product(tail)
  }

  //  // (2) tail-recursive solution
  def product2(ints: List[Int]): Int = {
    @tailrec
    def productAccumulator(ints: List[Int], accum: Int): Int = {
      ints match {
        case Nil => accum
        case x :: tail => productAccumulator(tail, accum * x)
      }
    }
    productAccumulator(ints, 1)
  }
}