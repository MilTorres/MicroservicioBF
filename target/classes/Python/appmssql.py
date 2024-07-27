from flask import Flask, request, jsonify
import pyodbc


app = Flask(__name__)
# Configuración de la conexión a SQL Server
server = 'localhost'
database = 'bd_maerik'
username = 'sa'
password = '12345'

# Cadena de conexión
conn_str = (
    r'DRIVER={SQL Server};'
    rf'SERVER={server};'
    rf'DATABASE={database};'
    rf'UID={username};'
    rf'PWD={password};'
    r'Trusted_Connection=no;'
)

def connect_and_query():
    try:
        # Conectar a SQL Server
        conn = pyodbc.connect(conn_str)
        print('Conexión establecida.')

        # Crear un cursor
        cursor = conn.cursor()

        # Ejecutar una consulta
        cursor.execute('SELECT * FROM usuarios_local')

        # Mostrar resultados
        for row in cursor:
            print(row)

    except Exception as e:
        print(f'Error al conectar o ejecutar consulta: {e}')

    finally:
        # Cerrar la conexión
        if 'conn' in locals():
            conn.close()
            print('Conexión cerrada.')

# Llamar a la función para conectar y ejecutar la consulta
connect_and_query()



@app.route('/check_uid', methods=['GET'])
def check_uid():
    uid = request.args.get('uid')
    connection = connect_and_query()
    if connection is None:
        return jsonify({'status': 'error', 'message': 'No se pudo conectar ala base de datos'})
    cursor = connection.cursor()
    cursor.execute('SELECT * FROM usuarios_local WHERE uid = %s', (uid,))
    result = cursor.fetchone()
    cursor.close()
    connection.close()

    if result:
        return jsonify({'status': 'registered'})
    else:
        return jsonify({'status': 'not registered'})
if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
