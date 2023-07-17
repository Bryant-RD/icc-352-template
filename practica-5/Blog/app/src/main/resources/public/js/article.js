import { getArticlebyId, sendComment } from "./api/article.controller.js";
import { getUserActive } from "./api/user.controller.js";

const title = document.getElementById("title-article");
const taglist = document.getElementById("tagList");
const bodyArticle = document.getElementById("body-article");
const username = document.getElementById("username");
const commentsContainer = document.getElementById("comments-container");
const comentario = document.getElementById("content");

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');

const UsuarioActivo = async () => {
  const autor = await getUserActive()
  return autor;
}



const getArticulo = async (idArticulo) => {
  const articulo = await getArticlebyId(idArticulo)
  return articulo
}

comentar.addEventListener("click", async (e) => {
  e.preventDefault();

  try {

    const autor = await UsuarioActivo()
    const articulo = await getArticulo(id)

    articulo.article.fecha = null;
    
    const obj = {
      id: Date.now(),
      comentario: comentario.value,
      autor: autor,
      articulo: articulo.article
    }
  
    const data = await sendComment(obj)
    console.log(data);
  } catch (error) {
    console.log(error);
  }
})


const showArticle = async () => {

    const article = await getArticlebyId(id);

    console.log(article);

    title.innerText = `${article.article.titulo} - ${article.article.fecha}`
    bodyArticle.innerText = article.article.cuerpo;
    username.innerText = article.article.autor.username;

    article.etiquetas.forEach(tag => {
        const li = document.createElement("li");
        li.innerText = tag.etiqueta;

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
