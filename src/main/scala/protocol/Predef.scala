package protocol

object Predef {
  val upper = UpperProtocolBuilder

  implicit def upperBuilderToProtocol(builder: UpperProtocolBuilder): UpperProtocol = builder.build()

  def upper(name: String) = new Upper(name)
}
