import java.net.InetAddress

import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.shield.ShieldPlugin

/**
  * Created by menzelmi on 09/11/16.
  */
object ESSampleLoadGenerator extends App {

  val NUMBER_SAMPLES = 10000
  val USE_HTTP = false

  val CLUSTER_ENDPOINT = "ec2-52-211-134-166.eu-west-1.compute.amazonaws.com"
  val CLUSTER_PORT = 9300
  val CLUSTER_NAME = "elasticsearch"


  lazy val client = connect
  lazy val generatedSamples = (1 to NUMBER_SAMPLES).map(id => id.toString -> generateSampleDocument(id))


  def generateLoad = {
    generatedSamples.foreach(sample =>
      client
        .prepareIndex("samples", "json", sample._1)
        .setSource(sample._2)
        .get()
    )
    client.close()
  }

  generateLoad


  private def connect = {
    lazy val settings = Settings.settingsBuilder()
      .put("cluster.name", CLUSTER_NAME)
      .put("transport.tcp.compress", false)
      .build()
    val builder = TransportClient.builder()
    if (USE_HTTP) builder.addPlugin(classOf[ShieldPlugin])
    builder.settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(CLUSTER_ENDPOINT), CLUSTER_PORT))
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
