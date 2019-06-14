package lv.edreams.bdc.device

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}
import java.util.{Timer, TimerTask, UUID}

import lv.edreams.bdc.device.client.Client
import lv.edreams.bdc.device.dto.Location

import scala.io.Source

object Main extends App {
  val UUID_FILE_NAME = "uuid.txt"

  val device = new Device(id = readUUID(), location = getLocation(args(0)))
  val client = new Client

  val timer = new Timer

  val task = new TimerTask {
    def run(): Unit = {
      val record = device.read()

      println(record)

      client.send(record)
    }
  }

  timer.schedule(task, 1000L, 1000L)

  sys.addShutdownHook({
    task.cancel()
    client.close()
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
    val locations: Map[String, Location] = Map(
      "Jekabpils" -> new Location(latitude = 56.499444, longitude = 25.878333),
      "Riga" -> new Location(latitude = 56.9475, longitude = 24.106389),
      "Jelgava" -> new Location(latitude = 56.648333, longitude = 23.713889),
      "Dobele" -> new Location(latitude = 56.616667, longitude = 23.266667)
    )

    locations.getOrElse(city, locations("Riga"))
  }
}