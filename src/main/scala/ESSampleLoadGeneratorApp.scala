import scopt.OptionParser

/**
  * Created by menzelmi on 10/11/16.
  */
object ESSampleLoadGeneratorApp extends App {

  new OptionParser[ESSampleLoadGenerator]("eslg"){
    head("Elasticsearch Sample Load Generator (eslg)", "1.0")
    opt[Int]('s', "sample-size").optional().action((value, generator) => generator.copy(samples = value))
    opt[String]('n', "cluster-name").required().action((value, generator) => generator.copy(name = value))
    opt[String]('e', "cluster-endpoint").optional().action((value, generator) => generator.copy(endpoint = value))
    opt[Int]('p', "cluster-port").optional().action((value, generator) => generator.copy(port = value))
  }.parse(args, ESSampleLoadGenerator()) match {
    case Some(generator) =>
      generator.measureLoad
    case None =>
  }

}
