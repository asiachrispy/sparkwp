/**
 * Description : some words
 * Author: chris
 * Date: 2014/9/16
 */
package play

import java.sql.{Connection, DriverManager}

/**
 * A Scala JDBC connection example by Alvin Alexander,
 * <a href="http://alvinalexander.com" title="http://alvinalexander.com">http://alvinalexander.com</a>
 */
object ScalaJdbcConnectSelect {

  def main(args: Array[String]) {
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://10.10.10.64:21008/recommend?characterEncoding=utf-8"
    val username = "root"
    val password = "@WSX#EDC6yhn"

    // there's probably a better way to do this
    var connection: Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM rs_job2job_txt WHERE 1 <= id AND ID <= 10")
      while (resultSet.next()) {
        val host = resultSet.getString("jid")
        val user = resultSet.getString("rsjid")
        println("host, user = " + host + ", " + user)
      }
    } catch {
      case e:Exception => e.printStackTrace
    }
    connection.close()
  }

}
