package com.heepoman.simple.akka.http.client.examples

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.heepoman.simple.akka.http.client.AkkaHttpSimpleClient._
import com.heepoman.simple.akka.http.client.AkkaHttpSimpleClientServiceTestKit
import org.scalatest.{FlatSpecLike, Matchers}
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global

class AkkaHttpSimpleClientSpec
  extends FlatSpecLike
    with Matchers
    with AkkaHttpSimpleClientServiceTestKit
    with SprayJsonSupport
    with DefaultJsonProtocol {

  case class AnyYourResponseObject(bar: String)
  implicit val fooTestResponseFormat: RootJsonFormat[AnyYourResponseObject] = jsonFormat1(AnyYourResponseObject)
  implicit val unitFormat: RootJsonFormat[Unit] = jsonFormat0[Unit](() => ())

  private val body =
    """
      |{
      |  "foo": "baz
      |}
    """.stripMargin

  "AkkaHttpSimpleClient" should "send http request get method call" in {
    Get(s"http://localhost:9007/v1/foo").receiveAs[AnyYourResponseObject].await shouldBe AnyYourResponseObject("baz")
    Get(s"http://localhost:9007/v1/foo").receiveAsHttpResponse.await should matchPattern {
      case HttpResponse(StatusCodes.OK, _, _, _) =>
    }
  }

  it should "send http request post method call" in {
    Post(s"http://localhost:9007/v1/foo", body).receiveAs[AnyYourResponseObject].await shouldBe AnyYourResponseObject("baz")
    Post(s"http://localhost:9007/v1/foo", body).receiveAs[Unit].await shouldBe ()
    Post(s"http://localhost:9007/v1/foo", body).receiveAsHttpResponse.await should matchPattern {
      case HttpResponse(StatusCodes.OK, _, _, _) =>
    }
  }

  it should "send http request put method call" in {
    Put(s"http://localhost:9007/v1/foo", body).receiveAs[AnyYourResponseObject].await shouldBe AnyYourResponseObject("baz")
    Put(s"http://localhost:9007/v1/foo", body).receiveAs[Unit].await shouldBe ()
    Put(s"http://localhost:9007/v1/foo", body).receiveAsHttpResponse.await should matchPattern {
      case HttpResponse(StatusCodes.OK, _, _, _) =>
    }
  }

  it should "send http request delete method call" in {
    Delete(s"http://localhost:9007/v1/foo").receiveAs[AnyYourResponseObject].await shouldBe AnyYourResponseObject("baz")
    Delete(s"http://localhost:9007/v1/foo").receiveAs[Unit].await shouldBe ()
    Delete(s"http://localhost:9007/v1/foo").receiveAsHttpResponse.await should matchPattern {
      case HttpResponse(StatusCodes.OK, _, _, _) =>
    }
  }

}
