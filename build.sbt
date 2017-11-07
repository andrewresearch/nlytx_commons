val commonsOrg = "io.nlytx"
val commonsName = "nlytx-nlp-commons"
val commonsVersion = "0.3.0"

val publish_to_BinTray = true

val scalaV = "2.12.3"

val scalatestV = "3.0.4"

lazy val nlytx_nlp_commons = (project in file(".")).
  settings(
    name := commonsName,
    organization := commonsOrg,
    version := commonsVersion,
    scalaVersion := scalaV,
    libraryDependencies ++= scalaTest,
    publishOptions
  )

lazy val scalaTest = Seq(
  "org.scalactic" %% "scalactic" % scalatestV,
  "org.scalatest" %% "scalatest" % scalatestV % "test"
)


/* Publishing  */

lazy val binTrayRealm = "Bintray API Realm"
lazy val binTrayUrl = s"https://api.bintray.com/content/nlytx/nlytx-nlp/"
lazy val binTrayCred = Credentials(Path.userHome / ".bintray" / ".credentials")
lazy val pubLicence = ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
lazy val commonsBinTray = Some(binTrayRealm at binTrayUrl + s"$commonsName/$commonsVersion/")

lazy val publishOptions = if (publish_to_BinTray) Seq(
  publishMavenStyle := true,
  licenses += pubLicence,
  credentials += binTrayCred,
  publishTo := commonsBinTray
)
else Seq(
  publishArtifact := false
)
