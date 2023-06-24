import { getUserActive, getUserById } from "./api/user.controller.js"


const username = document.getElementById("username");
const profileName = document.getElementById("profile-name");
const articlesContainer = document.getElementById("articles-container");
const imgProfile = document.getElementById("imgProfile")

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');


if(id == null) {
  const user = await getUserActive();
  console.log(user);

  username.innerText = `${user.username} - ${user.administrator ? "Administrador" : "autor"}`
  profileName.innerText = user.nombre;
  imgProfile.src = user.foto.fotoBase64
} else {
  const user = await getUserById(id)
  username.innerText = `${user.username} - ${user.administrator ? "Administrador" : "autor"}`
  profileName.innerText = user.nombre;
}


const printArticlesUser = async () => {

    //TODO: agregar un apartado para mostrar las etiquetas

    const userArticles = await getArticlesByUserId(id);
    console.log(userArticles);

    let card = document.createElement("article");
    card.tagName = "article";
    

    userArticles.forEach(article => {
        // let tags = article.
        card.setAttribute("id", article.article.id)
        card.innerHTML = `
        <h3 class="article-title">${article.article.titulo}</h3>
          <p class="article-content">${article.article.cuerpo}</p>
        `;
    });
}

printArticlesUser();


  