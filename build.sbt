name := "my-project"

scalaVersion := "2.13.3"

pushRemoteCacheTo := Some(
  MavenCache("local-cache", (ThisBuild / baseDirectory).value / "my_remote_cache")
)

remoteCacheId := "fixed-id"

remoteCacheIdCandidates := Seq(remoteCacheId.value)

pushRemoteCacheConfiguration := pushRemoteCacheConfiguration.value.withOverwrite(true)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)

Seq(Compile, Test).map { c =>
  c / scalacOptions := {
    val base = (LocalRootProject / baseDirectory).value
    val compilerPluginsTempDir = (LocalRootProject / target).value / "compiler_plugins"
    val prefix = "-Xplugin:"
    (c / scalacOptions).value.map { opt =>
      if (opt startsWith prefix) {
        val originalPluginFile = file(opt.drop(prefix.size))
        val pluginJarName = originalPluginFile.getName
        val targetJar = compilerPluginsTempDir / pluginJarName
        if(!targetJar.isFile) {
          IO.copyFile(
            sourceFile = originalPluginFile,
            targetFile = targetJar
          )
        }
        prefix + IO.relativize(base, targetJar).get
      } else {
        opt
      }
    }
  }
}
