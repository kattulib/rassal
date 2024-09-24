import sbt._

object Dependencies {
  lazy val munit = Def.setting("org.scalameta" %% "munit" % "1.0.0-M10")
  lazy val kuram =
    Def.setting("io.github.lamdalib" %% "kuram-core" % "0.1.0-SNAPSHOT")
}
