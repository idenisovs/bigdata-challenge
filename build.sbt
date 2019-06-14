ThisBuild / name := "Big Data Challenge"
ThisBuild / organization := "lv.edreams.bdc"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.8"

lazy val root = (project in file("."))
  .aggregate(core, deviceSimulator, processJob)

lazy val core = (project in file("core"))
  .disablePlugins(AssemblyPlugin)

lazy val deviceSimulator = (project in file("device-simulator"))
  .dependsOn(core)
  .settings(
    assemblyJarName in assembly := s"device-sim-${version.value}.jar",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.2.1",
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.9"
  )

lazy val processJob = (project in file("process-job"))
  .dependsOn(core)
  .settings(
    assemblyJarName in assembly := s"process-job-${version.value}.jar"
  )

