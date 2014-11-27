//package actor
//
//import java.net.{InetAddress, UnknownHostException}
//
//import scala.actors.Actor
//import scala.actors.Actor._
//
///**
// * Description : some words
// * Author: chris
// * Date: 2014/9/15
// */
//class ActorSR {
//
//}
//
//object NameRes extends Actor {
//  def act() {
//    react {
//      case (name: String, acr: Actor) => acr ! getIp(name)
//      case "EXIT" => println("exit"); exit()
//      case msg => println("unhandler msg " + msg)
//        act
//    }
//  }
//
//  def getIp(name: String): Option[InetAddress] = {
//    try {
//      Some(InetAddress.getByName(name))
//    } catch {
//      case _: UnknownHostException => None
//    }
//  }
//}
//
//object NameResMul extends Actor {
//  def act() {
//    react {
//      //线程共享
//      case (name: String, acr: Actor) => acr ! getIp(name)
//      case (name: String) => NameRes ! getIp(name)
//      case Net(name, acr) => acr ! getIp(name)
//      case "EXIT" => println("exit"); exit()
//      case msg => println("unhandler msg " + msg)
//        act
//    }
//  }
//
//  def getIp(name: String): Option[InetAddress] = {
//    try {
//      Some(InetAddress.getByName(name))
//    } catch {
//      case _: UnknownHostException => None
//    }
//  }
//}
//
//case class Net(name: String, acr: Actor)
//
//object ActorSR extends App {
//  NameRes.start()
//  NameRes !("www.liepin.com", self)
//  //import scala.actors.Actor._
//  val x = self.receive { case x => x}
//  println(x) //Some(www.liepin.com/211.151.18.66)
//
//  // 注意 消息发给了NameResMul，NameResMul接受消息后有转发给了NameRes，NameRes接受
//  NameResMul.start()
//  NameResMul ! ("www.baidu.com") //unhandler msg (www.baidu.com,null)
//  NameResMul !("www.dajie.com", NameRes) //
//  NameResMul ! Net("www.liepin.com", NameRes) //
//}
