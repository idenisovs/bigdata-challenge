package lv.edreams.bdc.job

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.{Date, UUID}

import lv.edreams.bdc.core.dto.{Location, Record}
import org.scalatest.FunSuite

class MainTest extends FunSuite {
  private val timestamp: Long = 1561097379L
  private val deviceId = UUID.randomUUID()
  private val location = new Location(0d, 0d)
  private val temperature = 21
  private val expected = "2019-06-21T09:09:39+03:00"

  test("Timestamp processing") {
    val record = new Record(
      deviceId = deviceId,
      time = timestamp.toString,
      location = location,
      temperature = temperature
    )

    Main.process(record)

    assert(record.time === expected)
  }

  test("Date Transformation") {
    val date = Date.from(Instant.ofEpochSecond(timestamp))

    val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")

    val dateStr = dateFormat.format(date)

    assert(dateStr === expected)
  }
}
