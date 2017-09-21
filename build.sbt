val cats = "org.typelevel" %% "cats" % "0.8.1"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
val junit = "junit" % "junit" % "4.12" % "test"
val assertj = "org.assertj"%"assertj-core"%"3.8.0"

val commonsCodec = "commons-codec" % "commons-codec" % "1.10" // for data transformation exercises

val monocleVersion = "1.4.0"

val monocles = Seq(
  "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-law" % monocleVersion % "test"
)

val root = (project in file("."))
  .settings(
    name := """jug-workshops""",
    version := "1.0",
    scalaVersion := "2.12.1"
  ).settings(
  libraryDependencies ++= Seq(
    cats,
    commonsCodec,
    scalaTest, scalaCheck,
    junit,assertj
  )
  ).settings(libraryDependencies ++= monocles)
