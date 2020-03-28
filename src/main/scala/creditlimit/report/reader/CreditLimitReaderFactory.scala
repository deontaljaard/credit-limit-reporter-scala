package creditlimit.report.reader

import creditlimit.report.reader.csv.CSVCreditLimitReader
import creditlimit.report.reader.prn.PRNCreditLimitReader
import nl.marktplaatz.exceptions.InvalidInputFileException

import scala.util.{Failure, Try}

object CreditLimitReaderFactory {

  val supportedFormats: Set[String] = Set(".csv", ".prn")

  def apply(inFile: String): Try[CreditLimitReader] = {
    for {
      file <- getFileToRead(inFile)
    } yield reader(file)
  }

  def getFileToRead(inFile: String): Try[String] = {
    if (supportedFormats.exists(inFile.contains)) Try(inFile)
    else Failure(InvalidInputFileException(s"Unsupported file type detected for file $inFile. " +
      s"Supported file formats are '${supportedFormats.mkString("[", ",", "]")}'"))
  }

  // add other report readers here
  def reader(inFile: String): CreditLimitReader = inFile.substring(inFile.lastIndexOf('.')).toLowerCase match {
    case ".prn" => new PRNCreditLimitReader(inFile)
    case _ => new CSVCreditLimitReader(inFile)
  }
}
