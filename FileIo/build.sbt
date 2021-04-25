val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.4"
version := "0.1"
scalaVersion := "2.13.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.11"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.0"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion)
libraryDependencies += "de.heikoseeberger" %% "akka-http-play-json" % "1.32.0"