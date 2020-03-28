package creditlimit.report.reader.csv

import creditlimit.report.CreditLimitReportFixture
import nl.marktplaatz.exceptions.MalformedCSVException
import org.scalatest.funsuite.AnyFunSuite

class CSVCreditLimitReaderTest extends AnyFunSuite
  with CreditLimitReportFixture {

  test("A CSV credit limit report reader should be able to read a CSV file") {
    val creditLimitReport = new CSVCreditLimitReader("./Workbook2.csv").read
    assert(creditLimitReport.title.nonEmpty)
    assert(creditLimitReport.headers.nonEmpty)
    assert(creditLimitReport.rows.length == 7)
  }

  test("A CSV credit limit report reader should be able to parse a delimited line with quotes") {
    val reader = new CSVCreditLimitReader("dummyFile.csv")
    val line = "\"Smith, John\",Børkestraße 32,87823,+44 728 889838,9898.3,20/09/1999"
    val parsedLine = reader.parseLine(line)
    assert(parsedLine == csvCreditLimitRow)
  }

  test("A CSV credit limit report reader should be able to parse a delimited line with empty cells") {
    val reader = new CSVCreditLimitReader("dummyFile.csv")
    val line = "\"Smith, John\",Børkestraße 32,87823,,9898.3,20/09/1999" // empty phone cell
    val parsedLine = reader.parseLine(line)
    assert(parsedLine == csvCreditLimitRow.copy(phone = ""))
  }

  test(s"A CSV credit limit report reader should handle imbalanced quotes") {
    val line = "\"Smith, John,Børkestraße 32,87823,+44 728 889838,9898.3,20/09/1999"
    val thrown = intercept[MalformedCSVException] {
      val reader = new CSVCreditLimitReader("dummyFile")
      reader.parseLine(line)
    }
    assert(thrown.getMessage === "CSV is malformed")
  }
}
