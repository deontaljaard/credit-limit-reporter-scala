package creditlimit

import creditlimit.report.writer.CreditLimitWriterFactory
import creditlimit.report.reader.CreditLimitReaderFactory.{supportedFormats => inFormats}
import CreditLimitWriterFactory.{supportedFormats => outFormats}
import creditlimit.report.reader.CreditLimitReaderFactory
import scopt._

import scala.util.{Failure, Success}

case class Config(input: String = null,
                  output: String = null)

object Main {

  val defaultInFile = "./Workbook2.csv"
  val defaultOutFile = "./Workbook2.html"

  def main(args: Array[String]): Unit = {
    val parser: OptionParser[Config] = new scopt.OptionParser[Config]("BeTestDeonTaljaard") {
      head("scopt", "3.x")
      opt[String]('i', "input") action { (x, c) =>
        c.copy(input = x)
      } text (s"input is the input path - supported formats ${inFormats.mkString("[", ",", "]")}") withFallback (() => defaultInFile)
      opt[String]('o', "output") action { (x, c) =>
        c.copy(output = x)
      } text (s"output is the output path - supported formats ${outFormats.mkString("[", ",", "]")}") withFallback (() => defaultOutFile)
      help("help") text ("prints this usage text")
    }

    parser.parse(args, Config()) map { config =>
      val result = for {
        reader <- CreditLimitReaderFactory(config.input)
        writer <- CreditLimitWriterFactory(config.output)
      } yield writer.write(reader.read)

      result match {
        case Success(()) => println("Done")
        case Failure(ex) => println(ex.getMessage)
      }
    } getOrElse {
      // arguments are bad, usage message will have been displayed
    }
  }
}
