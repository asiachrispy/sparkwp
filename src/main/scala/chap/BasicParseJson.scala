/**
 * Illustrates a simple map partition to parse JSON data in Scala
 * Loads the data into a case class with the name and a boolean flag
 * if the person loves pandas.
 */
package chap

object BasicParseJson {

//  case class Person(name: String, age: Int)
//
//  implicit val personReads = Json.format[Person]
//
//  def main(args: Array[String]) {
//    val sc = new SparkContext("local", "BasicParseJson", System.getenv("SPARK_HOME"))
//    val input = sc.textFile("file:///D:\\hdfs\\test\\json")
//    val parsed = input.map(Json.parse(_))
//    val result = parsed.flatMap(record => personReads.reads(record).asOpt)
//    result.filter(_.age > 11).map(Json.toJson(_)).foreach(println)
//  }
}
