import sbt.Keys.libraryDependencies

ThisBuild / name := "Big Data Challenge"
ThisBuild / organization := "lv.edreams.bdc"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.8"

lazy val root = (project in file("."))
  .aggregate(core, deviceSimulator, processJob)

lazy val core = (project in file("core"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.9",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.2.1"
  )

lazy val deviceSimulator = (project in file("device-simulator"))
  .dependsOn(core)
  .settings(
    name := "Device Simulator",
    assemblyJarName in assembly := s"device-sim-${version.value}.jar",
  )

lazy val processJob = (project in file("process-job"))
  .dependsOn(core)
  .settings(
    name := "Process Job",
    assemblyJarName in assembly := s"process-job-${version.value}.jar",
    libraryDependencies += "org.apache.spark" % "spark-streaming_2.12" % "2.4.3" % "provided",
    libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10-assembly_2.11" % "2.4.3"
  )

