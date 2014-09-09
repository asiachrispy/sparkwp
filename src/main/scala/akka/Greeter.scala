package akka

import akka.actor.Actor

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/8/28
 */
object Greeter {
  case object Greet
  case object Done
}

class Greeter extends Actor {
  def receive = {
    case Greeter.Greet =>
      println("Hello World!")
      sender ! Greeter.Done
  }
}