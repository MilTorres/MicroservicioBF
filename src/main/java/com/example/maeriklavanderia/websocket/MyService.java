package com.example.maeriklavanderia.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    private MyWebSocketHandler webSocketHandler;

    public void updateState(String newState) {
        // Lógica para actualizar el estado en la base de datos
        // ...

        // Enviar el nuevo estado a los clientes conectados
        webSocketHandler.sendMessageToClients(newState);
    }

    // Método para enviar actualizaciones periódicas desde la base de datos (opcional)
    @Scheduled(fixedRate = 5000)
    public void checkDatabaseAndSendUpdates() {
        // Lógica para comprobar la base de datos
        String newState = checkDatabaseState();

        // Enviar el nuevo estado a los clientes
        webSocketHandler.sendMessageToClients(newState);
    }

    private String checkDatabaseState() {
        // Aquí implementa la lógica para consultar la base de datos y devolver el estado (1 o 0)
        return "1"; // Ejemplo de retorno
    }
}
