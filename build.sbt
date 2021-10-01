import Dependencies._

name := "spark-service"

version := "0.1"

scalaVersion := "2.12.13"

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      Spark.Core,
      Spark.Sql,
      Spark.Kafka,
      Spark.Avro,
      Elasticsearch.Spark
    )
  )
  .aggregate(parquetwriter)

lazy val parquetwriter = (project in file("parquetwriter"))
  .settings(
    libraryDependencies ++= Seq(
      Spark.Core,
      Spark.Sql,
      Spark.Kafka,
      Spark.Avro
    )
  )
