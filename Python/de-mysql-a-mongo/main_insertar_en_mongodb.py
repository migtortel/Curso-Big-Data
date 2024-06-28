import pymongo
import json

# Conectar a MongoDB
client = pymongo.MongoClient("mongodb://localhost:27017/")
db = client.empresa #["empresa"]
clientes_col = db["clientes"]

# Cargar datos de clientes desde el archivo JSON
with open('clientes.json') as file:
    clientes_data = json.load(file)

# Cargar datos de ventas desde el archivo JSON
with open('ventas.json') as file:
    ventas_data = json.load(file)

# Crear un diccionario para agrupar ventas por cliente_id
ventas_por_cliente = {}
for venta in ventas_data:
    cliente_id = venta["cliente_id"]
    if cliente_id not in ventas_por_cliente:
        ventas_por_cliente[cliente_id] = []
    ventas_por_cliente[cliente_id].append(venta)

# Insertar clientes en la colección clientes con las ventas anidadas
for cliente in clientes_data:
    cliente_id = cliente["id"]
    cliente["ventas"] = ventas_por_cliente.get(cliente_id, [])
    clientes_col.insert_one(cliente)

print("Datos insertados correctamente.")

# Obtener todos los clientes con sus ventas
cursor = clientes_col.find()
for cliente in cursor:
    print(f"Cliente: {cliente['nombre']}")
    if "ventas" in cliente:
        for venta in cliente['ventas']:
            print(f"  Venta: Producto {venta['producto']} - Cantidad {venta['cantidad']} - Fecha {venta['fecha']}")
