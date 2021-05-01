lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := "play-mybatis-car-shop",
    version := "2.8.x",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      guice,
      "com.h2database" % "h2" % "1.4.199",
      "com.palominolabs.http" % "url-builder" % "1.1.0",
      "net.jodah" % "failsafe" % "2.3.1",
    ),
    PlayKeys.externalizeResources := false,
    testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v")),
  )
