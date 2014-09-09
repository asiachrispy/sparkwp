package akka

import akka.actor.{Props, Actor}

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/8/28
 */
class HelloWorld extends Actor {

  override def preStart(): Unit = {
    val greeter = context.actorOf(Props[Greeter], "greeter")
    greeter ! Greeter.Greet
  }

  def receive = {
    case Greeter.Done => context.stop(self)
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[HelloWorld].getName))
  }
}