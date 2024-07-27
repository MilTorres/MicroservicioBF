package com.example.maeriklavanderia.websocket;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WebSocketServerExample extends WebSocketServer {

    private Connection connection;

    public WebSocketServerExample(int port) {
        super(new InetSocketAddress(port));
        connectToDatabase();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Cliente conectado: " + conn.getRemoteSocketAddress());
        conn.send("Bienvenido al servidor WebSocket");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Cliente desconectado: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Mensaje recibido del cliente: " + message);
        // Aquí podrías manejar el mensaje y actualizar la base de datos si es necesario
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Servidor WebSocket iniciado en el puerto " + this.getPort());
        // Configurar la consulta periódica
        new Thread(() -> {
            while (true) {
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT estado FROM lavadoras");
                    if (resultSet.next()) {
                        int estado = resultSet.getInt("estado");
                        broadcast(String.valueOf(estado)); // Enviar el estado a todos los clientes
                    }
                    Thread.sleep(5000); // Verificar cada 5 segundos
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://tu_servidor;databaseName=tu_base_de_datos", "tu_usuario", "tu_contraseña");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebSocketServerExample server = new WebSocketServerExample(8080); // Usa el puerto que prefieras
        server.start();
    }
}
