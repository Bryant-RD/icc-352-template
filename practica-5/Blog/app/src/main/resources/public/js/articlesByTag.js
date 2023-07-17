const titulo = document.getElementById("title-section")

const urlParams = new URLSearchParams(window.location.search);
const tag = urlParams.get('tag');

titulo.innerText = `Articulos con ${tag}`


import { getAllTags, getArticlesByTag } from "./api/article.controller.js";
import { LogOut } from "./api/user.controller.js";


const pagination = document.getElementById("pagination");
const listTags = document.getElementById("list-tags");


for (let i = 1; i < 10; i++) {
    const pNum = document.createElement("p");
    pNum.innerText = i;
    pNum.classList = "itemPag"
    pagination.appendChild(pNum);

    pNum.addEventListener("click", (e) => {
        let page = e.target.textContent;

        const children = pagination.children;
        for (const child of children) {
            child.classList.remove("active");
        }

        e.target.classList.add("active");

         printTagArticles(parseInt(page))
    })
}

const printAllTags = async () => {
    try {
        const tags = await getAllTags();
        
        tags.forEach(item => {
            const tag = document.createElement("p");
            tag.className = "tag"
            tag.innerText = item;

            listTags.appendChild(tag);
            console.log(tag);

            tag.addEventListener("click", () => {
                window.location.href = `articlesByTag.html?tag=${tag.textContent}`
            })
        });
    } catch (error) {
        
    }
}

printAllTags();

const printTagArticles = async () => {
    const container = document.getElementById("article-container");

    try {

        while (container.firstChild) {
            container.firstChild.remove();
        }

        const articles = await getArticlesByTag(tag);

        console.log(articles);
        articles.forEach(item => {
            let descripcion = item.article.cuerpo.length > 70 ? item.article.cuerpo.substring(0, 69) : item.article.cuerpo;
            
            let card = document.createElement("article");
            card.className = "article";
            card.setAttribute("id", item.article.id)
            card.innerHTML = `
                <h3 id="${item.article.id}" class="title-article">${item.article.titulo} - <span>${item.article.fecha}</span></h3>
                <p id="${item.article.id}" class="text-article"> ${descripcion} </p>
                <p id="${item.article.id}" class="autor-article" > ${item.article.autor.username} </p>
            `;


            card.addEventListener("click", (e) => {
                window.location.href = `/article.html?id=${e.target.id}`;
            })



            container.appendChild(card);
        });

        
    } catch (error) {
        console.log(error);
    }
};


printTagArticles();


const Login = document.getElementById("login");

const goToLogin = (e) => {
    if (e.target.textContent == "Cerrar sesion") {
        LogOut();
    } else {
        window.location.href = "/login.html"
    }
}

Login.addEventListener("click", goToLogin);



