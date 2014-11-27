package course

import scala.io.Source

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/13
 */
object FileOps {

  def main(args: Array[String]) {
    val f = Source.fromFile("D:\\gitspace\\sparkwp\\data\\sql\\demo1-person.txt")
    for (line <- f.getLines()) {
      println(line)
    }

    val ur = Source.fromURL("http://spark.apache.org/docs/latest/sql-programming-guide.html")
    for (line <- f.getLines()) {
      println(line)
    }

  }
}
