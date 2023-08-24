import { getUsers } from "./api/user.controller.js";

const newUserButton = document.getElementById("newUserButton");
const tableBody = document.getElementById("tableBody");


const printUsuarios = async () => {

    const usuarios = await getUsers();
    console.log(usuarios);

    usuarios.forEach(item => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td data-th="ID Encuesta">${item.id_user}</td>
            <td data-th="ID Encuestador">${item.userName}</td>
            <td data-th="Nombre">${item.nombre}</td>
            <td data-th="Sector">${item.rol}</td>
            <td data-th="Acciones">
                <a href="#" id="editButton" class="actionButton">editar</a>
                <a href="#" id="deleteButton" class="actionButton">eliminar</a>
            </td>
        `;

        tr.addEventListener("click", (e) => {
            let select = e.target.id
            console.log(select);
            switch (select) {
                case "editButton":
                    window.location.href = `/newUser.html?id=${item.id_user}`;
                    break;

                case "deleteButton":
                    eliminarRegistro(item.id_user)
                    break;
                                    
                default:
                    break;
            }
        })

        tableBody.appendChild(tr);
    });

}

printUsuarios();



newUserButton.addEventListener("click", (e) => {
    e.preventDefault();

    window.location.href = "newUser.html";
})