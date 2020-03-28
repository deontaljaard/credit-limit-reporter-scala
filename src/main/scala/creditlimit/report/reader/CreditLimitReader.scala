package creditlimit.report.reader

import creditlimit.model.CreditLimitReport

trait CreditLimitReader {
  def filePath: String

  def read: CreditLimitReport

  def fileNameFrom(filePath: String): String = filePath.substring(filePath.lastIndexOf("/") + 1)
}
