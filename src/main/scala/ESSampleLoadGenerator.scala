import client.{ESClientHTTP, ESClientTCP}
import org.elasticsearch.common.xcontent.XContentFactory

/**
  * Created by menzelmi on 09/11/16.
  */
object ESSampleLoadGenerator extends App {

  val NUMBER_SAMPLES = 100
  val USE_HTTP = true

  val CLUSTER_ENDPOINT = "search-elasticsearch-l7nmgytxg3mxykmz7ray2b2bbi.eu-west-1.es.amazonaws.com"
  val CLUSTER_PORT = 80
  val CLUSTER_NAME = "elasticsearch"

  val client =
    if (USE_HTTP) ESClientHTTP(CLUSTER_NAME, CLUSTER_ENDPOINT, CLUSTER_PORT)
    else ESClientTCP(CLUSTER_NAME, CLUSTER_ENDPOINT, CLUSTER_PORT)

  lazy val generatedSamples = (1 to NUMBER_SAMPLES).map(id => id.toString -> generateSampleDocument(id))


  def measureLoad = {
    val start = System.currentTimeMillis()
    println(s"Starting at $start.")
    generatedSamples.foreach(sample =>
      putDocument(sample._1, sample._2)
    )
    val end = System.currentTimeMillis()
    println(s"Ending at $end.")

    println(s"Took ${end - start}ms.")
  }

  measureLoad


  private def putDocument(id: String, doc: String) = {
    client.putDocument(id, doc)
  }

  private def generateSampleDocument(id: Int) =
    XContentFactory.jsonBuilder()
      .startObject()
      .field("id", id)
      .field("name", s"name_is_$id")
      .field("description", s"I am document with id $id.")
      .endObject()
      .string()


}
