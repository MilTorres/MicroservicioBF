console.log("entra a login.js");

const form = document.querySelector('form');
const userNameInput = document.getElementById('usuario');
const passwordInput = document.getElementById('contraseña');
const errorMessage = document.createElement('p'); // Para mostrar mensajes de error
errorMessage.style.color = 'red'; // Estilo del mensaje de error


console.error('usuario y contraseña:', userNameInput,passwordInput);
// Función para enviar datos al endpoint
async function loginUser(email, password) {
    try {
        const response = await fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Error en la respuesta de la red');
        }

        const data = await response.json();
        console.log('Login exitoso:', data);
        window.location.href = './control.html';

    } catch (error) {
        console.error('Error en el login:', error);
        const form = document.getElementById('myForm');

        const inputField = document.getElementById('inputField').value.trim();

        // Verifica si el campo está vacío y muestra una alerta
        if (!inputField) {
            alert('El usuario o contraseña son incorrectos. \n Por favor, ingréselos nuevamente.');
        } else {
            // Aquí puedes agregar la lógica para procesar el formulario si los datos son correctos
            console.log('Formulario enviado con:', inputField);
            // Puedes limpiar el campo o hacer otras acciones aquí
        }
        window.location.reload();
        errorMessage.textContent = 'Error en la solicitud. Inténtalo de nuevo más tarde.';
        form.appendChild(errorMessage);
    }
}
// Maneja el envío del formulario
form.addEventListener('submit', async (event) => {
    event.preventDefault(); // Evita el envío del formulario por defecto

    // Obtén los valores del formulario
    const userName = userNameInput.value.trim();
    const password = passwordInput.value.trim();

    // Verifica que los campos no estén vacíos
    if (!userName || !password) {
        // Elimina mensaje de error previo, si existe
        if (form.contains(errorMessage)) {
            form.removeChild(errorMessage);
        }
        errorMessage.textContent = 'Por favor, ingrese nombre de usuario y contraseña.';
        form.appendChild(errorMessage);
        return;
    }

    // Llamar a la función para enviar los datos
    loginUser(userName, password);
});
