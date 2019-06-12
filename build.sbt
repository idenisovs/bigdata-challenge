name := "device-simulator"

organization := "lv.edreams"

version := "0.1"

scalaVersion := "2.13.0"

assemblyJarName in assembly := "device-simulator-${version.value}.jar"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.2.1"