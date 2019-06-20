package lv.edreams.bdc.core.client

import java.math.BigInteger
import java.security.MessageDigest
import java.util.Properties

import lv.edreams.bdc.core.dto.Record
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class Client(val host: String = "kafka:9092") {
  val props = new Properties()

  props.put("bootstrap.servers", host)
  props.put("acks", "all")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "lv.edreams.bdc.core.client.RecordSerializer")

  val producer = new KafkaProducer[String, Record](props)

  def send(record: Record): Unit = {
    val producerRecord = new ProducerRecord[String, Record](Client.TARGET_TOPIC, record.toString, record)

    producer.send(producerRecord)
  }

  def close(): Unit = {
    producer.close()
  }

  def calculateChecksum(json: String): String = {
    val byteSequence = MessageDigest.getInstance("SHA-256").digest(json.getBytes("UTF-8"))
    String.format("%032x", new BigInteger(1, byteSequence))
  }
}

object Client {
  val TARGET_TOPIC = "records"
}
