package chap.graphx

import org.apache.spark.SparkContext
import org.apache.spark.graphx.{EdgeDirection, Graph, GraphLoader, VertexId}

/**
* Description : some words
* Author: chris
* Date: 2014/9/12
*/
object Exp {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "StartContest", System.getenv("SPARK_HOME"))
    // step1: 加载测试数据文件，4个参数(SparkContext, 文件路径，是否有方向，分区数)
    val graph = GraphLoader.edgeListFile(sc, "/test/wc/web-Google.txt", true, 4)

    // step2:
    println(graph.edges.count())
    println(graph.vertices.count())
    graph.inDegrees.take(10).foreach(println)
    graph.outDegrees.take(10).foreach(println)

    // step3:
    val tmpVer = graph.mapVertices((id, attr) => attr.toInt * 2)
    tmpVer.vertices.take(10).foreach(println)

    // 指定了顶点和边的属性为int,attr不需要to.Int转换
    val tmpVer2: Graph[Int, Int] = graph.mapVertices((_, attr) => attr * 4)
    tmpVer2.vertices.take(10).foreach(println)

    // ste4:
    graph.edges.take(10).foreach(println)
    val tmpEdg: Graph[Int, Int] = graph.mapEdges(e => e.attr.toInt * 5)
    tmpEdg.edges.take(10).foreach(println)

    // step5:
    println(graph.inDegrees.reduce(max).toString()) //(885605,4686)
    println(graph.outDegrees.reduce(max).toString()) //(32163,4945)
    println(graph.degrees.reduce(max).toString) //(537039,6353)顶点537039具有最多的degree为6353

    // step6:对边和顶点过滤的操作，获取子图
    // subgraph函数说明：
    // 第一个参数是 一个带有EdgeTriple参数的函数，且函数返回boolean;
    // 第二个参数是 一个带有元组（顶点id,顶点属性）的函数，且函数返回boolean;
    val subgh = graph.subgraph(epred = e => e.srcId > e.dstId)
    subgh.vertices.count()
    subgh.edges.count()

    val subgh2 = graph.subgraph(epred = e => e.srcId > e.dstId, vpred = (id, _) => id > 5000)
    subgh2.vertices.count()
    subgh2.edges.count()

    // step7: 随机生成一张图
//    val gh: Graph[Double, Int] = GraphGenerators.logNormalGraph(sc, numVertices = 100).mapVertices((id, _) => id.toDouble)
//    gh.edges.take(10).mkString("\n").foreach(println)


    // step8: 最短路径
    val srcId: VertexId = 0
    val g = graph.mapVertices((id, _) => if (id == srcId) 0.0 else Double.PositiveInfinity)

//    val sssp = g.pregel(Double.PositiveInfinity)(
      // 第一个参数
//      (id, dist, newdist) => math.min(dist, newdist),
      // 第二个参数
//      triplet => {
//        if (triplet.srcAttr + triplet.attr < triplet.dstAttr) {
//          Iterator(triplet.dstAttr, triplet.srcAttr + triplet.attr)
//        } else {
//          Iterator.empty
//        }
//      },
      // 第三个参数
//      (a, b) => math.min(a, b)
//    )
//    sssp.vertices.take(10).foreach(println)

    /*
     (185012,12.0) 顶点0,经过12步到达顶点185012
     (612052,PositiveInfinity)，顶点0不可达顶点612052

     */

    // step9:
    graph.collectNeighbors(EdgeDirection.In)
    //.take(10).foreach(println())

    //    val raw = graph.mapVertices((id, att) => (id, 0))
    //    val rout = raw.outDegrees
    //    raw.joinVertices[Int](rout)((_, _, opt) => opt).vertices.take(10).foreach(println())

    graph.pageRank(0.1).vertices.count()
  }

  def max(a: (VertexId, Int), b: (VertexId, Int)): (VertexId, Int) = {
    if (a._2 > b._2) a else b
  }
}
