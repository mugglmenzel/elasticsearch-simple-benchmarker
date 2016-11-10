import scopt.OptionParser

/**
  * Created by menzelmi on 10/11/16.
  */
object ESSampleLoadGeneratorApp extends App {

  new OptionParser[ESSampleLoadGenerator]("eslg"){
    head("Elasticsearch Sample Load Generator (eslg)", "1.0")
    opt[Int]('s', "sample-size").optional().action((value, generator) => generator.copy(samples = value))
    opt[Int]('r', "record-size").optional().action((value, generator) => generator.copy(sampleSize = value))
    opt[String]('n', "cluster-name").required().action((value, generator) => generator.copy(name = value))
    opt[String]('e', "cluster-endpoint").required().action((value, generator) => generator.copy(endpoint = value))
    opt[Int]('p', "cluster-port").required().action((value, generator) => generator.copy(port = value))
    opt[Unit]('h', "use-http").optional().action((value, generator) => generator.copy(useHttp = true))
  }.parse(args, ESSampleLoadGenerator()) match {
    case Some(generator) =>
      generator.measureLoad
    case None =>
  }

}
