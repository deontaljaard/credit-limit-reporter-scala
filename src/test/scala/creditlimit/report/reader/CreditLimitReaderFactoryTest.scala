package creditlimit.report.reader

import creditlimit.report.reader.csv.CSVCreditLimitReader
import creditlimit.report.reader.prn.PRNCreditLimitReader
import org.scalatest.funsuite.AnyFunSuite

class CreditLimitReaderFactoryTest extends AnyFunSuite {

  test("A CreditLimitReaderFactory should return a CSV credit limit reader with a CSV file") {
    val reader = CreditLimitReaderFactory("dummy.csv")
    assert(reader.get.isInstanceOf[CSVCreditLimitReader])
  }

  test("A CreditLimitReaderFactory should return a PRN credit limit reader with a PRN file") {
    val reader = CreditLimitReaderFactory("dummy.prn")
    assert(reader.get.isInstanceOf[PRNCreditLimitReader])
  }

  test("A CreditLimitReaderFactory should return a failure if the input file is not supported") {
    val reader = CreditLimitReaderFactory("dummy.json")
    assert(reader.isFailure)
  }
}
