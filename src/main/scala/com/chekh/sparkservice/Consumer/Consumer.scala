package com.chekh.sparkservice.Consumer

import com.chekh.sparkservice.Person.Person
import org.apache.spark.sql.avro.functions.from_avro
import org.apache.spark.sql.functions.{col, struct}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.nio.file.{Files, Paths}

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

  val personDF = df.select(from_avro(col("value"), jsonFormatSchema).as("person"))
    .select("person.*")

  personDF.show()

  personDF.write.parquet("src/main/resources/output/people.parquet")
}
