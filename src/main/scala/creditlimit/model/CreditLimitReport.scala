package creditlimit.model

case class CreditLimitReport(title: String,
                             headers: List[String],
                             rows: List[CreditLimitRow])
