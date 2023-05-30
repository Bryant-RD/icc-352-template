import { getUserActive } from "./api/user.controller.js"


const username = document.getElementById("username");
const profileName = document.getElementById("profile-name");
const articlesContainer = document.getElementById("articles-container");

const user = await getUserActive();
console.log(user);

username.innerText = `${user.username} - ${user.administrator ? "Administrador" : "autor"}`
profileName.innerText = user.nombre;


const printArticlesUser = async () => {

    //TODO: agregar un apartado para mostrar las etiquetas

    const userArticles = await getArticlesByUserId();

    let card = document.createElement("article");
    card.tagName = "article";
    

    userArticles.forEach(article => {
        // let tags = article.
        card.setAttribute("id", article.id)
        card.innerHTML = `
        <h3 class="article-title">${article.titulo}</h3>
          <p class="article-content">${article.cuerpo}</p>
        `;
    });

}


  