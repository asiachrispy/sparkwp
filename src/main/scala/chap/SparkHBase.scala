package chap

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/11/7
 */

import org.apache.hadoop.hbase.client.{Delete, HBaseAdmin, HTable, Put}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor}
import org.apache.spark._


object SparkHBase extends Serializable {

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "SparkHBase")

    val conf = HBaseConfiguration.create()
    //    conf.set("hbase.zookeeper.property.clientPort", "2181")
    //    conf.set("hbase.zookeeper.quorum", "centos.host1")
    //    conf.set("hbase.master", "centos.host1:60000")
    //    conf.addResource("/home/hadoop/software/hbase-0.92.2/conf/hbase-site.xml")
    conf.set(TableInputFormat.INPUT_TABLE, "test")

    val admin = new HBaseAdmin(conf)
    if (!admin.isTableAvailable("test")) {
      print("Table Not Exists! Create Table")
      val tableDesc = new HTableDescriptor("test")
      tableDesc.addFamily(new HColumnDescriptor("f1".getBytes()))
      admin.createTable(tableDesc)
    }

    //Put操作
    val table = new HTable(conf, "test")
    for (i <- 1 to 15) {
      val put = new Put(Bytes.toBytes("row" + i))
      put.add(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("value " + i))
      table.put(put)
    }
    table.flushCommits()

    //Delete操作
    val delete = new Delete(Bytes.toBytes("row1"))
    table.delete(delete)

    //Scan操作
    val hbaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    val count = hbaseRDD.count()
    println("HBase RDD Count:" + count)
    //hbaseRDD.cache()

    val res = hbaseRDD.take(count.toInt)
    for (j <- 0 until count.toInt) {
      println("j: " + j)
      val rs = res(j)._2
      for (kv <- rs.raw)
        println("rowkey:" + new String(kv.getRow()) +
          " cf:" + new String(kv.getFamily()) +
          " column:" + new String(kv.getQualifier()) +
          " value:" + new String(kv.getValue()))
    }

//    for (j <- 0 until count.toInt) {
//      println("j: " + j)
//      val rs = res(j)._2
//      for (cell <- rs.rawCells())
//        println("rowkey:" + new String(cell.getRowArray) +
//          " cf:" + new String(cell.getFamilyArray()) +
//          " column:" + new String(cell.getQualifierArray()) +
//          " value:" + new String(cell.getValueArray()))
//    }

    System.exit(0)
  }
}