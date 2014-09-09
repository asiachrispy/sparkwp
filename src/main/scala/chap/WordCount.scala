package chap

import org.apache.spark._
import org.apache.spark.SparkContext._

// run local hdfs://name1:9000/test/wc hdfs://name1:9000/test/output1
// run spark://name1:7077 hdfs://name1:9000/test/wc hdfs://name1:9000/test/output2
object WordCount {
  def main(args: Array[String]) {
    if (args.length != 3) {
      print("usage: MASTER=[local | spark://ip:port] input output")
      System.exit(1)
    }
    val master = args(0)
    val sc = new SparkContext(master, "WordCount", System.getenv("SPARK_HOME"))
    val input = sc.textFile(args(1)) // 读入文件内容
    val words = input.flatMap(_.split(" ")) // 合并文件内容，然后按行输出，对行内容进行分词
    val counts = words.map((_, 1)).reduceByKey(_ + _) // map阶段单词统计，reduce阶段单词汇总
    counts.saveAsTextFile(args(2))
  }
}
// 最简单的业务代码可以使用一行完成
// sc.textFile(args(1)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).saveAsTextFile(args(2))