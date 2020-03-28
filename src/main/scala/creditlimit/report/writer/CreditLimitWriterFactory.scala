package creditlimit.report.writer

import creditlimit.report.writer.html.HTMLCreditLimitWriter
import nl.marktplaatz.exceptions.InvalidOutputFileException

import scala.util.{Failure, Try}

object CreditLimitWriterFactory {

  val supportedFormats: Set[String] = Set(".html")

  def apply(outFile: String): Try[CreditLimitWriter] = {
    for {
      file <- getFileToWrite(outFile)
    } yield writer(file)
  }

  def getFileToWrite(inFile: String): Try[String] = {
    if (supportedFormats.exists(inFile.contains)) Try(inFile)
    else Failure(InvalidOutputFileException(s"Unsupported file type detected for file $inFile. " +
      s"Supported file formats are '${supportedFormats.mkString("[", ",", "]")}'"))
  }

  // add other report writers here
  def writer(outFile: String): CreditLimitWriter = outFile.substring(outFile.lastIndexOf('.')).toLowerCase match {
    case _ => new HTMLCreditLimitWriter(outFile)
  }
}
