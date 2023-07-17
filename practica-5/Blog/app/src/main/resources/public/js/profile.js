import {
  getUserActive,
  getUserById,
  getArticlesByUserId
} from "./api/user.controller.js"


const username = document.getElementById("username");
const profileName = document.getElementById("profile-name");
const articlesContainer = document.getElementById("articles-container");
const imgProfile = document.getElementById("imgProfile")

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');


if (id == null) {
  const user = await getUserActive();
  console.log(user);

  username.innerText = `${user.username} - ${user.rol}`
  profileName.innerText = user.nombre;
  if (user.foto && user.foto.fotoBase64 !== undefined) {
    imgProfile.src = user.foto.fotoBase64;
  }
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
  card.className = "article";



  userArticles.forEach(article => {

    let descripcion = article.article.cuerpo.length > 70 ? article.article.cuerpo.substring(0, 69) : article.article.cuerpo;

    const card = document.createElement("div");
    card.className = "article";
    card.setAttribute("id", article.article.id);

    const tagsList = document.createElement("div");
    tagsList.className = "tagsArticle";

    article.etiquetas.forEach(item => {
      const tagArticle = document.createElement("p");
      tagArticle.className = "tagArticle";
      tagArticle.innerHTML = item.etiqueta;
      tagsList.appendChild(tagArticle);
    });

    const title = document.createElement("h3");
    title.className = "article-title";
    title.textContent = article.article.titulo;

    const content = document.createElement("p");
    content.className = "article-content";
    content.textContent = descripcion;

    card.appendChild(title);
    card.appendChild(tagsList); // Agregar tagsList como segundo hijo de card
    card.appendChild(content);

    articlesContainer.appendChild(card);

    card.addEventListener("click", () => {
      window.location.href = `/article.html?id=${article.article.id}`
    })
  });

}

printArticlesUser();