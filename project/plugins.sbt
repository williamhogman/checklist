resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

// Sbt plugin for building fat jars with all deps
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.8.5")