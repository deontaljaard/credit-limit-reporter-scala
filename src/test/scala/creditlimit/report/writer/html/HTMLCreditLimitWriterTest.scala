package creditlimit.report.writer.html

import creditlimit.report.CreditLimitReportFixture
import org.scalatest.funsuite.AnyFunSuite

class HTMLCreditLimitWriterTest extends AnyFunSuite with CreditLimitReportFixture {

  test("An HTML credit limit report writer should process a credit limit report") {
    val writer = new HTMLCreditLimitWriter("dummyFile.html")

    val report = writer.buildHtml(csvCreditLimitReport).toString
    assert(report.contains("Name"))
    assert(report.contains("Address"))
    assert(report.contains("Post Code"))
    assert(report.contains("Phone"))
    assert(report.contains("Credit Limit"))
    assert(report.contains("Birthday"))

    for(creditLimitRow <- csvCreditLimitReport.rows) {
      assert(report.contains(creditLimitRow.name))
      assert(report.contains(creditLimitRow.address))
      assert(report.contains(creditLimitRow.postCode))
      assert(report.contains(creditLimitRow.phone))
      assert(report.contains(creditLimitRow.creditLimit))
      assert(report.contains(creditLimitRow.birthday))
    }
  }


}
