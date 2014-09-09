/**
 * Illustrates loading data over JDBC
 */
package chap

import org.apache.spark._
import org.apache.spark.rdd.JdbcRDD
import java.sql.{DriverManager, ResultSet}

//bin/spark-submit --jars ilib/*.jar --class chap.LoadSimpleJdbc
object LoadSimpleJdbc {
  def main(args: Array[String]) {//spark://name1:7077
    val sc = new SparkContext("local", "LoadSimpleJdbc", System.getenv("SPARK_HOME"))
    val data = new JdbcRDD(sc,
      createConnection, "SELECT * FROM rs_job2job_txt WHERE ? <= id AND ID <= ?",
      lowerBound = 1, upperBound = 10, numPartitions = 2, mapRow = extractValues)
    println(data.collect().toList)
  }

  def createConnection() = {
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    DriverManager.getConnection("jdbc:mysql://10.10.10.64:21008/recommend?characterEncoding=utf-8&autoReconnect=true&maxReconnects=1&initialTimeout=1&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&connectTimeout=50000&socketTimeout=50000","root","@WSX#EDC6yhn")
  }

  def extractValues(r: ResultSet) = {
    (r.getInt(1), r.getString(2))
  }
}
