import { getArticlebyId } from "./api/article.controller.js";

const title = document.getElementById("title-article");
const taglist = document.getElementById("tagList");
const bodyArticle = document.getElementById("body-article");
const username = document.getElementById("username");
const commentsContainer = document.getElementById("comments-container");

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');


const showArticle = async () => {

    const article = await getArticlebyId(id);

    title.innerText = `${article.titulo} - ${article.fecha}`
    bodyArticle.innerText = article.cuerpo;
    username.innerText = article.autor.username;

    article.etiquetas.forEach(tag => {
        const li = document.createElement("li");
        li.innerText = tag;

        taglist.appendChild(li)
    });

    article.comentarios.forEach(comment => {
        const div = document.createElement("div");

        div.innerHTML = `
        <div class="comment">
          <div class="comment-header">
            <h3>${comment.autor.username}</h3>
          </div>
          <div class="comment-body">
            <p>${comment.comentario}</p>
          </div>
        `;

        commentsContainer.appendChild(div);
    });

}

showArticle();



console.log("El valor del parámetro 'id' es: " + id);
