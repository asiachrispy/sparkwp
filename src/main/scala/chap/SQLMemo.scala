package chap

import org.apache.spark._
import org.apache.spark.sql.SQLContext

//bin/spark-submit --jars ilib/*.jar --class chap.HiveQL
object SQLMemo {

  case class Person(name: String, age: Int)

  def main(args: Array[String]) {//spark://name1:7077
    val sc = new SparkContext("local", "HiveQL", System.getenv("SPARK_HOME"))
    val sqlContext = new SQLContext(sc)
    import sqlContext.createSchemaRDD

    val input = sc.textFile("hdfs://name1:9000/test/people/people.txt") // 读入文件内容
    input.map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt)).registerAsTable("people")
    val teenagers = sqlContext.sql("SELECT name FROM people WHERE age >= 10 AND age <= 20")
    teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
  }
}
