package chap.graphx

import org.apache.spark._
import org.apache.spark.graphx._

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/12
 */
object ConComponent {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "StartContest", System.getenv("SPARK_HOME"))

    val graph = GraphLoader.edgeListFile(sc, "/test/wc/followers.txt")
    val cc = graph.connectedComponents().vertices

    val users = sc.textFile("/test/wc/users.txt").map { line =>
      val fields = line.split(",")
      (fields(0).toLong, fields(1))
    }

//    val ccByUsername = users.join(cc).map {
//      case (id, (username, cc)) => (username, cc)
//    }
//    // 打印结果
//    println(ccByUsername.collect().mkString("\n"))
  }
}
