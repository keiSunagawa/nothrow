lazy val V = _root_.scalafix.sbt.BuildInfo

organization in ThisBuild := "me.kerfume"
scalaVersion in ThisBuild := V.scala212
version in ThisBuild := "0.1.0-SNAPSHOT"
scalafmtOnCompile in ThisBuild := true

lazy val rules = project.settings(
  moduleName := "nothrow",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion
)

lazy val input = project.settings(
  skip in publish := true,
  addCompilerPlugin(scalafixSemanticdb)
)

lazy val output = project.settings(
  skip in publish := true
)

lazy val tests = project
  .settings(
    skip in publish := true,
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full,
    scalafixTestkitOutputSourceDirectories :=
      sourceDirectories.in(output, Compile).value,
    scalafixTestkitInputSourceDirectories :=
      sourceDirectories.in(input, Compile).value,
    scalafixTestkitInputClasspath :=
      fullClasspath.in(input, Compile).value
  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)