//package course
//
///**
// * Description : some words
// * Author: chris
// * Date: 2014/9/13
// */
//class UseTrait {
//
//}
//
//
////---------------------
//trait TraitLogger {
//  //抽象方法
//  def log(msg: String)
//
//  def log(x: String, y: String) {
//    print(x + y)
//  }
//}
//
//// 子trait 实现父 trait的抽象方法
//trait ConsLog extends TraitLogger {
//  def log(msg: String) {
//    log("it's chris" + msg)
//  }
//
//  override def log(x: String, y: String) {
//    print(x + y)
//  }
//}
//
//class TLog extends ConsLog {
//  def test {
//    log("Tlog's log method.")
//  }
//}
//
////---------------------------------
//
//trait Logger {
//  def log(msg: String) {
//    println(msg)
//  }
//}
//
//// 调用trait的方法
//class ComLog extends Logger {
//  def concrete {
//    log("it's chris")
//  }
//}
//
//object UseTrait extends App {
//
//  val cg = new ComLog
//  cg.concrete
//}
