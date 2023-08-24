import { checkInternetConnection, sincronizarRegistros } from "./services/api.js"
import { deleteLocaleStorage, eliminarRegistro, obtenerRegistros } from "./services/storageController.js"

const tableBody = document.getElementById("tableBody")
const pictureContainer = document.getElementById("pictureContainer")
const imagen = document.getElementById("imagen")
const closePicture = document.getElementById("closePicture")
const uploadButton = document.getElementById("uploadData");

const worker = new Worker("./js/services/worker.js")

const printRegistros = () => {
    
    const registros = obtenerRegistros();
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
                <a href="#" id="editButton" class="actionButton">editar</a>
                <a href="#" id="deleteButton" class="actionButton">eliminar</a>
                <a href="#" id="showPicture" class="actionButton">imagen</a>
            </td>
        `;

        tr.addEventListener("click", (e) => {
            let select = e.target.id
            console.log();
            switch (select) {
                case "editButton":
                    window.location.href = `/index.html?id=${item.id_encuesta}`;
                    break;

                case "deleteButton":
                    eliminarRegistro(item.id)
                    break;
                
                case "showPicture":
                    if (item.imagen) {
                        imagen.src = item.imagen
                        pictureContainer.classList.remove("hidde")
                        pictureContainer.classList.add("show")
                    } else {
                        alert("Este registro no tiene imagen");
                    }
                    break;
                    
                default:
                    break;
            }
        })

        tableBody.appendChild(tr);
    });

}

closePicture.addEventListener("click", () => {
    pictureContainer.classList.remove("show");
    pictureContainer.classList.add("hidde")
})

printRegistros();

uploadButton.addEventListener("click", e => {
    e.preventDefault();

    if (checkInternetConnection) {
      console.log("TENGO");
      const registros = obtenerRegistros()
      worker.postMessage(registros)
      worker.addEventListener("message", (event) => {
        console.log(event.data);
        if (event.data.status == "success") {
          // Obtén todas las claves del localStorage

          deleteLocaleStorage("registros")

        }
        alert(event.data.message);
      })
    }

})

console.log(obtenerRegistros());


