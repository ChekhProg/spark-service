package com.chekh.sparkservice.parquetwriter

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.avro.functions.from_avro
import org.apache.spark.sql.functions.col

import java.nio.file.{ Files, Paths }

object Consumer extends App {

  val spark: SparkSession = SparkSession.builder
    .appName("StructurePersonConsumer")
    .master("local[2]")
    .getOrCreate()

  val jsonFormatSchema = new String(Files.readAllBytes(Paths.get("./src/main/resources/person.avsc")))

  val df = spark.read
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "topic1")
    .option("mode", "PERMISSIVE")
    .option("startingOffsets", "earliest")
    .load()

  val personDF = df
    .select(from_avro(col("value"), jsonFormatSchema).as("person"))
    .select("person.*")

  personDF.write.parquet("src/main/resources/output/people.parquet")
}
