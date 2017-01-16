package protocol

class Upper(requestName: String) {
  def connect() = new UpperConnectActionBuilder(requestName)
}
