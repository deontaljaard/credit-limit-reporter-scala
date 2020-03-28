package creditlimit.report.reader.csv

import java.nio.charset.CodingErrorAction

import creditlimit.report.reader.CreditLimitReader
import creditlimit.control.FileControl.using
import creditlimit.model.{CreditLimitReport, CreditLimitRow}
import nl.marktplaatz.exceptions.MalformedCSVException
import nl.marktplaatz.model
import nl.marktplaatz.model.CreditLimitReport

import scala.io.Codec

class CSVCreditLimitReader(val filePath: String, val delimiter: Char = ',') extends CreditLimitReader {
  implicit val codec: Codec = Codec.ISO8859
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)
  private val quotePattern = "^\\s*\"((?:[^\"]|(?:\"\"))*?)\"\\s*,".r

  def read: CreditLimitReport = {
    val (headers: List[String], rows: List[CreditLimitRow]) = using(io.Source.fromFile(filePath)) { source =>
      val lines = source.getLines
      val headers = lines.take(1).next.split(",").toList
      val rows = lines.map(parseLine).toList
      (headers, rows)
    }

    CreditLimitReport(
      title = s"${fileNameFrom(filePath)} Credit Limit Report",
      headers = headers,
      rows = rows
    )
  }

  def parseLine(line: String): CreditLimitRow = {
    @scala.annotation.tailrec
    def parse(lineRemaining: String,
              acc: Array[String] = Array.empty[String]): CreditLimitRow = {
      if (lineRemaining.isEmpty) CreditLimitRow(acc(0), acc(1), acc(2), acc(3), acc(4), acc(5))
      else {
        if (lineRemaining.contains("\"")) {
          val quoteBlock = quotePattern.findFirstIn(lineRemaining)
          if (quoteBlock.isDefined) {
            val cellValue = quoteBlock.get
            parse(lineRemaining.substring(cellValue.length), acc :+ cellValue.substring(1, cellValue.length - 2))
          } else throw MalformedCSVException("CSV is malformed")
        } else if (lineRemaining.contains(",")) {
          val cIdx = lineRemaining.indexOf(",")
          val cellValue = if (cIdx == 0) "" else lineRemaining.substring(0, cIdx)
          parse(lineRemaining.substring(cIdx + 1), acc :+ cellValue)
        } else {
          parse("", acc :+ lineRemaining)
        }
      }
    }

    parse(line)
  }
}

