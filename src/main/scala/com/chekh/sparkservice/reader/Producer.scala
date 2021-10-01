package com.chekh.sparkservice.reader

import com.chekh.sparkservice.model.{ PersonDqData, Person }
import org.apache.spark.sql.avro.functions.to_avro
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ IntegerType, StringType, StructField, StructType }
import org.apache.spark.sql.{ DataFrame, SparkSession }

import java.nio.file.{ Files, Paths }

object Producer extends App {

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
    .map(row => PersonDqData(Person.fromStrings(row.getString(1), row.getInt(2)), row.getInt(0)))

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
  kafkaDF.awaitTermination()
}
