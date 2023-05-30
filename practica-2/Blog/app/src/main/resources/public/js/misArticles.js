import { getArticlesByUserId } from "./api/article.controller.js";

const update = document.getElementById("editButton");
const eliminar = document.getElementById("deleteButton");
const tableBody = document.getElementById("tableBody");


const showArticlesList = async() => {
    const articles = await getArticlesByUserId();
    articles.forEach(item => {
        let descripcion = item.cuerpo.length > 10 ? item.cuerpo.substring(0, 9) : item.cuerpo;

        let row = document.createElement("tr")
        row.innerHTML = `
            <td>${item.id}</td>
            <td id="titulo" >${item.titulo}</td>
            <td id="titulo">${descripcion}</td>
            <td id="autor">${item.autor.username}</td>
            <td>${item.fecha}</td>
            <td class="actions">
                <a href="/newArticle.html?id=${item.id}" id="editButton" >editar</a>
                <a href="/detete-article/${item.id}" id="deleteButton" >eliminar</a>
            </td>
        `;

        row.addEventListener("click", (e)=> {
            let select = e.target.id
            console.log();
            switch (select) {
                case "titulo":
                    window.location.href = `/article.html?id=${item.id}`;
                    break;

                case "autor":
                    window.location.href = `/profile.html?id=${item.id}`;
                    break;
                
                case "editButton":
                    window.location.href = `/newArticle.html?id=${item.id}`;
                    break;

                case "deleteButton":
                    window.location.href = `/delete-article/${item.id}`;
                    break;
                    
                default:
                    break;
            }
        })

        const edithButton =  document.getElementById("editButton");

        tableBody.appendChild(row)

    });
}

showArticlesList();


