name := "quizzitdue"
description := "Make your own quiz questions and test your memory"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  filters,
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.sangria-graphql" %% "sangria" % "1.4.2",
  "org.sangria-graphql" %% "sangria-slowlog" % "0.1.8",
  "org.sangria-graphql" %% "sangria-play-json" % "1.0.4",
  "org.scala-lang" % "scala-library" % "2.12.6",
  // "org.playframework" %% "anorm" % "2.6.2",
  // "com.typesafe.play" %% "anorm" % "2.4.0",
  "org.playframework.anorm" %% "anorm" % "2.6.2",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")

routesGenerator := InjectedRoutesGenerator

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

resolvers += Resolver.sonatypeRepo("snapshots")
