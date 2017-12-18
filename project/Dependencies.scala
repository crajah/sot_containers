import sbt._

object Dependencies {
  val gcloudVersion = "1.6.0"

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.4"
  val mockito = "org.mockito" % "mockito-all" % "1.10.19"
  val grizzledLogging = "org.clapper" %% "grizzled-slf4j" % "1.3.1"
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val testContainers = "com.dimafeng" %% "testcontainers-scala" % "0.11.0"

  val typesafeConfig = "com.typesafe" % "config" % "1.3.2"
  val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.8.0"

  val shapeless = "com.chuusai" %% "shapeless" % "2.3.2"

  val gcloudDatastore = "com.google.cloud" % "google-cloud-datastore" % gcloudVersion
  val gcloudStorage = "com.google.cloud" % "google-cloud-storage" % gcloudVersion
}
