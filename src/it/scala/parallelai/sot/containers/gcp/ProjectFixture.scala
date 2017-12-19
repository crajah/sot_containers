package parallelai.sot.containers.gcp

import org.scalatest.Suite
import parallelai.sot.gcp.Project

trait ProjectFixture {
  this: Suite =>

  lazy val project = Project("project-id-test")
}