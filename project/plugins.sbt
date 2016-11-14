logLevel := Level.Warn

addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.2.0-RC1")

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.5.4")