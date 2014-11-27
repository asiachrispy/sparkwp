package chap.graphx

import org.apache.spark._
import org.apache.spark.graphx._

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/12
 */
object Pagerank {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "StartContest", System.getenv("SPARK_HOME"))
    val graph = GraphLoader.edgeListFile(sc, "/test/wc/followers.txt")
    // (uid,rank)
    val ranks = graph.pageRank(0.0001).vertices

    // 生成(uid,username)
    val users = sc.textFile("/test/wc/users.txt").map { line =>
      val fields = line.split(",")
      (fields(0).toLong, fields(1))
    }
    // 合并属性
//    val ranksByUsername = users.join(ranks).map {
//      case (id, (username, rank)) => (username, rank)
//    }
//    // 打印结果
//    println(ranksByUsername.collect().mkString("\n"))
  }

}
