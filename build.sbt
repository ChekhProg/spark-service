import Dependencies._

name := "spark-service"

version := "0.1"

scalaVersion := "2.12.13"

libraryDependencies ++= Seq(
  Spark.Core,
  Spark.Sql,
  Spark.Kafka,
  Spark.Avro,
  Elasticsearch.Spark
)
