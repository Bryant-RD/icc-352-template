import { getRegistros } from "./registro.controller.js";

// Función para obtener registros desde el LocalStorage
export const obtenerRegistros = async () => {
  if (checkInternetConnection) {
    const registrosNube = await getRegistros();

    if (registrosNube == null) {
      alert("No hay conexion con el servidor se mostraran los ultimos registros descargados.")
      return JSON.parse(localStorage.getItem("registros")) || [];
    } else {
      let registrosLocal = JSON.parse(localStorage.getItem("registros")) || [];
      let allRegistros = registrosLocal.concat(registrosNube);
      // console.log(allRegistros);
      // localStorage.setItem("registros", JSON.stringify(allRegistros))
      return allRegistros;
    }

  }
}
  
  // Función para obtener registritro por ID en el LocalStorage
export const obtenerRegistro = (id) => {
    const registros = obtenerRegistros();
    const registro = registros.find(item => item.id == id)
    return registro;
}
  
  // Función para crear un nuevo registro
export const crearRegistro = (nuevoRegistro) => {
    const registros = obtenerRegistros();
    registros.push(nuevoRegistro);
    localStorage.setItem("registros", JSON.stringify(registros));
}
  
  // Función para actualizar un registro por su ID
export const actualizarRegistro = (id, nuevoDatos) => {
    const registros = obtenerRegistros();
  
    const registroIndex = registros.findIndex(registro => registro.id == id);
  
    if (registroIndex !== -1) {
      registros[registroIndex] = { ...registros[registroIndex], ...nuevoDatos };
      localStorage.setItem("registros", JSON.stringify(registros));
      return true;
    } else {
      return false;
    }
}
  
  // Función para eliminar un registro por su ID
export const eliminarRegistro = (id) => {
    const registros = obtenerRegistros();
  
    const registrosActualizados = registros.filter(registro => registro.id_encuesta !== id);
  
    localStorage.setItem("registros", JSON.stringify(registrosActualizados));
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

const getJWT = () => {
  return localStorage.getItem("registros");
}