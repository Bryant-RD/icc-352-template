const URL = "http://127.0.0.1:8080"

import { getJWT, registroLogin, registroLogout, deleteLocaleStorage } from "./storageController.js";

export const getUsers = async () => {
  try {
      const response = await fetch(`${URL}/users`);
      const data = await response.json();
      return data;
  } catch (error) {
      console.log(error);
      return [];
  }
}

export const getUserById = async (id) => {
try {
  const response = await fetch(`${URL}/user/${id}`)
  const data = await response.json();
  return data

} catch (error) {
  console.log(error);
}
}

export const createUser = async (user) => {

try {
    const response = await fetch(`${URL}/create-user`, {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
            'Content-type': 'application/json'
        }
    });

    if (response.ok) {
        const data = await response.json();
        console.log(data);
        return data;
      } else {
        throw new Error('Error en la solicitud');
      }

    
  } catch (error) {
      console.error(error);
  } 
}

export const deleteUserById = async (id) => {
  try {
    const response = await fetch(`${URL}/delete-user/${id}`, {
        method: "DELETE",
        headers: {
            'Content-type': 'application/json'
        }
    });

    if (response.ok) {
        alert("usuario eliminado correctamente.");
    } else {
        console.log("HTTP request unsuccessful");
    }

    const data = await response.json();
    console.log(data);
} catch (error) {
    console.log(error);
}

}

export const updateUserById = async (id, obj) => {
try {
  const response = await fetch(`${URL}/update-user/${id}`, {
      method: "PUT",
      headers: {
          'Content-type': 'application/json'
      },
      body: JSON.stringify(obj)
  });

  if (response.ok) {
      alert("Articulo editado correctamente.");
  } else {
      console.log("HTTP request unsuccessful");
  }

  const data = await response.json();
  return data
} catch (error) {
  console.log(error);
}
}


export const login = (username, password) => {

  if (getJWT()) {
      registroLogin(username);
      window.location.href = "./index.html"
      return;
  }
fetch(URL+"/login", {
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
              window.location.href = "./login.html"
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
          // window.location.href = "./index.html"
      }

    })
    .catch((error) => {
        console.error(error);
        alert("Error de conexion y no hay sesion vigente")
        window.location.href = "./login.html"
        // Mostrar un mensaje de error al usuario
    });
}


export const LogOut = async () => {

  if (getJWT()) {
      deleteLocaleStorage("jwt")
      // window.location.href = "index.html"
      registroLogout()
      return;
  }
 
  fetch(URL+'/logout', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (response.ok) {
        // Cierre de sesión exitoso, redirigir a la página de inicio de sesión

        registroLogout();
        window.location.href = '/login.html';
      } else {
        // Ocurrió un error al cerrar sesión
        console.log('Error al cerrar sesión');
      }
    })
    .catch(error => {
      console.log('Error al cerrar sesión:', error);
    });
}