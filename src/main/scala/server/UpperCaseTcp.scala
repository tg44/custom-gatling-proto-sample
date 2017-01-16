package server

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.Tcp.{IncomingConnection, ServerBinding}
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent._

object UpperCaseTcp {
  implicit val system = ActorSystem("ServerSys")
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val connections: Source[IncomingConnection, Future[ServerBinding]] = Tcp().bind("127.0.0.1", 8888)
    connections runForeach {
      connection =>
        println(s"New connection from: ${connection.remoteAddress}")
        val echo = Flow[ByteString].map(_.utf8String.toUpperCase).map(ByteString(_))
        connection.handleWith(echo)
    }
  }
}
