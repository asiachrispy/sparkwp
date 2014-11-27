package chap.graphx

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{PartitionStrategy, GraphLoader}

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/12
 */
object TriangleCount {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "StartContest", System.getenv("SPARK_HOME"))

    val graph = GraphLoader.edgeListFile(sc, "/test/wc/followers.txt", true).partitionBy(PartitionStrategy.RandomVertexCut)

    // 三角关系时，canonical必须为true
    val triCounts = graph.triangleCount().vertices

    val users = sc.textFile("/test/wc/users.txt").map { line =>
      val fields = line.split(",")
      (fields(0).toLong, fields(1))
    }
//    val triCountByUsername = users.join(triCounts).map {
//      case (id, (username, tc)) => (username, tc)
//    }
//    println(triCountByUsername.collect().mkString("\n"))
  }
}
