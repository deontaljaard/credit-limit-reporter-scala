package creditlimit.report.writer

import creditlimit.model.CreditLimitReport

trait CreditLimitWriter {
  def filePath: String

  def write(creditLimitReport: CreditLimitReport): Unit
}
