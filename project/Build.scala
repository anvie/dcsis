import sbt._
import Keys._

// Author: Robin

object Build extends Build {
	import BuildSettings._
	import Dependencies._

  	// root
	lazy val root = Project("root", file("."))
		.aggregate(examples, dcsis, server)
		.settings(basicSettings: _*)
		.settings(noPublishing: _*)
		
	// modules
	lazy val examples = Project("examples", file("examples"))
		.settings(moduleSettings: _*)
		.settings(libraryDependencies ++= 
			compile(ansviaCommons) ++
			test(specs2) ++
			runtime(logback)
		)

	lazy val dcsis = Project("dcsis-core", file("dcsis-core"))
		.settings(moduleSettings: _*)
		.settings(libraryDependencies ++=
			compile(ansviaCommons, ansviaIdgen, bc, qrgen,
                apacheCommons) ++
			test(specs2) ++
			runtime(logback)
		)

	lazy val server = Project("dcsis-server", file("dcsis-server"))
		.settings(moduleSettings: _*)
		.settings(libraryDependencies ++=
			compile(ansviaCommons, gossip,
                apacheCommons, liftWebkit) ++
			test(specs2) ++
			runtime(logback)
		).dependsOn(dcsis)
}
