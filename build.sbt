lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      version := "0.1",
      scalaVersion := "2.13.1"
    )),
    name := "credit-limit-reporter-scala"
  )

libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.8.2"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test
