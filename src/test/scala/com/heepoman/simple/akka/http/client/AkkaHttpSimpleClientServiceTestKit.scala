package com.heepoman.simple.akka.http.client

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.ClasspathFileSource
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource
import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait AkkaHttpSimpleClientServiceTestKit extends BeforeAndAfterAll { self: Suite =>

  val serviceProviderServer = new WireMockServer(9007)

  override def beforeAll(): Unit = {
    serviceProviderServer.start()
    serviceProviderServer.loadMappingsUsing(new JsonFileMappingsSource(new ClasspathFileSource("http-client-test")))
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    try super.afterAll()
    finally serviceProviderServer.stop()
  }

  implicit class AwaitableFuture[A](f: Future[A]) {
    private val timeout = 10.seconds
    def await(): A = Await.result(f, timeout)
  }

}
