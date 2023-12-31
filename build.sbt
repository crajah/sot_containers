import scala.language.postfixOps
import ohnosequences.sbt.SbtS3Resolver.autoImport.{s3, s3region, s3resolver}
import sbt.Keys.{libraryDependencies, publishTo, _}
import sbt.{Resolver, _}
import com.amazonaws.regions.{Region, Regions}
import Dependencies._
import SotDependencies._

lazy val scala_2_11 = "2.11.11"
lazy val scala_2_12 = "2.12.4"

lazy val IT = config("it") extend Test

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .configs(IT)
  .settings(inConfig(IT)(Defaults.testSettings): _*)
  .settings(Revolver.settings)
  .settings(addArtifact(artifact in (IntegrationTest, packageBin), packageBin in IntegrationTest).settings)
  .settings(
    name := "sot_containers",
    inThisBuild(Seq(
      organization := "parallelai",
      scalaVersion := scala_2_11
    )),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Ywarn-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-value-discard",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:reflectiveCalls",
      "-language:postfixOps",
      "-language:experimental.macros",
      "-Yrangepos",
      "-Yrepl-sync"
    ),
    crossScalaVersions := Seq(scala_2_11, scala_2_12),
    parallelExecution in ThisBuild := false,
    fork in Test := true,
    fork in IntegrationTest := true,
    s3region := Region.getRegion(Regions.EU_WEST_2),
    resolvers ++= Seq[Resolver](
      s3resolver.value("Parallel AI S3 Releases resolver", s3("release.repo.parallelai.com")) withMavenPatterns,
      s3resolver.value("Parallel AI S3 Snapshots resolver", s3("snapshot.repo.parallelai.com")) withMavenPatterns
    ),
    resolvers += sbtResolver.value,
    publishTo := {
      val prefix = if (isSnapshot.value) "snapshot" else "release"
      Some(s3resolver.value(s"Parallel AI $prefix S3 bucket", s3(s"$prefix.repo.parallelai.com")) withMavenPatterns)
    },
    libraryDependencies ++= Seq(
      scalatest % "test, it",
      mockito % "test, it",
      testContainers % "test, it"
    ),
    libraryDependencies ++= Seq(
      typesafeConfig,
      pureConfig,
      grizzledLogging,
      shapeless,
      gcloudDatastore,
      gcloudApiPubsub
    ),
    libraryDependencies ++= Seq(
      sotGcp
    )
  )