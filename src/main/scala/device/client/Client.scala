package device.client

import java.util.Properties

import device.dto.Record
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class Client {
  val TARGET_TOPIC = "records"

  val props = new Properties()

  props.put("bootstrap.servers", "kafka:9092")
  props.put("acks", "all")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  def send(record: Record): Unit = {
    val key = record.time.toString
    val value = record.toString

    producer.send(new ProducerRecord[String, String](TARGET_TOPIC, key, value))
  }

  def close(): Unit = {
    producer.close()
  }
}
