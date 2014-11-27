//package actor
//
//import akka.actor.Actor
//
////import scala.actors._
//
///**
// * Description : some words
// * Author: chris
// * Date: 2014/9/15
// */
//class ActorOps {
//}
//
//object HelloActor extends Actor {
//  def act() {
//    for (i <- 1 to 5) {
//      println("this is a scala actor.")
//      Thread.sleep(2000)
//    }
//  }
//}
//
//object CusActor extends Actor {
//  def act() {
//    while (true) {
//      receive {
//        case msg: String => println("receive msg " + msg)
//        case _ => println("the end"); exit()
//      }
//    }
//  }
//}
//
//object ActorOps {
//
//  def main(args: Array[String]) {
//    // 启动actor
//    //    HelloActor.start
//
//    // 启动actor
//    CusActor.start
//    // 将消息发给CusActor，CusActor的act函数接收消息
//    CusActor ! "this is chairs"
//    // 程序退出
//    CusActor ! 1
//    // 无法处理了
//    CusActor ! "again"
//
//    // 发送消息给自己并处理
//    self ! "scala"
//    self.receive { case msg: String => println(msg)}
//
//    // 等待时间
//    self ! "scala2"
//    self.receiveWithin(1000) { case msg: String => println(msg)}
//
//    // 其实就是定义了act函数体，并赋给我们定义的send函数
//    //    def send = Actor.actor {
//    //      for (i <- 1 to 5) {
//    //        println("this is a scala actor.")
//    //        Thread.sleep(2000)
//    //      }
//    //    }
//  }
//}
