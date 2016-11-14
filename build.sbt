/** Build file for io.nlytx.commons
  * @version 0.1.0
  */

val scalaTest = Seq(
  "org.scalactic" %% "scalactic" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)


lazy val commonSettings = Seq(
  organization := "io.nlytx",
  version := "0.1.0",
  scalaVersion in ThisBuild := "2.12.0"
)

lazy val nlytx_commons = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "commons",
    libraryDependencies ++= scalaTest
  )

scalacOptions in (Compile,doc) ++= Seq("-groups", "-implicits")

enablePlugins(SiteScaladocPlugin)