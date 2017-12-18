import scala.language.postfixOps
import Dependencies._
import ohnosequences.sbt.SbtS3Resolver.autoImport.{s3, s3region, s3resolver}
import sbt.Keys.{libraryDependencies, publishTo, _}
import sbt.{Resolver, _}
import com.amazonaws.regions.{Region, Regions}

lazy val IT = config("it") extend Test

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .configs(IT)
  .settings(inConfig(IT)(Defaults.testSettings): _*)
  .settings(Revolver.settings)
  .settings(
    name := "sot_containers",
    inThisBuild(Seq(
      organization := "parallelai",
      scalaVersion := "2.11.2"
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
      gcloudDatastore
    )
  )
