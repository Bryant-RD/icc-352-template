const IP = "http://127.0.0.1:8080"

self.addEventListener('message', async event => {
    const mensaje = event.data;


  switch (mensaje.tipo) {
    case "registro":

      if (mensaje.datos.length > 0) {
        const response = await sincronizarRegistros(mensaje.datos);
        if (response) {
          self.postMessage({ status: 'success', message: 'Datos registrados y sincronizados' });
        } else {
          console.log("no hay conexion");
          self.postMessage({ status: 'warning', message: 'Datos registrados de manera local y no sincronizados' });
        }
      }
      
      break;

      case "login":
        if (mensaje.datos.length > 0) {
          const response = await sincronizarlogins(mensaje.datos);
          
          if (response) {
            self.postMessage({ status: 'success', message: 'Datos registrados y sincronizados' });
          } else {
            console.log("no hay conexion");
            self.postMessage({ status: 'warning', message: 'Datos registrados de manera local y no sincronizados' });
          }
        }    break;

    default:
      break;
  }

});
  
const sincronizarRegistros = async (registros) => {
  try {
      const response = await fetch(IP+"/sincronizar", {
          method: "POST",
          body: JSON.stringify(registros),
          headers: {
              'Content-type': 'application/json'
          }
      });

      if (response.ok) {
          
          return true;             
        } else {
          throw new Error('Error en la solicitud');
          
        }

  } catch (error) {
      console.log(error);
      
      return false;
  }
}

const sincronizarlogins = async (logins) => {
  try {
      const response = await fetch(IP+"/sincronizar-logins", {
          method: "POST",
          body: JSON.stringify(logins),
          headers: {
              'Content-type': 'application/json'
          }
      });
      if (response.ok) {
        return true;
        } else {
          throw new Error('Error en la solicitud');
        }

  } catch (error) {
      console.error(error);
      return false;
  }
}