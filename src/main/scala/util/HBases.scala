package util

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HConnection, HConnectionManager}

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/11/13
 */
object HBases {
  private val conf = HBaseConfiguration.create()

  // need to set conf or have hbase-site.xml in resources

  // get a connection from pool
  def getConnection(): HConnection = {
    HConnectionManager.createConnection(conf)
  }

  def main(args: Array[String]) {
    val connection = HConnectionManager.createConnection(conf)
    val table = connection.getTable("myTable")
  }

}
