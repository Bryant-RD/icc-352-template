import { deleteRegistroById, getRegistros } from "./api/registro.controller.js"
import { obtenerRegistros } from "./api/storageController.js"

const tableBody = document.getElementById("tableBody")
const pictureContainer = document.getElementById("pictureContainer")
const imagen = document.getElementById("imagen")
const closePicture = document.getElementById("closePicture")
const uploadButton = document.getElementById("uploadData");


  const printRegistros = async () => {
    
    const registros = await getRegistros();
    registros.forEach(item => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td data-th="ID Encuesta">${item.id_encuesta}</td>
            <td data-th="ID Encuestador">${item.id_encuestador}</td>
            <td data-th="Nombre">${item.nombre}</td>
            <td data-th="Sector">${item.sector}</td>
            <td data-th="Nivel">${item.nivel}</td>
            <td data-th="Coordenadas">${item.coordenadas.latitud}, ${item.coordenadas.longitud}</td>
            <td data-th="Acciones">
                <a href="detalles.html?id=${item.id_encuesta}" class="actionButton">Ver detalles</a>
            </td>
        `;

        tableBody.appendChild(tr);
    });

  }

  closePicture.addEventListener("click", () => {
    pictureContainer.classList.remove("show");
    pictureContainer.classList.add("hidde")
  })

  printRegistros();

// uploadButton.addEventListener("click", e => {
//     e.preventDefault();

//     if (checkInternetConnection) {
//       console.log("TENGO");
//       const registros = obtenerRegistros()
//       worker.postMessage(registros)
//       worker.addEventListener("message", (event) => {
//         console.log(event.data);
//         if (event.data.status == "success") {
//           // Obtén todas las claves del localStorage
//           const keys = Object.keys(localStorage);

//           // Itera sobre las claves y elimina los registros deseados
//           keys.forEach(key => {
//             if (key.includes("registros")) {
//               localStorage.removeItem(key);
//             }
//           });

//         }
//         alert(event.data.message);
//       })
//     }

// })


