package com.heepoman.simple.akka.http.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContext, Future}

object AkkaHttpSimpleClient extends RequestBuilding {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  implicit class HttpClientServiceHandler[B](httpRequest: HttpRequest){
    def receiveAs[B](implicit m: Unmarshaller[HttpEntity, B], ec: ExecutionContext): Future[B] = {
      Http().singleRequest(httpRequest).flatMap[B] { res =>
        if(res.status.isRedirection()) {
          httpRequest.receiveAutomaticRedirection[B]
        } else {
          Unmarshal[HttpEntity](res.entity.withContentType(ContentTypes.`application/json`)).to[B]
        }
      }
    }

    def receiveAsHttpResponse: Future[HttpResponse] = Http().singleRequest(httpRequest)

    def receiveAutomaticRedirection[B](implicit m: Unmarshaller[HttpEntity, B], ec: ExecutionContext): Future[B] = {
      httpRequest.receiveAsHttpResponse.flatMap { res =>
        val getMethod = httpRequest.method
        val redirectionHeader = httpRequest.headers
        val redirectionUri = res.header[Location].get.uri
        val body = httpRequest.entity
        val redirectionHttpRequest = HttpRequest(method = getMethod, uri = redirectionUri, headers = redirectionHeader, entity = body)
        Http().singleRequest(redirectionHttpRequest).flatMap[B] { res =>
          Unmarshal[HttpEntity](res.entity.withContentType(ContentTypes.`application/json`)).to[B]
        }
      }
    }
  }

}
