package creditlimit.report.writer

import creditlimit.report.writer.html.HTMLCreditLimitWriter
import org.scalatest.funsuite.AnyFunSuite

class CreditLimitWriterFactoryTest extends AnyFunSuite {

  test("A CreditLimitWriterFactory should return an HTML credit limit writer with an HTML file") {
    val writer = CreditLimitWriterFactory("dummy.html")
    assert(writer.get.isInstanceOf[HTMLCreditLimitWriter])
  }

  test("A CreditLimitWriterFactory should return a failure if the output file is not supported") {
    val writer = CreditLimitWriterFactory("dummy.json")
    assert(writer.isFailure)
  }

}
