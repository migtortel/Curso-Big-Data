import pymysql
from Clientes import Clientes
from Ventas import Ventas

def main():
    # Crear una conexión a MySQL
    conexion = pymysql.connect(host='localhost',
                               user='root',
                               password='root',
                               database='empresa',
                               cursorclass=pymysql.cursors.DictCursor)

    # Crear instancias de las clases Clientes y Ventas
    clientes = Clientes(conexion)
    ventas = Ventas(conexion)

    # Borrar tablas
   # ventas.drop_tabla()
    clientes.drop_tabla()

    # Crear las tablas
    clientes.crear_tabla()
    ventas.crear_tabla()

    print("\n")
    # Insertar algunos datos
    clientes.insertar_cliente("Juan Perez", "Calle Principal 123", "123-456-7890")
    clientes.insertar_cliente("Alicia Gomez", "Avenida Secundaria 456", "987-654-3210")
    print("\n")
    ventas.insertar_venta(1, "Producto A", 2, 19.99, "2024-06-27")
    ventas.insertar_venta(2, "Producto B", 1, 9.99, "2024-06-27")
    ventas.insertar_venta(1, "Producto C", 5, 59.99, "2024-06-28")

    # Listar clientes y ventas
    print("\nClientes:")
    clientes.listar_clientes()

    print("\nVentas:")
    ventas.listar_ventas()

    clientes.convertir_a_json('clientes')
    ventas.convertir_a_json('ventas')

    # Cerrar la conexión
    conexion.close()

if __name__ == "__main__":
    main()
