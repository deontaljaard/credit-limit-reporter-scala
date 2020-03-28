package creditlimit

package object exceptions {

  case class InvalidInputFileException(message: String) extends Exception(message)

  case class InvalidOutputFileException(message: String) extends Exception(message)

  case class MalformedCSVException(message: String) extends Exception(message)

}
