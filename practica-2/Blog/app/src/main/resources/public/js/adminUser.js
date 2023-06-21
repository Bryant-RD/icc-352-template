import { getUsers } from "./api/user.controller.js";

const update = document.getElementById("editButton");
const eliminar = document.getElementById("deleteButton");
const tableBody = document.getElementById("tableBody");


const showUserList = async() => {
    const users = await getUsers();
    users.forEach(item => {
        
        let rol = item.administrator ? "Administrador" : "autor";
        console.log(item)
        let row = document.createElement("tr")
        row.innerHTML = `
            <td>${item.userId}</td>
            <td id="titulo" >${item.nombre}</td>
            <td id="titulo">${item.username}</td>
            <td id="autor">${1}</td>
            <td>${rol}</td>
            <td class="actions">
                <a href="/newUser.html?id=${item.userId}" id="editButton" >editar</a>
                <a href="/detete-article/${item.id}" id="deleteButton" >eliminar</a>
            </td>
        `;

        row.addEventListener("click", (e)=> {
            let select = e.target.id
            console.log();
            switch (select) {
                case "titulo":
                    window.location.href = `/profile.html?id=${item.userId}`;
                    break;

                case "autor":
                    window.location.href = `/profile.html?id=${item.userId}`;
                    break;
                
                case "editButton":
            
                    window.location.href = `/newUser.html?id=${item.userId}`;
                    break;

                case "deleteButton":
                    window.location.href = `/delete-user/${item.userId}`;
                    break;
                    
                default:
                    break;
            }
        })

        const edithButton =  document.getElementById("editButton");

        tableBody.appendChild(row)

    });
}

showUserList();


