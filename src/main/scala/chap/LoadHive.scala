/**
* Illustrates loading Hive data using Spark SQL
*/
package chap

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext


object LoadHive {
  def main(args: Array[String]) {//spark://name1:7077
    val sc = new SparkContext(args(0), "LoadHive", System.getenv("SPARK_HOME"))
    val hiveCtx = new HiveContext(sc)
    val peoples = hiveCtx.hql("FROM person SELECT name, age order by age")
    peoples.map(t => "Name:" + t(0) + " Age:" + t(1)).collect().foreach(println)
  }
}
