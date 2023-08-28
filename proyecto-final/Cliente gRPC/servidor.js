import { ServicioCreacionClient, ServicioListadoClient } from './formulario_pb.js';

// Crear un cliente para el servicio de creación
const servicioCreacionClient = new ServicioCreacionClient('http://localhost:9090');

// Crear un cliente para el servicio de listado
const servicioListadoClient = new ServicioListadoClient('http://localhost:9090');

// Ejemplo: Llamar al servicio de creación
const formularioACrear = new CrearFormularioRequest();
// Configurar el formularioACrear con tus datos

servicioCreacionClient.crearFormulario(formularioACrear, {}, (error, response) => {
  if (!error) {
    const formularioCreado = response.getFormulario();
    console.log('Formulario creado:', formularioCreado.toObject());
  } else {
    console.error('Error al crear el formulario:', error.message);
  }
});

// Ejemplo: Llamar al servicio de listado
const listarFormulariosRequest = new ListarFormulariosRequest();
// Configurar listarFormulariosRequest con tus datos

servicioListadoClient.listarFormularios(listarFormulariosRequest, {}, (error, response) => {
  if (!error) {
    const formularios = response.getFormulariosList();
    console.log('Formularios listados:', formularios.map(f => f.toObject()));
  } else {
    console.error('Error al listar formularios:', error.message);
  }
});
