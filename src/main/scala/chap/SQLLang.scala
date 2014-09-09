package chap

import org.apache.spark._

//bin/spark-submit --jars ilib/*.jar --class chap.SQLLang
object SQLLang {

  case class Person(name: String, age: Int)

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "SQLLang", System.getenv("SPARK_HOME"))
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext._

    val input = sc.textFile("hdfs://name1:9000/test/people/people.txt") // 读入文件内容
    val people = input.map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt))
    val teenagers = people.where('age >= 10).where('age <= 19).select('name)
    teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
  }
}
