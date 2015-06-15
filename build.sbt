name := "SecondTryApp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
   "com.google.inject" % "guice" % "4.0-beta",
   "com.couchbase.client" % "couchbase-client" % "1.4.1",
   "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.1"
)

play.Project.playScalaSettings

resolvers += "Couchbase Maven Repository" at "http://files.couchbase.com/maven2"