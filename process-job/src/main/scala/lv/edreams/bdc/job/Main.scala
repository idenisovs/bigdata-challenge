package lv.edreams.bdc.job

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import lv.edreams.bdc.core.client.RecordDeserializer
import lv.edreams.bdc.core.dto.Record

object Main extends App {
  println("Hello, world!")

  val HDFS_HOST = "hdfs://localhost:9000"
  val sparkConfig = new SparkConf().setMaster("local[2]").setAppName("Processing Job")
  val streamingContext = new StreamingContext(sparkConfig, Seconds(5))

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "kafka:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[RecordDeserializer],
    "group.id" -> "spark_test_group",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val topics = Array("records")

  val stream = KafkaUtils.createDirectStream[String, Record](
    streamingContext,
    PreferConsistent,
    Subscribe[String, Record](topics, kafkaParams)
  )

  // Output must be idempotent

  stream.foreachRDD(rdd => {
    if (!rdd.isEmpty()) {
      rdd.map(consumerRecord => {
        process(consumerRecord.value())
      }).saveAsTextFile(s"$HDFS_HOST/device-sim-record-${Instant.now().getEpochSecond}.json")
    }
  })

  streamingContext.start()
  streamingContext.awaitTermination()

  def process(record: Record): Record = {
    val timestamp = record.time.toLong

    val date = Date.from(Instant.ofEpochSecond(timestamp))

    val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")

    record.time = formatter.format(date)

    record
  }
}