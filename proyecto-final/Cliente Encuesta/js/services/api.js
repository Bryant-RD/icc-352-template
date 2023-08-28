import {deleteLocaleStorage, getJWT, registroLogin, registroLogout } from "./storageController.js";

const IP = "http://127.0.0.1:8080"

export const getServerRegistros = async () => {
    try {
        const response = await fetch(IP+"/registros")
        const data = await response.json();

        return data;
    } catch (error) {
        console.log(error);
        return [];
    }
}


export const sincronizarRegistros = async (registros) => {
    try {
        const response = await fetch(IP+"/sincronizar", {
            method: "POST",
            body: JSON.stringify(registros),
            headers: {
                'Content-type': 'application/json'
            }
        });
        if (response.ok) {
            const data = await response.json();
            //TODO: MANEJO DE SEGUNDO ARRAY LOCAL PARA TENER TODOS LOS REGISTROS

            return data;
          } else {
            throw new Error('Error en la solicitud');
          }

    } catch (error) {
        console.log(error);
    }
}



// Función para iniciar sesión
export const login = (username, password) => {

    if (getJWT()) {
        registroLogin(username);
        window.location.href = "home.html"
        return;
    }
  fetch(IP+"/login", {
      method: "POST",
      headers: {
          "Content-Type": "application/x-www-form-urlencoded",
      },
      body: `username=${username}&password=${password}`,
  })
      .then(async (response) => {
          if (response.ok) {
            const data = await response.text();

            if (data != "false") {
                return data;
            } else {
                alert("El usuario o la contraseña son incorrectos");
                window.location.href = "index.html"
            }

          } else {
              throw new Error("Inicio de sesión fallido");
          }
      })
      .then((jwt) => {


        if (jwt != "false" && jwt != undefined) {
            localStorage.setItem('jwt', jwt);
            registroLogin(username);
            console.log(jwt);
            window.location.href = "home.html"
        }

      })
      .catch((error) => {
          console.error(error);
          alert("Error de conexion y no hay sesion vigente")
          window.location.href = "index.html"
          // Mostrar un mensaje de error al usuario
      });
}


export const LogOut = async () => {

    if (getJWT()) {
        deleteLocaleStorage("jwt")
        window.location.href = "index.html"
        registroLogout()
        return;
    }
   
    fetch(IP+'/logout', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
        if (response.ok) {
          // Cierre de sesión exitoso, redirigir a la página de inicio de sesión

          deleteLocaleStorage();
          registroLogout();
          window.location.href = '/index.html';
        } else {
          // Ocurrió un error al cerrar sesión
          console.log('Error al cerrar sesión');
        }
      })
      .catch(error => {
        console.log('Error al cerrar sesión:', error);
      });
}


export const checkInternetConnection = () => {
    const url = 'www.google.com.do'; // Puedes reemplazarlo con la URL de tu servidor o cualquier otra página
  
    fetch(url)
      .then(response => {
        if (response.ok) {
          console.log('Conexión exitosa');
          return true;
        } else {
          console.log('Error en la respuesta del servidor');
          return false;
        }
      })
      .catch(error => {
        console.log('Error de conexión:', error);
        return false;
      });
  }

