import sbt._

object Dependencies {
  val scalaCompat = "2.12.13"

  private object Version {
    val spark = "3.1.1"
    //val spark = "2.4.6"
    val kafka = "2.8.0"
    val elasticsearch = "7.14.0"
  }

  object Spark {
    val Core = "org.apache.spark" %% "spark-core" % Version.spark
    val Sql  = "org.apache.spark" %% "spark-sql" % Version.spark
    val Kafka = "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % Version.spark
    val Avro = "org.apache.spark" % "spark-avro_2.12" % Version.spark
  }

  object Kafka {
    val Client     = "org.apache.kafka" % "kafka-clients"               % Version.kafka
    val Akka       = "org.apache.kafka" %% "scala-kafka-client-akka"    % Version.kafka
    val TestKit    = "org.apache.kafka" %% "scala-kafka-client-testkit" % Version.kafka
  }

  object Elasticsearch {
    val Spark = "org.elasticsearch" %% "elasticsearch-spark-30" % Version.elasticsearch
    val Hadoop = "org.elasticsearch" % "elasticsearch-hadoop" % "6.1.0"
  }
}
