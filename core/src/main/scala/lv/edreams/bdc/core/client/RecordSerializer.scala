package lv.edreams.bdc.core.client

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import lv.edreams.bdc.core.dto.Record
import org.apache.kafka.common.serialization.Serializer

class RecordSerializer extends Serializer[Record] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def serialize(topic: String, data: Record): Array[Byte] = {
    (new ObjectMapper).writeValueAsString(data).getBytes
  }

  override def close(): Unit = {}
}
