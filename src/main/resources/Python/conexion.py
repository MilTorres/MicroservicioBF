from flask import Flask, request, jsonify
from flask_socketio import SocketIO
import mysql.connector

app = Flask(__name__)
socketio = SocketIO(app)

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
        conexion = mysql.connector.connect(**config)
        if conexion.is_connected():
            return conexion
    except mysql.connector.Error as error:
        return None

def cerrar_bd(conexion):
    if conexion and conexion.is_connected():
        conexion.close()

@app.route('/check_uid', methods=['GET'])
def check_uid():
    uid = request.args.get('uid')
    connection = conectar_bd()

    if connection is None:
        return jsonify({'status': 'error', 'message': 'No se pudo conectar a la base de datos'})

    cursor = connection.cursor()
    cursor.execute('SELECT saldo FROM usuarios_local WHERE rfid = %s', (uid,))
    result = cursor.fetchone()
    saldo = result[0] if result else 0

    if result:
        if saldo >= 10:
            socketio.emit('update_status', {'status': 'registered'})
            return jsonify({'status': 'registered'})
        else:
            socketio.emit('update_status', {'status': 'not_registered'})
            return jsonify({'status': 'not_registered'})
    else:
        socketio.emit('update_status', {'status': 'not_registered'})
        return jsonify({'status': 'not_registered'})

if __name__ == '__main__':
    socketio.run(app, debug=True, host='0.0.0.0')
