package creditlimit.report.writer.html

import java.io.{BufferedWriter, File, FileWriter}

import creditlimit.report.writer.CreditLimitWriter
import creditlimit.control.FileControl.using
import creditlimit.model.CreditLimitReport
import scalatags.Text
import scalatags.Text.all._

class HTMLCreditLimitWriter(val filePath: String) extends CreditLimitWriter {

  override def write(creditLimitReport: CreditLimitReport): Unit = {
    val report = buildHtml(creditLimitReport)
    using(new BufferedWriter(new FileWriter(new File(filePath)))) { source =>
      source.write(report.toString)
    }
  }

  def buildHtml(report: CreditLimitReport): Text.TypedTag[String] = {
    html(
      head(
        meta(charset := "utf-8"),
        meta(name := "viewport", content := "width=device-width, initial-scale=1, shrink-to-fit=no"),
        link(
          rel := "stylesheet",
          href := "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css",
          attr("integrity") := "sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm",
          attr("crossorigin") := "anonymous"
        ),
        title := report.title
      ),
      body(
        div(`class` := "container")(
          br,
          h1(report.title),
          div(`class` := "row")(
            table(`class` := "table table-bordered")(
              thead(`class` := "thead-light")(
                tr(
                  th(attr("scope") := "col")("#"),
                  for (header <- report.headers) yield th(attr("scope") := "col")(header)
                )
              ),
              tbody(
                for ((creditLimitRow, idx) <- report.rows.zipWithIndex) yield tr(
                  th(attr("scope") := "row")(idx + 1),
                  td(creditLimitRow.name),
                  td(creditLimitRow.address),
                  td(creditLimitRow.postCode),
                  td(creditLimitRow.phone),
                  td(creditLimitRow.creditLimit),
                  td(creditLimitRow.birthday)
                )
              )
            )
          )
        ),
      )
    )
  }
}
