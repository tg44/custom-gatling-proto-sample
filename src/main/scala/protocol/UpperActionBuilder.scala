package protocol

import io.gatling.commons.stats.OK
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.protocol.ProtocolComponentsRegistry
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.gatling.core.stats.message.ResponseTimings
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen

class UpperConnectActionBuilder(requestName: String) extends ActionBuilder {
	private def components(protocolComponentsRegistry: ProtocolComponentsRegistry) =
		protocolComponentsRegistry.components(UpperProtocol.UpperProtocolKey)

	override def build(ctx: ScenarioContext, next: Action): Action = {
		import ctx._
		val statsEngine = coreComponents.statsEngine
		val upperComponents = components(protocolComponentsRegistry)
		new UpperConnect(upperComponents.upperProtocol, statsEngine, next)
	}
}

class UpperConnect(protocol: UpperProtocol, val statsEngine: StatsEngine, val next: Action) extends ExitableAction with NameGen {
	override def name: String = genName("upperConnect")

	override def execute(session: Session) = {
		val k = new UpperServiceClient(protocol.address, protocol.port)
		val start = System.currentTimeMillis
		k.run
		val end = System.currentTimeMillis
		val timings = ResponseTimings(start, end)
		statsEngine.logResponse(session, name, timings, OK, None, None)
		next ! session
	}
}
