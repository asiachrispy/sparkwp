///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package chap.ml
//
//import org.apache.spark._
//import org.apache.spark.mllib.feature._
//import org.apache.spark.mllib.linalg.Vectors
//import org.apache.spark.mllib.util.MLUtils
//import org.apache.spark.rdd.RDD
//
//object SparkPP {
//  def main(args: Array[String]) {
//    val conf = new SparkConf().setAppName("SparkPP").setMaster("local")
//    val sc = new SparkContext(conf)
//
//    // for tf-idf
//    // Load documents (one per line).
////    val documents: RDD[Seq[String]] = sc.textFile("...").map(_.split(" ").toSeq)
//    val documents: RDD[Seq[String]] = sc.parallelize(List("aa", "bb", "cc", "aa")).map(_.split(" ").toSeq)
//
//    val htF = new HashingTF()
//    val tf = htF.transform(documents)
//
//    tf.cache()
//    val idf = new IDF().fit(tf)
//    val tfidf = idf.transform(tf)
//    System.out.println(documents.collect().foreach(println))
//    System.out.println(tfidf.collect().foreach(println))
//    System.out.println(tf.collect().foreach(println))
//    sc.stop()
//
//    // for word2vector
//    val input = sc.textFile("text8").map(line => line.split(" ").toSeq)
//    val word2vec = new Word2Vec()
//    val model = word2vec.fit(input)
//    val synonyms = model.findSynonyms("china", 40)
//    for((synonym, cosineSimilarity) <- synonyms) {
//      println(s"$synonym $cosineSimilarity")
//    }
//
//    ////// for model fitting
//    val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")
//    val scaler1 = new StandardScaler().fit(data.map(x => x.features))
//    val scaler2 = new StandardScaler(withMean = true, withStd = true).fit(data.map(x => x.features))
//
//    // data1 will be unit variance.
//    val data1 = data.map(x => (x.label, scaler1.transform(x.features)))
//
//    // Without converting the features into dense vectors, transformation with zero mean will raise
//    // exception on sparse vector.
//    // data2 will be unit variance and zero mean.
//    val data2 = data.map(x => (x.label, scaler2.transform(Vectors.dense(x.features.toArray))))
//
//    ////// for L1 L2
//    val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")
//    val normalizer1 = new Normalizer()
//    val normalizer2 = new Normalizer(p = Double.PositiveInfinity)
//
//    // Each sample in data1 will be normalized using $L^2$ norm.
//    val data1 = data.map(x => (x.label, normalizer1.transform(x.features)))
//    // Each sample in data2 will be normalized using $L^\infty$ norm.
//    val data2 = data.map(x => (x.label, normalizer2.transform(x.features)))
//  }
//}
