package chap

import org.apache.spark._
import org.apache.spark.SparkContext._

// run local hdfs://name1:9000/test/wc hdfs://name1:9000/test/output1
// run spark://name1:7077 hdfs://name1:9000/test/wc hdfs://name1:9000/test/output2
object WordCountLocal {
  def main(args: Array[String]) {
    val sc = new SparkContext("spark://name1:7077", "WordCount", System.getenv("SPARK_HOME"))
//    val input = sc.textFile("/test/wc") // 读入文件内容
    val input = sc.textFile("file:///D:\\hdfs\\test\\wc") // 读入文件内容
    val words = input.flatMap(_.split(" ")) // 合并文件内容，然后按行输出，对行内容进行分词
    val counts = words.map((_, 1)).reduceByKey(_ + _) // map阶段单词统计，reduce阶段单词汇总
    counts.foreach(println)
//    counts.saveAsTextFile("/test/output4") // for hdfs
//    counts.saveAsTextFile("file:///D:\\hdfs\\test\\out") // for local

//    counts.map{case(key,value) => (value,key)}.sortByKey(true,1).take(10).map(x => (x._2,x._1))
  }
}
// 最简单的业务代码可以使用一行完成
// sc.textFile(args(1)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).saveAsTextFile(args(2))
// sc.textFile(args(1)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
// .map((_._1,_._2)).sortByKey().saveAsTextFile(args(2))