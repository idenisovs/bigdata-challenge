import sbt.Keys.libraryDependencies

ThisBuild / name := "Big Data Challenge"
ThisBuild / organization := "lv.edreams.bdc"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.8"

ThisBuild / resolvers ++= Seq(
  "Apache Repository" at "https://repository.apache.org/content/repositories/releases/",
  "Thrift" at "https://people.apache.org/~rawson/repo/"
)


lazy val root = (project in file("."))
  .aggregate(core, deviceSimulator, processJob)
  .disablePlugins(AssemblyPlugin)

lazy val core = (project in file("core"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.9",
      "org.apache.kafka" % "kafka-clients" % "2.2.1",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
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
    assemblyMergeStrategy in assembly := {
      case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
      case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
      case PathList("org", "apache", xs @ _*) => MergeStrategy.last
      case PathList("com", "google", xs @ _*) => MergeStrategy.first
      case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
      case "about.html" => MergeStrategy.rename
      case "plugin.properties" => MergeStrategy.last
      case "log4j.properties" => MergeStrategy.last
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    },

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-streaming" % "2.4.3" % "provided",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.3" % "provided",
      "org.apache.hbase" % "hbase" % "2.2.0",
      "org.apache.hbase" % "hbase-common" % "2.2.0",
      "org.apache.hbase" % "hbase-client" % "2.2.0",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    )
  )

