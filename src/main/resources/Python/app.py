print("Hola, Conecto a APP.PY ")

from flask import Flask, request, jsonify
import mysql.connector
from mysql.connector import Error

app = Flask(__name__)
# Configuración de la base de datos
def conectar_bd():
    try:
        config = {
            'user': 'root',
            'password': '12345',
            'host': '127.0.0.1',
            'database': 'maerik',
            'port': 3306,
        }

        # Establecer la conexión
        conexion = mysql.connector.connect(**config)

        if conexion.is_connected():
            print('Conexión establecida correctamente.')
            return conexion

    except mysql.connector.Error as error:
        print(f'Error al conectarse a MySQL: {error}')
        return None



def cerrar_bd(conexion):
    # Cerrar la conexión si está abierta
    if conexion and conexion.is_connected():
        conexion.close()
        print('Conexión cerrada.')

def mostrar_datos_rf_id():
    try:
        conexion = conectar_bd()

        if conexion:
            cursor = conexion.cursor()
            cursor.execute("SELECT * FROM usuarios_local")

            # Mostrar los resultados
            for fila in cursor.fetchall():
                print(fila)  # Aquí puedes procesar cada fila como desees

    except mysql.connector.Error as error:
        print(f'Error al ejecutar la consulta DE SALDO : {error}')




# Ejemplo de uso
mostrar_datos_rf_id()



@app.route('/check_uid', methods=['GET'])
def check_uid():
    uid = request.args.get('uid')
    connection = conectar_bd()



    try:
        conexion = conectar_bd()

        if conexion:
            cursor = conexion.cursor()
            cursor.execute('SELECT saldo FROM usuarios_local WHERE rfid = %s', (uid,))

            # Mostrar los resultados
            for fila in cursor.fetchall():

                saldo = fila[0]
                print(saldo)
    except mysql.connector.Error as error:
        print(f'Error al ejecutar la consulta: {error}')

    if connection is None:
         return jsonify({'status': 'error', 'message': 'No se pudo conectar ala base de datos'})
    cursor = connection.cursor()
    cursor.execute('SELECT * FROM usuarios_local WHERE rfid = %s', (uid,))

    result = cursor.fetchone()

    print(f'Codigo recibido y saldo : {uid,saldo}')
    cursor.close()
    print('Conexión cerrada. ')

    connection.close()

    if result:
        if saldo >= 10:
         print('Saldo sufuciente ')
         return jsonify({'status': 'registered'})
        else:
         print('Saldo sufuciente ')
         return jsonify({'status': 'not registered'})
    else:
        return jsonify({'status': 'not registered'})
if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')



