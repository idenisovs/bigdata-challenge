package device

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}
import java.util.{Timer, TimerTask, UUID}

import scala.io.Source
import device.dto.Location

object Main extends App {
  val UUID_FILE_NAME = "uuid.txt"
  val location = new Location(latitude = 56.616667, longitude = 23.266667)
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
}
