

self.addEventListener('message', async event => {
    const registros = event.data;

  if (registros.length > 0) {
    console.log(registros);
    const response = await sincronizarRegistros(registros);
    console.log(response);
    if (response) {
      console.log("DENTRO");
      self.postMessage({ status: 'success', message: 'Datos registrados y sincronizados' });
    } else {
      console.log("no hay conexion");
      self.postMessage({ status: 'warning', message: 'Datos registrados de manera local y no sincronizados' });
    }
  }

});
  
const sincronizarRegistros = async (registros) => {
  try {
      const response = await fetch("http://127.0.0.1:8080/sincronizar", {
          method: "POST",
          body: JSON.stringify(registros),
          headers: {
              'Content-type': 'application/json'
          }
      });
      console.log(response);
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

  