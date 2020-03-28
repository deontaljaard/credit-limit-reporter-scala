# credit-limit-reporter

## Instructions

In this repo, you'll find two files, Workbook2.csv and Workbook2.prn. These files need to be converted to a HTML 
format by the code you deliver. Please consider your work as a proof of concept for a system that can keep track of 
credit limits from several sources.

### Run

#### Tests
You can run the tests of for the project running the following in the project root 
```bash
sbt test
```
#### Usage
To get a usage instructions, run
```bash
sbt "run --help"
```
This will display the following
```bash
scopt 3.x
Usage: BeTestDeonTaljaard [options]

  -i, --input <value>   input is the input path - supported formats [.csv,.prn]
  -o, --output <value>  output is the output path - supported formats [.html]
  --help                prints this usage text
```
Example usage
```bash
sbt "run -i ./Workbook2.prn -o ./Workbook2.html"
```

### Write up
##### Readers
The project currently supports two readers for file formats, `.csv` and `.prn`.

###### CSV
The [CSV reader](src/main/scala/creditlimit/report/reader/csv/CSVCreditLimitReader.scala) is functional and can handle files given the 
provided format for this assessment. CSV files with more complex encodings and formatting, will require that the current 
CSV report reader to be adapted. However, it's probably a better idea to use a battle-tested library such this 
[Scala CSV Reader](https://github.com/tototoshi/scala-csv), or making use of Apache Spark to read a CSV into a Data Frame. 

###### PRN
The [PRN reader](src/main/scala/creditlimit/report/reader/prn/PRNCreditLimitReader.scala) is functional and can handle files given the 
provided format for this assessment. The PRN reader implementation assumes upfront knowledge of the headers in the file
and their start and end position to extract their cell values.

##### Writers
The project currently supports two writers for file formats, `.html`.

###### HTML 
I thought I'd initially write a generator that uses a .html [template file](./CreditLimitTemplate.html) and 
inserts/substitutes the credit limit data. However, I decided to use [ScalaTags](http://www.lihaoyi.com/scalatags/), 
which is more robust than what I could've come up with in the limited time and to make it easier to test. Here is the 
[HTML report writer](src/main/scala/creditlimit/report/writer/html/HTMLCreditLimitWriter.scala).

##### Room for Improvement
Although functional, the project can improve in with regard to the following (non-exhaustive):
* PRN Reader: accepting header specifications externally instead of having it hard-coded
* CSV Reader: additional support for special characters
* Allow options to be passed to the program that specify formats for dates and monetary fields
* Additional readers for different input formats, like JSON, XML, or Parquet for example
* Additional writers for different output formats, like JSON, Parquet, or Excel for example