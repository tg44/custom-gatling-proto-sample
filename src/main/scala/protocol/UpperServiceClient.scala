package protocol

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Source, Tcp}
import akka.util.ByteString

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class UpperServiceClient(ip: String, port: Int) {
	def run = {
		implicit val system = ActorSystem("ClientSys")
		implicit val materializer = ActorMaterializer()


		val testInput = ('a' to 'z').map(ByteString(_))

		val result: Future[ByteString] = Source(testInput).via(Tcp().outgoingConnection(ip, port)).
			runFold(ByteString.empty) { (acc, in) ⇒ acc ++ in }

		val res: ByteString = Await.result(result, 10.seconds)
	}
}
