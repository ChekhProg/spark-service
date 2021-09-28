package com.chekh.sparkservice.Reader

import com.chekh.sparkservice.Data.Data
import com.chekh.sparkservice.Person.Person
import org.apache.spark.SparkConf
import org.apache.spark.sql.avro.functions.to_avro
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions
import org.elasticsearch.spark.sql.EsSparkSQL
import org.elasticsearch.spark.sql._
import org.elasticsearch.spark.streaming._

import java.nio.file.{Files, Paths}

object Reader extends App {

//  val conf = new SparkConf().setAppName("spark-basic-hw").setMaster("local")
//  conf.set("es.index.auto.create", "true")
//
//  ss.sparkContext.setLogLevel("ERROR")
//
//  lazy val ss: SparkSession =
//    SparkSession.builder
//      .config(ConfigurationOptions.ES_NODES, "localhost")
//      .config(ConfigurationOptions.ES_PORT, "9200")
//      .config(conf)
//      .getOrCreate()

  val spark: SparkSession = SparkSession.builder
    .appName("StructurePersonReader")
    .master("local[1]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  val schemaDf = StructType(
    Array(
      StructField("id", IntegerType),
      StructField("name", StringType),
      StructField("age", IntegerType)
    )
  )

  val csvDF: DataFrame = spark.readStream
    .schema(schemaDf)
    .format("csv")
    .load("src/main/resources/input")

  import spark.implicits._

  val dataDF = csvDF
    .map(row => Data(Person.fromStrings(row.getString(1), row.getInt(2)), row.getInt(0)))

  val schemaAvro = new String(Files.readAllBytes(Paths.get("./src/main/resources/person.avsc")))

  val elasticDF = dataDF
    .map(data => data.metric)
    .writeStream
    .outputMode("append")
    .format("es")
    .option("checkpointLocation", "/Users/anpavlov/dev/spark/elastic/checkpoints")
    .start("spark/index")

  val kafkaDF = dataDF
    .map(data => data.person)
    .filter(_.isDefined)
    .select(to_avro(col("value"), schemaAvro) as "value")
    .writeStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("topic", "topic1")
    .option("checkpointLocation", "/Users/anpavlov/dev/spark/kafka/checkpoints")
    .start()

  elasticDF.awaitTermination()
  //kafkaDF.awaitTermination()
}
