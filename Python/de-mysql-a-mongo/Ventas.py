import pymysql
import json

class Ventas:
    def __init__(self, conexion):
        self.conexion = conexion

    def crear_tabla(self):
        create_ventas = """
        CREATE TABLE IF NOT EXISTS ventas (
            id INT AUTO_INCREMENT PRIMARY KEY,
            cliente_id INT,
            producto VARCHAR(100),
            cantidad INT,
            precio DOUBLE(10, 2),
            fecha VARCHAR(15),
            FOREIGN KEY (cliente_id) REFERENCES clientes(id)
        );
        """
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(create_ventas)
            self.conexion.commit()
            print("Tabla 'ventas' creada o ya existía.")
        except pymysql.Error as err:
            print(f"Error al crear la tabla ventas: {err}")

    def drop_tabla(self):
        drop_ventas = "DROP TABLE IF EXISTS ventas;"
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(drop_ventas)
            self.conexion.commit()
            print("Tabla 'ventas' eliminada.")
        except pymysql.Error as err:
            print(f"Error al eliminar la tabla ventas: {err}")

    def insertar_venta(self, cliente_id, producto, cantidad, precio, fecha):
        sql = "INSERT INTO ventas (cliente_id, producto, cantidad, precio, fecha) VALUES (%s, %s, %s, %s, %s)"
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(sql, (cliente_id, producto, cantidad, precio, fecha))
            self.conexion.commit()
            print(f"Venta de '{producto}' insertada correctamente.")
        except pymysql.Error as err:
            print(f"Error al insertar venta: {err}")

    def listar_ventas(self):
        sql = "SELECT * FROM ventas"
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(sql)
                for row in cursor.fetchall():
                    print(row)
        except pymysql.Error as err:
            print(f"Error al listar ventas: {err}")

    def convertir_a_json(self,tabla):
        try:
            with self.conexion.cursor() as cursor:
                # Ejecutar consulta SQL
                sql = f"SELECT * FROM {tabla}"
                cursor.execute(sql)
                resultados = cursor.fetchall()

                # Escribir resultados a un archivo JSON
                with open(f'{tabla}.json', 'w', encoding='utf-8') as json_file:
                    json.dump(resultados, json_file, ensure_ascii=False, indent=4)

                print(f'Archivo {tabla}.json creado exitosamente.')

        except Exception as e:
            print(f'Error al consultar y convertir a JSON: {e}')

