//package akka
//
//import scala.actors.Actor
//import scala.actors.Actor._
//
///**
// * Description : some words
// * Author: chris
// * Date: 2014/9/12
// */
//class Hi extends Actor {
//  def act() {
//    while (true) {
//      receive {
//        case "Hi" => print("hello")
//        case msg => System.out.println(msg)
//      }
//    }
//  }
//
//}
//
//object Hi {
//  def main(args: Array[String]) = {
//    val hi = Actor.actor {
//      receive {
//        case msg => System.out.println(msg)
//      }
//    }
//    hi ! "这是向badActor发送的消息, 能看见?"
//
//    val h = new Hi
//    h.start()
//    h ! "hi chris"
//  }
//}
