package device.dto

import java.util.UUID

class Record(var deviceId: UUID, var time: Long, var location: Location, var temperature: Int) {

  override def toString: String = {
    "{ time: " + time + ", deviceId: " + (if (deviceId == null) "null" else deviceId) +
      ", location: { latitude: " + location.latitude + ", longitude: " + location.longitude + " }" +
      ", temperature: " + temperature +
      " }"
  }
}
