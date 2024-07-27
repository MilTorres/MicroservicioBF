console.log("entra a chec.js ")
validateRFID(rfid);


async function validateRFID(rfid) {
    try {
        const response = await fetch(`/check_uid/${rfid}`);
        const data = await response.json();

        if (data.status === "registered") {
            console.log("Conecta con clase CountdownTimer.js");
            activateCheckbox(); // Llama a la función para activar el checkbox
        } else {
            console.log("RFID no registrado");
        }
    } catch (error) {
        console.error("Error al validar RFID:", error);
    }
}

function activateCheckbox() {
    // Lógica para activar el checkbox
    const checkbox = document.getElementById('checkbox1'); // Cambia el ID según el checkbox que quieras activar
    if (checkbox) {
        checkbox.checked = true;
    } else {
        console.log('No se encontró el checkbox con el ID: checkbox1');
    }
}


document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('checkbox1').addEventListener('click', function(e) {
        if (this.checked) {
            let scannedCode = "yourRFIDcode"; // Aquí deberías obtener el código RFID que necesitas validar
            validateRFID(scannedCode); // Validar el RFID
        }
    });
});

async function validateRFID(rfid) {
    try {
        const response = await fetch(`/check_uid/${rfid}`);
        const data = await response.json();

        if (data.status === "registered") {
            console.log("Conecta con clase CountdownTimer.js");
            startCountdown(); // Llama a la función para iniciar la cuenta regresiva
        } else {
            console.log("RFID no registrado");
            document.getElementById('checkbox1').checked = false; // Desmarcar el checkbox si no está registrado
        }
    } catch (error) {
        console.error("Error al validar RFID:", error);
        document.getElementById('checkbox1').checked = false; // Desmarcar el checkbox en caso de error
    }
}

function startCountdown() {
    // Aquí va tu lógica para iniciar la cuenta regresiva
    console.log("Iniciando cuenta regresiva...");
    // Puedes llamar a cualquier función que tengas definida para manejar el CountdownTimer.js
}
