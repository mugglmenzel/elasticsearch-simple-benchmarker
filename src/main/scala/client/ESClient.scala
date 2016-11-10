package client

import java.io.Closeable
import java.net.InetAddress

import io.searchbox.client.JestClientFactory
import io.searchbox.client.config.HttpClientConfig
import io.searchbox.core.Index
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress

/**
  * Created by menzelmi on 10/11/16.
  */
trait ESClient extends Closeable {

  val endpoint: String
  val port: Int

  def putDocument(id: String, doc: String)
}

case class ESClientTCP(cluster: String, endpoint: String, port: Int) extends ESClient {

  private lazy val client = connect

  private def connect = {
    lazy val settings = Settings.settingsBuilder()
      .put("cluster.name", cluster)
      .put("transport.tcp.compress", false)
      .build()
    TransportClient.builder()
      .settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(endpoint), port))
  }

  def putDocument(id: String, doc: String) = {
    println(s"Putting doc with $id")
    client.prepareIndex("samples", "json", id)
      .setSource(doc)
      .get()
  }

  def close = client.close()

}

case class ESClientHTTP(cluster: String, endpoint: String, port: Int) extends ESClient {

  lazy val client = connect

  private def connect = {
    val factory = new JestClientFactory()
    factory.setHttpClientConfig(new HttpClientConfig
    .Builder(s"http://$endpoint:$port")
      .multiThreaded(true)
      .build())
    factory.getObject()
  }

  def putDocument(id: String, doc: String) = {
    println(s"Putting doc with $id")
    client.execute(new Index.Builder(doc).index("samples").`type`("json").id(id).build())
  }

  def close = client.shutdownClient()
}
