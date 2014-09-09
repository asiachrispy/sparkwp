package chap.ml

import org.apache.spark._
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

/**
 * Description : some words
 * Author: chris 
 * Date: 2014/9/4
 */
object Bayes {

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "Bayes", System.getenv("SPARK_HOME"))
    val data = sc.textFile("mllib/data/sample_naive_bayes_data.txt")
    val parsedData = data.map(map(_))
    // Split data into training (60%) and test (40%).
    val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    val model = NaiveBayes.train(training, lambda = 1.0)
    val prediction = model.predict(test.map(_.features))

    val predictionAndLabel = prediction.zip(test.map(_.label))
    //        val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
  }

  def map(line: String) = {
    val parts = line.split(',')
    LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
  }

}
