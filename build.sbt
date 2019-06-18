import sbt.Keys.libraryDependencies

ThisBuild / name := "Big Data Challenge"
ThisBuild / organization := "lv.edreams.bdc"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.8"

lazy val root = (project in file("."))
  .aggregate(core, deviceSimulator, processJob)
  .disablePlugins(AssemblyPlugin)

lazy val core = (project in file("core"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.9",
      "org.apache.kafka" % "kafka-clients" % "2.2.1"
    ),
    excludeDependencies ++= Seq(
      ExclusionRule(organization = "org.apache.spark")
    )
  )

lazy val deviceSimulator = (project in file("device-simulator"))
  .dependsOn(core)
  .settings(
    name := "Device Simulator",
    assemblyJarName in assembly := s"device-sim-${version.value}.jar"
  )

lazy val processJob = (project in file("process-job"))
  .dependsOn(core)
  .settings(
    name := "Process Job",
    assemblyJarName in assembly := s"process-job-${version.value}.jar",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-streaming" % "2.4.3" % "provided",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.3" % "provided"
    )
  )

