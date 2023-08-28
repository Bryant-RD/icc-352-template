// Importa el paquete grpc-web
const { FormulariosClient } = require('./formulario_pb.js');
const { ListarFormulariosRequest, CrearFormularioRequest } = require('./formulario_pb.js');

// Configura la dirección del servidor gRPC
const servidorGRPC = 'http://localhost:9090'; // Reemplaza con la dirección de tu servidor

// Crea un cliente gRPC para el servicio Formularios
const client = new FormulariosClient(servidorGRPC);

// Función para listar formularios
function listarFormularios() {
  const request = new ListarFormulariosRequest();

  client.listarFormularios(request, {}, (error, response) => {
    if (!error) {
      console.log('Formularios:', response.getFormulariosList());
    } else {
      console.error('Error al listar formularios:', error);
    }
  });
}

// Función para crear un formulario
function crearFormulario(nombre, sector, nivel, coordenadas) {
  const request = new CrearFormularioRequest();

  // Configura los datos del formulario
  request.getFormulario().setNombre(nombre);
  request.getFormulario().setSector(sector);
  request.getFormulario().setNivel(nivel);
  request.getFormulario().getCoordenadas().setLatitud(coordenadas.latitud);
  request.getFormulario().getCoordenadas().setLongitud(coordenadas.longitud);

  client.crearFormulario(request, {}, (error, response) => {
    if (!error) {
      console.log('Formulario creado:', response.getFormulario());
    } else {
      console.error('Error al crear formulario:', error);
    }
  });
}

// Llamar a la función listarFormularios para listar los formularios existentes
listarFormularios();

// Llamar a la función crearFormulario para crear un nuevo formulario
const nuevoFormulario = {
  nombre: 'Nuevo Formulario',
  sector: 'Nuevo Sector',
  nivel: 'Nuevo Nivel',
  coordenadas: { latitud: '123.456', longitud: '-789.012' },
};
// crearFormulario(nuevoFormulario.nombre, nuevoFormulario.sector, nuevoFormulario.nivel, nuevoFormulario.coordenadas);
