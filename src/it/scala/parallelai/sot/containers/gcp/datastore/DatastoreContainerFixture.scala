package parallelai.sot.containers.gcp.datastore

import org.scalatest.Suite
import org.testcontainers.containers.wait.Wait
import parallelai.sot.containers.gcp.ProjectFixture
import parallelai.sot.containers.{Container, ContainersFixture}
import parallelai.sot.gcp.datastore.Kind

/**
  * docker run -p 8081:8081 --name my-datastore google/cloud-sdk gcloud beta emulators datastore start --project=test --host-port=0.0.0.0:8081
  */
trait DatastoreContainerFixture {
  this: Suite with ContainersFixture with ProjectFixture =>

  lazy val kind: Kind = Kind("kind-test")

  lazy val datastoreContainer = new DatastoreContainer(8081)

  override def teardown(): Unit = {
    // TODO
  }

  class DatastoreContainer(exposedPort: Int) extends Container(
    imageName = "google/cloud-sdk",
    exposedPorts = Seq(exposedPort),
    waitStrategy = Option(Wait.forHttp("/")),
    commands = Seq(s"gcloud beta emulators datastore start --project=${project.id} --host-port=0.0.0.0:$exposedPort")) {

    lazy val host: String = containerIpAddress

    lazy val port: Int = container.getMappedPort(exposedPort)

    lazy val ip: String = s"http://$host:$port"
  }
}