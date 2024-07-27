// Crear una nueva conexión WebSocket
const socket = new WebSocket('ws://localhost:8080'); // Ajusta la URL y el puerto si es necesario

// Manejar la apertura de la conexión
socket.onopen = function(event) {
    console.log('Conexión WebSocket abierta');
};

// Manejar los mensajes recibidos del servidor
socket.onmessage = function(event) {
    console.log('Mensaje recibido del servidor:', event.data);
    // Procesar el mensaje y actualizar la UI
    const estado = parseInt(event.data);
    updateUI(estado);
};

// Manejar errores de conexión
socket.onerror = function(error) {
    console.error('Error en WebSocket:', error);
};

// Manejar el cierre de la conexión
socket.onclose = function(event) {
    console.log('Conexión WebSocket cerrada:', event);
};

// Función para actualizar el estado del `check` y la cuenta regresiva
function updateUI(state) {
    const checkbox = document.getElementById('myCheckbox1');
    const countdownLabel = document.getElementById('countdown1');

    if (state === 1) {
        checkbox.checked = true;
        startCountdown(15 * 60); // 15 minutos en segundos
    } else {
        checkbox.checked = false;
        resetCountdown();
    }
}

// Función para iniciar la cuenta regresiva
function startCountdown(duration) {
    let timer = duration, minutes, seconds;
    const countdownLabel = document.getElementById('countdown1');
    const interval = setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        countdownLabel.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            timer = duration;
            // Enviar mensaje al servidor para actualizar el estado si es necesario
            socket.send('estado=0'); // Ejemplo de cómo enviar un mensaje al servidor
        }
    }, 1000);

    // Guardar la referencia al intervalo para que se pueda limpiar si es necesario
    countdownLabel.dataset.interval = interval;
}

// Función para reiniciar la cuenta regresiva
function resetCountdown() {
    const countdownLabel = document.getElementById('countdown1');
    countdownLabel.textContent = '15:00'; // Reestablecer a 15 minutos

    // Limpiar el intervalo de cuenta regresiva si existe
    if (countdownLabel.dataset.interval) {
        clearInterval(countdownLabel.dataset.interval);
        delete countdownLabel.dataset.interval;
    }
}
