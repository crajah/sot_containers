resolvers ++= Seq[Resolver](
  "Typesafe" at "http://repo.typesafe.com/typesafe/releases",
  "Artima Maven Repository" at "http://repo.artima.com/releases",
  "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com",
  Resolver.sbtPluginRepo("releases"),
  Classpaths.sbtPluginReleases
)

// addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0") TODO - Need extra coursier to work with S3

addSbtPlugin("ohnosequences" % "sbt-s3-resolver" % "0.16.0")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")

addSbtPlugin("io.gatling" % "gatling-sbt" % "2.2.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

addSbtPlugin("net.vonbuchholtz" % "sbt-dependency-check" % "0.2.1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")