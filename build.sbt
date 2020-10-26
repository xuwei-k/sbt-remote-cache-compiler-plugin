name := "my-project"

scalaVersion := "2.13.3"

pushRemoteCacheTo := Some(
  MavenCache("local-cache", (ThisBuild / baseDirectory).value / "my_remote_cache")
)

remoteCacheId := "fixed-id"

remoteCacheIdCandidates := Seq(remoteCacheId.value)

pushRemoteCacheConfiguration := pushRemoteCacheConfiguration.value.withOverwrite(true)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)
