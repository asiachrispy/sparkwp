package chap

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/11/7
 */

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark._


object HBaseTest {
  def main(args: Array[String]) {
    val scf = new SparkConf()
    scf.setMaster("local")
    scf.setAppName("HBaseTest")
    val sc = new SparkContext(scf)

    val conf = HBaseConfiguration.create()
    conf.set(TableInputFormat.INPUT_TABLE, "test")
//    conf.set("hbase.zookeeper.property.clientPort", "2181"); //设置zookeeper client端口
//    conf.set("hbase.zookeeper.quorum", "localhost");  //设置zookeeper quorum
//    conf.set("hbase.master", "localhost:60000");  //设置hbase master
//    conf.addResource("/home/victor/software/hbase/conf/hbase-site.xml")  //将hbase的配置加载
//    val hadmin = new HBaseAdmin(conf);//实例化hbase管理

    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    println(hBaseRDD.count())

    val rs = hBaseRDD.take(1)
    println(rs(0)._1 +"-" +rs(0)._2)

    sc.stop()
    System.exit(0)
  }
}