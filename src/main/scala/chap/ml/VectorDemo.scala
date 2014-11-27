package chap.ml

import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.linalg.distributed._
import org.apache.spark.mllib.linalg.{Matrices, Matrix, Vector, Vectors}
import org.apache.spark.mllib.random.RandomRDDs
import org.apache.spark.mllib.regression.{LinearRegressionWithSGD, LabeledPoint}
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/10/22
 */
object VectorDemo {

  case class Params(input: String = "file:///D:\\gitspace\\sparkwp\\data\\sample_linear_regression_data.txt")

  def main(args: Array[String]) {
    val params = Params()
    val conf = new SparkConf().setAppName(s"Correlations with $params")
    conf.setMaster("local")
    val sc = new SparkContext(conf)

    //    val examples = MLUtils.loadLibSVMFile(sc, params.input).cache()
    //    corr(examples)

    //    frdd(sc)

    //    val examples = MLUtils.loadLibSVMFile(sc, "file:///D:\\gitspace\\sparkwp\\data\\sample_libsvm_data.txt").cache()
    //    regs2(examples)

    val examples = sc.textFile("file:///D:\\gitspace\\sparkwp\\data\\lpsa.data")
    linreg(examples)

    sc.stop()
  }

  def linreg(examples: RDD[String]) = {

    val datas = examples.map { line =>
      val par = line.split(",")
      LabeledPoint(par(0).toDouble, Vectors.dense(par(1).split(" ").map(_.toDouble)))
    }

    val model = LinearRegressionWithSGD.train(datas, 100)
    val valuesAndLabel = datas.map { point =>
      val pre = model.predict(point.features)
      (point.label, pre)
    }

//    val mse = valuesAndLabel.map(vl => math.pow((vl._1 - vl._2), 2d))
//      println(mse)
    }

    /**
     * 二元分类
     * @param examples
     */
    def regs2(examples: RDD[LabeledPoint]) = {
      val spt = examples.randomSplit(Array(0.6, 0.4), seed = 11l)
      val train = spt(0).cache()
      val test = spt(1)

      // for 1
      val numIts = 100
      val model = SVMWithSGD.train(train, numIts)
      model.clearThreshold()

      // for 2
      //    val svm = new SVMWithSGD()
      //    svm.optimizer.setNumIterations(numIts).setRegParam(0.1).setUpdater(new L1Updater)
      //   val model = svm.run(train)

      val scoreAndLab = test.map { point =>
        val score = model.predict(point.features)
        (score, point.label)
      }

      val mat = new BinaryClassificationMetrics(scoreAndLab)
      println(mat.areaUnderROC())
    }

    def frdd(sc: SparkContext) = {
      // Example: RandomRDDs.normalRDD
      val normalRDD: RDD[Double] = RandomRDDs.normalRDD(sc, 100l)
      println(s"Generated RDD of ${normalRDD.count()}" +
        " examples sampled from the standard normal distribution")
      println("  First 5 samples:")
      normalRDD.take(5).foreach(x => println(s"    $x"))

      // Example: RandomRDDs.normalVectorRDD
      val normalVectorRDD: RDD[Vector] = RandomRDDs.normalVectorRDD(sc, numRows = 100l, numCols = 2)
      println(s"Generated RDD of ${normalVectorRDD.count()} examples of length-2 vectors.")
      println("  First 5 samples:")
      normalVectorRDD.take(5).foreach(x => println(s"    $x"))
    }

    def corr(examples: RDD[LabeledPoint]) = {

      val labelRDD = examples.map(_.label)
      val numFeatures = examples.take(1)(0).features.size

      val corrType = "pearson"
      println(s"Correlation ($corrType) between label and each feature")
      println(s"Feature\tCorrelation")
      var feature = 0
      while (feature < numFeatures) {
        val featureRDD = examples.map(_.features(feature))
        val corr = Statistics.corr(labelRDD, featureRDD)
        println(s"$feature\t$corr")
        feature += 1
      }
      println()
    }

    def demo(sc: SparkContext) = {

      val v1: Vector = Vectors.dense(1.0, 0.0, 3.0)
      val v2: Vector = Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0))
      //    val v3: Vector = Vectors.sparse(3, Seq(0, 2), (1.0, 3.0))

      val pos = LabeledPoint(1.0, v1)
      val neg = LabeledPoint(0.0, v2)
      val dm: Matrix = Matrices.dense(3, 2, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0))

      val vv: RDD[Vector] = sc.parallelize(Seq(v1,v2))
      var mat: RowMatrix = new RowMatrix(vv)
      println(mat.numCols())
      println(mat.numRows())

      val ivv: RDD[IndexedRow] = null
      var mati: IndexedRowMatrix = new IndexedRowMatrix(ivv, 3, 2)
      println(mati.numCols())
      println(mati.numRows())
      mat = mati.toRowMatrix()

      val en: RDD[MatrixEntry] = null
      val cmat: CoordinateMatrix = new CoordinateMatrix(en)
      println(cmat.numCols())
      println(cmat.numRows())
      mati = cmat.toIndexedRowMatrix()

      val sm = Statistics.colStats(vv)
      println(sm.count)
      println(sm.max)
      println(sm.min)
      println(sm.mean)
      println(sm.variance)
      println(sm.numNonzeros)


    }
  }
