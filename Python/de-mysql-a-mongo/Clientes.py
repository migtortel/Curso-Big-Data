import pymysql
import json

class Clientes:
    def __init__(self, conexion):
        self.conexion = conexion

    def crear_tabla(self):
        create_clientes = """
        CREATE TABLE IF NOT EXISTS clientes (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(100),
            direccion VARCHAR(100),
            telefono VARCHAR(15)
        );
        """
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(create_clientes)
            self.conexion.commit()
            print("Tabla 'clientes' creada o ya existía.")
        except pymysql.Error as err:
            print(f"Error al crear la tabla clientes: {err}")

    def drop_tabla(self):
        drop_clientes = "DROP TABLE IF EXISTS clientes;"
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(drop_clientes)
            self.conexion.commit()
            print("Tabla 'clientes' eliminada.")
        except pymysql.Error as err:
            print(f"Error al eliminar la tabla clientes: {err}")

    def insertar_cliente(self, nombre, direccion, telefono):
        sql = "INSERT INTO clientes (nombre, direccion, telefono) VALUES (%s, %s, %s)"
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(sql, (nombre, direccion, telefono))
            self.conexion.commit()
            print(f"Cliente '{nombre}' insertado correctamente.")
        except pymysql.Error as err:
            print(f"Error al insertar cliente: {err}")

    def listar_clientes(self):
        sql = "SELECT * FROM clientes"
        try:
            with self.conexion.cursor() as cursor:
                cursor.execute(sql)
                for row in cursor.fetchall():
                    print(row)
        except pymysql.Error as err:
            print(f"Error al listar clientes: {err}")

    def convertir_a_json(self, tabla):
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