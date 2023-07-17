import { getArticlesByUserId, deleteArticleById } from "./api/article.controller.js";

const update = document.getElementById("editButton");
const eliminar = document.getElementById("deleteButton");
const tableBody = document.getElementById("tableBody");


const showArticlesList = async() => {
    const articles = await getArticlesByUserId();
    articles.forEach(item => {
        let descripcion = item.article.cuerpo.length > 10 ? item.article.cuerpo.substring(0, 9) : item.article.cuerpo;

        let row = document.createElement("tr")
        row.innerHTML = `
            <td>${item.article.id}</td>
            <td id="titulo" >${item.article.titulo}</td>
            <td id="titulo">${descripcion}</td>
            <td id="autor">${item.article.autor.username}</td>
            <td>${item.article.fecha}</td>
            <td class="actions">
                <a href="/newArticle.html?id=${item.article.id}" id="editButton" >editar</a>
                <a href="#" id="deleteButton" >eliminar</a>
            </td>
        `;

        row.addEventListener("click", async (e)=> {
            let select = e.target.id
            console.log();
            switch (select) {
                case "titulo":
                    window.location.href = `/article.html?id=${item.article.id}`;
                    break;

                case "autor":
                    window.location.href = `/profile.html?id=${item.article.autor.userId}`;
                    break;
                
                case "editButton":
                    window.location.href = `/newArticle.html?id=${item.article.id}`;
                    break;

                case "deleteButton":
                    await deleteArticleById(item.article.id)
                    window.location.href = `/misArticulos.html`;
                    
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


