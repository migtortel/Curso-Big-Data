package com.sparkbyexamples.spark.dataframe

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
object UDFDataFrame {
  def main(args:Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()

    val data = Seq(("2018/01/23",23),("2018/01/24",24),("2018/02/25",25))

    import spark.sqlContext.implicits._
    val df = data.toDF("date1","day")
    // df es un data frame, en este caso son tuplas de String,int
    val replace: String => String = _.replace("/","-") // se sustituye la barra por el guion porque sql soporta fechas con guiones
    import org.apache.spark.sql.functions.udf
    val replaceUDF = udf(replace)
    val minDate = df.agg(min($"date1")).collect()(0).get(0) //usa la funcion de agregado min() de sql
    // toma como referencia el valor de date1 del dataframe, el metodo collect() convierte el resultado en un array y
    // y accede al primer elemento, el get(0) guarda el valor en minDate

    val df2 = df.select("*").filter( to_date(replaceUDF($"date1")) > date_add(to_date(replaceUDF(lit(minDate))),7 ))
    // select todos los registros, filtra(pasamos el valor de date1 a fecha > suma a minDate 7 dias)
    df2.show()
  }


}
