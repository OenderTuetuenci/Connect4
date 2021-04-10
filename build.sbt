organization := "Connect4"
lazy val commonSettings = Seq(
    organization := "Connect4",
    version := "0.1",
    scalaVersion := "2.13.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.11",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.0"
)

lazy val root = (project in file(".")).settings(
  commonSettings,
  name := "Connect4"
).dependsOn(model,controller,gui,tui)

lazy val model = (project in file("Model")).settings(
  commonSettings,
  name := "Connect4-Model"
)

lazy val controller = (project in file("Controller")).settings(
  commonSettings,
  name := "Connect4-Controller"
).dependsOn(model)

lazy val gui = (project in file("GUI")).settings(
  commonSettings,
  name := "Connect4-GUI"
).dependsOn(controller,model)

lazy val tui = (project in file("TUI")).settings(
  commonSettings,
  name := "Connect4-TUI"
).dependsOn(controller,model)



