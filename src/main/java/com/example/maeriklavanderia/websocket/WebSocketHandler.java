package com.example.maeriklavanderia.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=bd_maerik";
    private static final String USER = "sa";
    private static final String PASS = "1234";

    private Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("Cliente conectado: " + session.getId());
        broadcastStatus();
        System.out.println("le valio: " + session.getId());

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Manejo de mensajes recibidos del cliente, si es necesario
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("Cliente desconectado: " + session.getId());
    }

    public void broadcastStatus() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT estado FROM lavadoras")) {
            System.out.println("dentro de la consulta sql de websocket -----: "+resultSet);

            if (resultSet.next()) {
                int estado = resultSet.getInt("estado");
                TextMessage message = new TextMessage(String.valueOf(estado));
                System.out.println("dentro de la consulta sql de websocket estado= -----: "+estado);
                for (WebSocketSession s : sessions.values()) {
                    if (s.isOpen()) {
                        s.sendMessage(message);
                        System.out.println("mensaje --"+s);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error en la consulta sql de websocket");
        }
    }
}
