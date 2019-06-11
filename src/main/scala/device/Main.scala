package device

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}
import java.util.{Timer, TimerTask, UUID}

import scala.io.Source
import device.dto.Location

object Main extends App {
  val locations: Map[String, Location] = Map(
    "Jekabpils" -> new Location(latitude = 56.499444, longitude = 25.878333),
    "Riga" -> new Location(latitude = 56.9475, longitude = 24.106389),
    "Jelgava" -> new Location(latitude = 56.648333, longitude = 23.713889),
    "Dobele" -> new Location(latitude = 56.616667, longitude = 23.266667)
  )

  val UUID_FILE_NAME = "uuid.txt"
  val location = getLocation(args(0))
  val device = new Device(id = readUUID(), location = location)

  val timer = new Timer

  val task = new TimerTask {
    def run(): Unit = {
      println(device.read())
    }
  }

  timer.schedule(task, 1000L, 1000L)

  sys.addShutdownHook({
    task.cancel()
  })

  def readUUID(): UUID = {
    if (!Files.exists(Paths.get(UUID_FILE_NAME))) {
      val uuidFile = new File(UUID_FILE_NAME)
      val writer = new PrintWriter(uuidFile)
      writer.write(UUID.randomUUID.toString)
      writer.close()
    }

    val uuid = Source.fromFile(UUID_FILE_NAME).mkString

    UUID.fromString(uuid)
  }

  def getLocation(city: String): Location = {
    locations.getOrElse(city, locations("Riga"))
  }
}
