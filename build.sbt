organization := "com.inkenkun.x1"

name := "math-sandbox"

version := "0.1"

scalaVersion := "2.12.3"

scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-target:jvm-1.8")

libraryDependencies ++= Seq(
  "org.apache.commons"      % "commons-math3"        % "3.6.1",
  "ch.qos.logback"          % "logback-classic"      % "1.2.3",
  "org.scalatest"          %% "scalatest"            % "3.0.1"  % "test",
  "org.scalacheck"         %% "scalacheck"           % "1.13.4" % "test"
)

