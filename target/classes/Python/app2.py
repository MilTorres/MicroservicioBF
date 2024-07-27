from flask import Flask, request, jsonify
import mysql.connector
from mysql.connector import Error

app = Flask(__name__)

# Configuración de la base de datos
db_config = {
    'user': 'root',
    'password': '1234',
    'host': '127.0.0.1',
    'database': 'maerik',
    'port': 3308,
}

def get_db_connection():
    try:
        connection = mysql.connector.connect(**db_config)
        if connection.is_connected():
            print("Conexión exitosa a la base de datos")
            return connection
    except Error as e:
        print(f"Error al conectar a la base de datos: {e}")
        return None

@app.route('/check_uid', methods=['GET'])
def check_uid():
    uid = request.args.get('uid')
    connection = get_db_connection()
    if connection is None:
        return jsonify({'status': 'error', 'message': 'No se pudo conectar a la base de datos'})

    cursor = connection.cursor()
    cursor.execute('SELECT * FROM rfid_cards WHERE uid = %s', (uid,))
    result = cursor.fetchone()
    cursor.close()
    connection.close()

    if result:
        return jsonify({'status': 'registered'})
    else:
        return jsonify({'status': 'not registered'})

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
