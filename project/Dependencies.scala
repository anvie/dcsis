import sbt._

object Dependencies {
    val resolutionRepos = Seq(
        "typesafe repo"   at "http://repo.typesafe.com/typesafe/releases/",
        "glassfish repo"  at "http://download.java.net/maven/glassfish/",
        "spray repo"      at "http://repo.spray.cc/",
        "Ansvia repo"     at "http://scala.repo.ansvia.com/releases/",
	    "Local repo"      at "file://" + Path.userHome + "/.m2/repository"
    )

    def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
    def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
    def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
    def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
    def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

    val ansviaCommons   = "com.ansvia" % "ansvia-commons" % "0.1.7"
    val ansviaPerf      = "com.ansvia" % "ansvia-perf" % "0.1.7"
    val ansviaIdgen      = "com.ansvia" % "ansvia-idgen" % "0.1.7"
    val specs2          = "org.specs2" %%  "specs2" % "1.12.4"
    val logback         = "ch.qos.logback" % "logback-classic" % "1.0.9"
    val qrgen           = "net.glxn" % "qrgen" % "1.4"
    val bc              = "org.bouncycastle" % "bcprov-jdk16" % "1.46"
    val apacheCommons   = "commons-io" % "commons-io" % "2.4"
    val gossip          = "com.jolira" % "java-gossip" % "1.5.2-SNAPSHOT"
}
