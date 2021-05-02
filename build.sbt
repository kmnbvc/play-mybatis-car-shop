lazy val root = (project in file("."))
  .enablePlugins(PlayMinimalJava)
  .settings(
    name := "play-mybatis-car-shop",
    version := "2.8.x",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      evolutions,
      javaJdbc,
      guice,
      "com.h2database" % "h2" % "1.4.199",
      "com.palominolabs.http" % "url-builder" % "1.1.0",
      "net.jodah" % "failsafe" % "2.3.1",
      "org.mybatis" % "mybatis" % "3.5.7",
      "org.mybatis" % "mybatis-guice" % "3.12" exclude("org.slf4j", "slf4j-api"),
    ),
    PlayKeys.externalizeResources := false,
    testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v")),
  )
