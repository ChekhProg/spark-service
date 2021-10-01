import sbt._

object Dependencies {
  val scalaCompat = "2.12.13"

  private object Version {
    val spark         = "3.1.1"
    val elasticsearch = "7.14.0"
  }

  object Spark {
    val Core  = "org.apache.spark" %% "spark-core"               % Version.spark
    val Sql   = "org.apache.spark" %% "spark-sql"                % Version.spark
    val Kafka = "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % Version.spark
    val Avro  = "org.apache.spark" % "spark-avro_2.12"           % Version.spark
  }

  object Elasticsearch {
    val Spark = "org.elasticsearch" %% "elasticsearch-spark-30" % Version.elasticsearch
  }
}
