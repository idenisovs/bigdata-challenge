package lv.edreams.bdc.core.dto

import java.util.UUID

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
class Record(var deviceId: UUID, var time: String, var location: Location, var temperature: Int) {
  def this() {
    this(null, "0", null, 0)
  }

  override def toString: String = {
    "{ time: " + time + ", deviceId: " + (if (deviceId == null) "null" else deviceId) +
      ", location: { latitude: " + location.latitude + ", longitude: " + location.longitude + " }" +
      ", temperature: " + temperature +
      " }"
  }
}
