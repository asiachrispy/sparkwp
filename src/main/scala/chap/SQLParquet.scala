package chap

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/4
 */
object SQLParquet {
  case class Person(name: String, age: Int)

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "LoadHive", System.getenv("SPARK_HOME"))
    val people = sc.textFile("hdfs://name1:9000/test/people/people.txt").map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt))
    val sqlContext = new SQLContext(sc)
    import sqlContext._
    //people.saveAsParquetFile("people.parquet")
    val parquetFile = sqlContext.parquetFile("people.parquet")

    parquetFile.registerAsTable("parquetFile")
    val teenagers = sql("SELECT name FROM parquetFile WHERE age >= 10 AND age <= 20")
    teenagers.collect().foreach(println)

  }
}
