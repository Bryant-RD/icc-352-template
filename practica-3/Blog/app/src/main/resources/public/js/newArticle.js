//TODO: hacer el get de las etiquetas en el input y agregarla al container de los tags

import { createArticle, getArticlebyId, getArticles, updateArticleById } from "./api/article.controller.js";
import { getUserActive } from "./api/user.controller.js";

const addTagButton = document.getElementById("addTagButton");
const containerTagList = document.getElementById("tagList");
let inputTag = document.getElementById("tags");
const button = document.getElementById("newArticleButton");
const titleArticle = document.getElementById("title");
const bodyArticle = document.getElementById("content");
const title = document.getElementById("title-article");

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
let articleEditing;
let tags = [];


const printArticleEditing = () => {
    

    titleArticle.value = articleEditing.titulo;
    bodyArticle.value = articleEditing.cuerpo;

    articleEditing.etiquetas.forEach(item => {
        const tag = document.createElement("p");
        tag.className = "tag"
    
        tag.innerText = item.etiqueta;
    
        containerTagList.appendChild(tag)
    });
}

if (id != null) {
    button.innerText = "Guardar";
    title.innerText = "Editar Articulo";
    articleEditing = await getArticlebyId(id);
    printArticleEditing();
    tags = articleEditing.etiquetas;

    tags.forEach(item => {
    const tag = document.createElement("p");
    tag.className = "tag"

    tag.innerText = tag.etiqueta;

    containerTagList.appendChild(tag)
    });


}



addTagButton.addEventListener("click", () => {

    if (inputTag.value != "") {
        tags.push({
            id: Date.now(),
            etiqueta: inputTag.value,
            articulo: 0
        })
        
        const tag = document.createElement("p");
        tag.className = "tag"
    
        tag.innerText = inputTag.value;
    
        containerTagList.appendChild(tag)

        inputTag.value = "";
    }

})

button.addEventListener("click", async () => {
    const user = await getUserActive();
    let obj;
    let actionArticle;

    if (id != null) {
        obj = {
            id: articleEditing.id,
            titulo: titleArticle.value,
            cuerpo: bodyArticle.value,
            autor: user,
            fecha: articleEditing.fecha,
            etiquetas: tags.map(tag => ({
                id: tag.id,
                etiqueta: tag.etiqueta,
                articulo: articleEditing.id
            }))
        };

        actionArticle = await updateArticleById(id, obj);
    } else {
        const newArticleId = Date.now();
        obj = {
            id: newArticleId,
            titulo: titleArticle.value,
            cuerpo: bodyArticle.value,
            autor: user,
            fecha: new Date(),
            etiquetas: tags.map(tag => ({
                id: tag.id,
                etiqueta: tag.etiqueta,
                articulo: null
            }))
        };

        actionArticle = await createArticle(obj);
    }

    console.log(actionArticle);

    titleArticle.value = "";
    bodyArticle.value = "";
    tags = [];

    while (containerTagList.firstChild) {
        containerTagList.firstChild.remove();
    }
});





