import { getRegistrobyId } from "./api/registro.controller.js";

const urlParams = new URLSearchParams(window.location.search);
const id  = urlParams.get("id")


const idEncuesta = document.getElementById("id_encuesta");
const idEncuestador = document.getElementById("id_encuestador");
const nombre = document.getElementById("nombre");
const nivel = document.getElementById("nivel");
const sector = document.getElementById("sector");
const latitud = document.getElementById("latitud");
const longitud = document.getElementById("longitud");
const imagen = document.getElementById("imagen");


if (id == null) {
    alert("Ocurrio en error vuelva a intentarlo despues")
    // return;
}


const printDetalles = async  () => {

    const registro = await getRegistrobyId(id);

    idEncuesta.innerText = registro.idEncuesta;
    idEncuestador.innerText = registro.idEncuestador || undefined;
    nombre.innerText = registro.nombre;
    nivel.innerText = registro.nivel;
    sector.innerText = registro.sector;
    latitud.innerText = registro.coordenadas.latitud;
    longitud.innerText = registro.coordenadas.longitud;
    imagen.src = registro.foto;


    var map = L.map('map').setView([registro.coordenadas.latitud, registro.coordenadas.longitud], 13);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap'
    }).addTo(map);

    L.marker([registro.coordenadas.latitud, registro.coordenadas.longitud]).addTo(map)

    console.log(registro);
}

printDetalles();