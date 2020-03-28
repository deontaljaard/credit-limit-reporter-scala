package creditlimit.report

import creditlimit.model.{CreditLimitReport, CreditLimitRow}
import nl.marktplaatz.model.CreditLimitReport

trait CreditLimitReportFixture {

  val csvCreditLimitRow: CreditLimitRow = CreditLimitRow(
    name = "Smith, John",
    address = "Børkestraße 32",
    postCode = "87823",
    phone = "+44 728 889838",
    creditLimit = "9898.3",
    birthday = "20/09/1999"
  )

  val prnCreditLimitRow: CreditLimitRow = csvCreditLimitRow.copy(creditLimit = "989830", birthday = "19990920")

  val csvCreditLimitReport: CreditLimitReport = CreditLimitReport(
    title = "Test Report",
    headers = List("Name", "Address", "Post Code", "Phone", "Credit Limit", "Birthday"),
    rows = List(csvCreditLimitRow)
  )

  val prnCreditLimitReport: CreditLimitReport = csvCreditLimitReport.copy(rows = List(prnCreditLimitRow))
}
