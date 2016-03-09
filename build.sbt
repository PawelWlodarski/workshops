name := """jug-workshops"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "junit" % "junit" % "4.12"
libraryDependencies += "org.assertj" % "assertj-core" % "3.3.0"

libraryDependencies += "com.javaslang" % "javaslang" % "2.0.0-RC4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"

libraryDependencies += "org.typelevel" %% "cats" % "0.4.1"
