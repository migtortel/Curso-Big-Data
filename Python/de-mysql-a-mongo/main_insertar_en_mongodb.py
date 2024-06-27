import pymongo
import json

# Conectar a MongoDB
client = pymongo.MongoClient("mongodb://localhost:27017/")
db = client["empresa"]
clientes_col = db["clientes"]

# Cargar datos de clientes desde el archivo JSON
with open('clientes.json') as file:
    clientes_data = json.load(file)

# Cargar datos de ventas desde el archivo JSON
with open('ventas.json') as file:
    ventas_data = json.load(file)

# Insertar clientes en la colección clientes
clientes_col.insert_many(clientes_data)

# Iterar sobre los datos de ventas y agregarlos a cada cliente correspondiente
for venta in ventas_data:
    cliente_id = venta["cliente_id"]
    cliente = clientes_col.find_one({"_id": cliente_id})
    if cliente:
        if "ventas" in cliente:
            cliente["ventas"].append(venta)
        else:
            cliente["ventas"] = [venta]
        clientes_col.update_one({"_id": cliente_id}, {"$set": cliente})

print("Datos insertados correctamente.")
