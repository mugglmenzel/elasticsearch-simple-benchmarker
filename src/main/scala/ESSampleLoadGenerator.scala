import client.{ESClientHTTP, ESClientTCP}
import org.elasticsearch.common.xcontent.XContentFactory

/**
  * Created by menzelmi on 09/11/16.
  */
case class ESSampleLoadGenerator(samples: Int = 100, useHttp: Boolean = true, name: String = null, endpoint: String = null, port: Int = 0) {

  private lazy val client =
    if (useHttp) ESClientHTTP(name, endpoint, port)
    else ESClientTCP(name, endpoint, port)

  private lazy val generatedSamples = (1 to samples).map(id => id.toString -> generateSampleDocument(id))


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
