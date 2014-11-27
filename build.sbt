name := "sparkwp"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.0.0",
  "org.apache.spark" %% "spark-sql" % "1.0.0",
  "org.apache.spark" %% "spark-streaming" % "1.0.0",
  "org.apache.spark" %% "spark-hive" % "1.0.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.4"
)
