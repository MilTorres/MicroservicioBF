const socket = new WebSocket('ws://192.168.1.49:5000/ws');

socket.onopen = function(event) {
    console.log('Conectado al WebSocket');
};

socket.onmessage = function(event) {
    const message = event.data;
    console.log('Mensaje recibido: ' + message);

    // Lógica para manejar el mensaje recibido (1 o 0)
    if (message === '1') {
        startCountdown();
    } else {
        stopCountdown();
    }
};

socket.onclose = function(event) {
    console.log('Desconectado del WebSocket');
};

function startCountdown() {
    // Implementa la lógica para iniciar la cuenta regresiva mt
    console.log('Iniciar cuenta regresiva');
}

function stopCountdown() {
    // Implementa la lógica para detener la cuenta regresiva
    console.log('Detener cuenta regresiva');
}
