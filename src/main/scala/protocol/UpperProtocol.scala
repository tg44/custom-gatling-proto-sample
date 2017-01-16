package protocol

import akka.actor.ActorSystem
import io.gatling.core
import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolComponents, ProtocolKey}
import io.gatling.core.session.Session

class UpperProtocol(val address: String, val port: Int) extends Protocol {
	type Components = UpperComponents
}

object UpperProtocol {
	def apply(address: String, port: Int) = new UpperProtocol(address, port)

	val UpperProtocolKey = new ProtocolKey {

		type Protocol = UpperProtocol
		type Components = UpperComponents

		override def protocolClass: Class[core.protocol.Protocol] = classOf[UpperProtocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]

		override def defaultProtocolValue(configuration: GatlingConfiguration): UpperProtocol = throw new IllegalStateException("Can't provide a default value for UpperProtocol")

		override def newComponents(system: ActorSystem, coreComponents: CoreComponents): UpperProtocol => UpperComponents = {
			upperProtocol => UpperComponents(upperProtocol)
		}
	}
}

case class UpperComponents(upperProtocol: UpperProtocol) extends ProtocolComponents {
	def onStart: Option[Session => Session] = None

	def onExit: Option[Session => Unit] = None
}

case class UpperProtocolBuilder(address: String, port: Int) {
	def build() = UpperProtocol(address, port)
}

object UpperProtocolBuilder {
	def endpoint(address: String, port: Int) = UpperProtocolBuilder(address, port)
}
