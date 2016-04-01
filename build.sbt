// import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

// workbenchSettings

name := "Scala-Mandelbrot"

version := "0.1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "be.doeraene"    %%% "scalajs-jquery" % "0.9.0",
  "com.lihaoyi"    %%% "scalatags"      % "0.5.4",
  "com.lihaoyi"    %%% "utest"          % "0.4.3"
)

// bootSnippet := "example.ScalaJSMandelbrot().main(document.getElementById('canvas'));"

testFrameworks += new TestFramework("utest.runner.Framework")

// updateBrowsers <<= updateBrowsers.triggeredBy(fastOptJS in Compile)

