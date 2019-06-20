package lv.edreams.bdc.core.client

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import lv.edreams.bdc.core.dto.Record
import org.apache.kafka.common.serialization.Deserializer

class RecordDeserializer extends Deserializer[Record] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def deserialize(topic: String, data: Array[Byte]): Record = {
    (new ObjectMapper).readValue(data, classOf[Record])
  }

  override def close(): Unit = {}
}
