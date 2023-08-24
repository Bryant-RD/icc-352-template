import { actualizarRegistro, crearRegistro, obtenerRegistro } from "./services/storageController.js";

// Selectores para camara
const webcamElement = document.getElementById('webcam');
const canvasElement = document.getElementById('canvas');
const snapSoundElement = document.getElementById('snapSound');
const webcam = new Webcam(webcamElement, 'user', canvasElement, snapSoundElement);

//Variables globales
let picture = null;
let obj;
let registro;
const worker = new Worker("./js/services/worker.js")

//Selectores del DOM

const menuIcon = document.querySelector(".menu-icon");
const navbarLinks = document.querySelector(".navbar-links");
const cbxCamara = document.getElementById("cbxCamara");
const containerCamara = document.getElementsByClassName("container-camara");
const canvas = document.getElementById("canvas")
const sendData = document.getElementById("sendData");
const nombre = document.getElementById("nombre")
const sector = document.getElementById("sector")
const nivel = document.getElementById("nivel")

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');


//Obtiene coordenadas
const getCoordenadas = () => {
  return new Promise((resolve, reject) => {
    if ("geolocation" in navigator) {
      navigator.geolocation.getCurrentPosition(
        position => {
          const latitud = position.coords.latitude;
          const longitud = position.coords.longitude;

          const coordenadas = {
            latitud: latitud,
            longitud: longitud
          };

          resolve(coordenadas);
        },
        error => {
          console.error("Error al obtener la ubicación: " + error.message);
          alert(
            "Debes permitir el uso de la ubicación para enviar los datos. Inténtalo de nuevo"
          );
          reject(error);
        }
      );
    } else {
      console.error("Geolocalización no es compatible con este navegador.");
      reject(new Error("Geolocalización no es compatible con este navegador."));
    }
  });
};

//Habilita el modo edicion
const loadEditing = () => {

  console.log(registro);
 
  nombre.value = registro.nombre;
  sector.value = registro.sector;
  nivel.value = registro.nivel;

  const context = canvasElement.getContext('2d');

  const img = new Image();
  img.src = registro.imagen || "../images/defaultImage.jpg";

  img.onload = function() {
    // Calcula las dimensiones proporcionales de la imagen para que encaje en el canvas
    const aspectRatio = img.width / img.height;
    const maxWidth = canvas.width;
    const maxHeight = canvas.height;

    let drawWidth = maxWidth;
    let drawHeight = maxWidth / aspectRatio;

    if (drawHeight > maxHeight) {
      drawHeight = maxHeight;
      drawWidth = maxHeight * aspectRatio;
    }

    const x = (canvas.width - drawWidth) / 2;
    const y = (canvas.height - drawHeight) / 2;

    context.drawImage(img, x, y, drawWidth, drawHeight);
  };

  canvas.classList.remove("hidde");
  canvas.classList.add("show")
  canvas.style.width = "80%"

  sendData.innerText = "Guardar"


}
//Habilita el modo edicion
if (id != null) {
  registro = obtenerRegistro(id)
  loadEditing();

}
//Enciende la camara OJO: probar en telefono
cbxCamara.addEventListener("change", ()=> {
  if (cbxCamara.checked) {
    // console.log(containerCamara[0]);
    containerCamara[0].classList.remove("hidde");
    containerCamara[0].classList.add("show")

    webcam.start()
   .then(result =>{
      console.log("webcam started");
      document.getElementById("take-picture").addEventListener("click", (e) => {
        e.preventDefault();

        picture = webcam.snap();
        canvas.classList.remove("hidde");
        canvas.classList.add("show")

      })
   })
   .catch(err => {
       console.log(err);
   });
  } else {
    containerCamara[0].classList.remove("show")
    containerCamara[0].classList.add("hidde")
    webcam.stop();
    if (picture == null) {
      canvas.classList.remove("show")
      canvas.classList.add("hidde")
    }
  }
})

//Envia la informacion guarda o edita dependiendo del modo
sendData.addEventListener("click", (e) => {
  e.preventDefault();

  if (id == null) {
    
    getCoordenadas()
  .then(coordenadas => {
    obj = {
      id_encuesta: Date.now(),
      id_encuestador: null, //OJO ARREGLAR
      nombre: nombre.value,
      sector: sector.value,
      nivel: nivel.value,
      coordenadas: coordenadas,
      foto: picture
    }
    
    crearRegistro(obj)



    alert("Registro guardado de manera local correctamente.")

    nombre.value = ""
    sector.value = ""
  })
  .catch(error => {
    console.error(error);
  });

  } else {

    //MODO EDICION

    obj = {
      id_encuesta: id,
      id_encuestador: 0,
      nombre: nombre.value,
      sector: sector.value,
      nivel: nivel.value,
      coordenadas: registro.coordenadas,
      foto: picture ? picture : registro.imagen
    }
    console.log(obj);
    actualizarRegistro(id, obj)
  }

})
