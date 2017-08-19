name := """blocking-slick-play2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  guice,
  "com.github.takezoe" %% "blocking-slick-32" % "0.0.10",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
  "com.h2database" % "h2" % "1.4.192",
  "com.typesafe.play" %% "play-slick" % "3.0.0"
)
