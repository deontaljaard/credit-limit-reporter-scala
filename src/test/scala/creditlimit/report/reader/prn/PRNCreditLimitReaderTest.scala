package creditlimit.report.reader.prn

import creditlimit.report.CreditLimitReportFixture
import org.scalatest.funsuite.AnyFunSuite

class PRNCreditLimitReaderTest extends AnyFunSuite
  with CreditLimitReportFixture {

  test("A PRN credit limit report reader should be able to read a PRN file") {
    val creditLimitReport = new PRNCreditLimitReader("./Workbook2.prn").read
    assert(creditLimitReport.title.nonEmpty)
    assert(creditLimitReport.headers.nonEmpty)
    assert(creditLimitReport.rows.length == 7)
  }

  test("A PRN credit limit report reader should be able to parse a space delimited line") {
    val reader = new PRNCreditLimitReader("dummyFile.prn")
    val line = "Smith, John     Børkestraße 32        87823    +44 728 889838      989830 19990920"
    val parsedLine = reader.parseLine(line)
    assert(parsedLine == prnCreditLimitRow)
  }

  test("A PRN credit limit report reader should be able to parse a delimited line with empty cells") {
    val reader = new PRNCreditLimitReader("dummyFile.prn")
    val line = "Smith, John     Børkestraße 32        87823                        989830 19990920" // empty phone cell
    val parsedLine = reader.parseLine(line)
    assert(parsedLine == prnCreditLimitRow.copy(phone = ""))
  }
}
