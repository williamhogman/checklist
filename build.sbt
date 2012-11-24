name := "checklist"

version := "0.1"

scalaVersion := "2.9.2"

resolvers += "Twitter Maven Repository" at "http://maven.twttr.com/"

libraryDependencies += "com.twitter" % "finatra" % "1.1.0"

libraryDependencies += "org.mongodb" %% "casbah" % "2.4.1"
