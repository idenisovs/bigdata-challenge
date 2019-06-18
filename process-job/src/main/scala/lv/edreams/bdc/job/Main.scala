package lv.edreams.bdc.job

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

//import lv.edreams.bdc.core.client.Client

object Main extends App {
  println("Hello, world!")

  val sparkConfig = new SparkConf().setMaster("local[2]").setAppName("Processing Job")
  val streamingContext = new StreamingContext(sparkConfig, Seconds(5))

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "kafka:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "spark_test_group",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val topics = Array("records")

  val stream = KafkaUtils.createDirectStream[String, String](
    streamingContext,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams)
  )

  // Output must be idempotent
  stream.map(record => record.value()).print(10)

  // Commit offsets to a special Kafka topic to ensure recovery from a failure
  //    stream.foreachRDD { rdd =>
  //      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
  //      stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
  //    }

  streamingContext.start()
  streamingContext.awaitTermination()
}