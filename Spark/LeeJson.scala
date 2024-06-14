import org.apache.spark.sql.{SQLContext, SparkSession}

object LeeJson extends App {
  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("SparkByExamples.com")
    .getOrCreate();

  spark.sparkContext.setLogLevel("ERROR")


  val sqlContext:SQLContext = spark.sqlContext

  // lee el json
  val df = sqlContext.read.options(Map("inferSchema"->"true","delimiter"->",","header"->"true")).json("zipcodes.json")
  df.show()
  df.printSchema()

  df.createOrReplaceTempView("TAB")
  sqlContext.sql("select * from TAB")
    .show(false)
  scala.io.StdIn.readLine()

}
