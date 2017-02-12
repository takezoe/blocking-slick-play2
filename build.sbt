name := """blocking-slick-play2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.github.takezoe" %% "blocking-slick-32" % "0.0.7",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.h2database" % "h2" % "1.4.192",
  "com.typesafe.play" %% "play-slick" % "2.0.0"
)
