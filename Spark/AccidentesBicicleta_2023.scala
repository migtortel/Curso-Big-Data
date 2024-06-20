import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


object AccidentesBicicleta_2023 {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder()
      .appName("Java Spark WordCount basic example")
      .config("spark.master", "local")
      .getOrCreate();

    val df = spark.read
      .format("csv")
      .option("header", "true") // Si el archivo CSV tiene encabezados de columna
      .option("delimiter", ";") // Delimitador del archivo
      .load("AccidentesBicicletas_2023.csv") // Ruta al archivo CSV en el sistema de archivos

    // Contar filas
    val numFilas = df.count()
    println(s"Número de filas: $numFilas")

    // Libreria necesaria para poder usar la expresiones de filtrado
    import spark.implicits._
    val conteo_alcohol = df.filter($"positiva_alcohol" === "S").count()
    println(s"El total de registros con positiva_alcohol = 'S' es: $conteo_alcohol")

    val porcentaje_alcohol = (conteo_alcohol.toDouble / numFilas) * 100 // hay que forzar el tipo de dato a double
    println("El porcentaje de accidentes en los que el conductor dio positivo en alcohol fue: " +porcentaje_alcohol)

  }

}
