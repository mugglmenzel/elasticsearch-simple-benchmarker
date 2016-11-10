name := "elasticsearch-simple-benchmarker"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies += "org.elasticsearch" % "elasticsearch" % "2.4.1"
libraryDependencies += "org.elasticsearch.plugin" % "shield" % "2.4.1" from "http://maven.elasticsearch.org/releases/org/elasticsearch/plugin/shield/2.4.1/shield-2.4.1.jar"

//resolvers += "Elasticsearch Repo" at "https://maven.elasticsearch.org/releases/"
    