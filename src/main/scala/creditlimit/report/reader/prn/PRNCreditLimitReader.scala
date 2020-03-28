package creditlimit.report.reader.prn

import java.nio.charset.CodingErrorAction

import creditlimit.model.{CreditLimitReport, CreditLimitRow}
import creditlimit.report.reader.CreditLimitReader
import creditlimit.control.FileControl.using
import nl.marktplaatz.model
import nl.marktplaatz.model.CreditLimitReport

import scala.io.Codec

class PRNCreditLimitReader(val filePath: String) extends CreditLimitReader {
  implicit val codec: Codec = Codec.ISO8859
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  // this header spec should be provided externally
  val headerSpec: List[HeaderPosition] = List(
    HeaderPosition("Name", 0, 16),
    HeaderPosition("Address", 16, 38),
    HeaderPosition("Post Code", 38, 47),
    HeaderPosition("Phone", 47, 61),
    HeaderPosition("Credit Limit", 61, 74),
    HeaderPosition("Birthday", 74, 82)
  )

  def read: CreditLimitReport = {
    val rows = using(io.Source.fromFile(filePath)) { source =>
      val lines = source.getLines
      lines.take(1).next // advance the iterator to skip header
      lines.map(parseLine(_)).toList
    }

    CreditLimitReport(
      title = s"${fileNameFrom(filePath)} Credit Limit Report",
      headers = headerSpec.map(_.headerName),
      rows = rows
    )
  }

  def parseLine(line: String, headers: List[HeaderPosition] = headerSpec): CreditLimitRow = {
    @scala.annotation.tailrec
    def parse(headers: List[HeaderPosition],
              acc: Array[String] = Array.empty[String]): CreditLimitRow = headers match {
      case Nil => CreditLimitRow(acc(0), acc(1), acc(2), acc(3), acc(4), acc(5))
      case headerPos :: rem => parse(rem, acc :+ line.substring(headerPos.start, headerPos.end).trim())
    }

    parse(headers)
  }


}

