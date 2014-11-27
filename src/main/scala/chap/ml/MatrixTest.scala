package chap.ml

import org.apache.spark.mllib.linalg.distributed._
import org.apache.spark.mllib.linalg.{Matrices, Matrix, Vector, Vectors}
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/11/14
 */
object MatrixTest extends App {

  val conf = new SparkConf().setAppName(s"Correlations with")
  conf.setMaster("local")
  val sc = new SparkContext(conf)

  val v1: Vector = Vectors.dense(1.0, 0.0, 3.0)
  val v2: Vector = Vectors.dense(1.0, 3.0)
  val v3: Vector = Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0))
  //    val v3: Vector = Vectors.sparse(3, Seq(0, 2), (1.0, 3.0))

  val dm: Matrix = Matrices.dense(3, 2, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0))

  val vv: RDD[Vector] = sc.parallelize(List(v1, v2))
  var mat: RowMatrix = new RowMatrix(vv) // v2的长度没有v1大，但是是按照v1的长度来生成矩阵的
  println(mat.numCols()) //3 取第一行数据的长度
  println(mat.numRows())
  //2

  val ivv: RDD[IndexedRow] = sc.parallelize(Seq(IndexedRow(0, v1), IndexedRow(2, v2)))
  var mati: IndexedRowMatrix = new IndexedRowMatrix(ivv)
  println(mati.numCols()) //3 取第一行数据的长度
  println(mati.numRows())
  //3 取最大indexed的值  (2 + 1)


  val en: RDD[MatrixEntry] = sc.parallelize(Seq(MatrixEntry(0, 0, 1), MatrixEntry(0, 1, 2), MatrixEntry(1, 0, 11), MatrixEntry(1, 1, 22)))
  val cmat: CoordinateMatrix = new CoordinateMatrix(en)
  println(cmat.numCols()) //2
  println(cmat.numRows())
  //2

  val vvv: RDD[Vector] = sc.parallelize(List(v1, v3))
  val sm = Statistics.colStats(vvv)
  println(sm.count) //2
  println(sm.max)
  println(sm.min)
  println(sm.mean)
  println(sm.variance)
  println(sm.numNonzeros)
  /*
  [1.0,0.0,3.0]
  [1.0,0.0,3.0]
  [1.0,0.0,3.0]
  [0.0,0.0,0.0]
  [2.0,0.0,2.0]
   */

  // 可以计算特征之间的相关性,默认是 pearson,spearman
  val x: RDD[Double] = sc.parallelize(List(1, 0, 4))
  val y: RDD[Double] = sc.parallelize(List(1, 0, 3))
  val smc = Statistics.corr(x, y, "pearson")
  println("smc:" + smc)
  //smc:0.9958705948858224

  val matrix = Statistics.corr(vv, "pearson")
  println("smc:")

  //======================
  //  val data = sc.parallelize(List((1, "asia"), (2, "chris"), (3, ""), (4, "manda")))
  //  import org.apache.spark.SparkContext.rddToPairRDDFunctions
  //  val fr = Fractions()
  //  val approx = data.map({ l => (l._1, l._2)}).sampleByKey(withReplacement = fasle, fra)
  //  val extra = data.map({ l => (l._1, l._2)}).sampleByKeyExact(withReplacement = fasle, fra)

  //page 30'
  Statistics.chiSqTest(matrix)
}
