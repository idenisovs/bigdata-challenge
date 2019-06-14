package lv.edreams.bdc.device

import java.time.Instant
import java.util.UUID

import lv.edreams.bdc.device.dto.{Location, Record}

class Device(val id: UUID, val location: Location) {
  private val startTime = Instant.now.getEpochSecond

  def read(): Record = {
    val timestamp = Instant.now.getEpochSecond

    new Record(
      deviceId = id,
      location = location,
      time = timestamp,
      temperature = temperature(timestamp)
    )
  }

  private def temperature(currentTime: Long): Int = {
    val avgTemp: Int = 21
    val deviation: Double = 5.5d
    val d = (currentTime - startTime).asInstanceOf[Double]

    avgTemp + (Math.sin(d/5d) * deviation).asInstanceOf[Int]
  }
}
