import { getArticles } from "./api/article.controller.js";
import { LogOut, getUsers } from "./api/user.controller.js";

const printAllUsers = async () => {
    try {
        const users = await getUsers();
        console.log(users);
    } catch (error) {
        console.log(error);
    }
}

printAllUsers();

const printAllArticles = async () => {
    const container = document.getElementById("article-container");

    try {
        const articles = await getArticles();
        articles.forEach(item => {
            let descripcion = item.cuerpo.length > 70 ? item.cuerpo.substring(0, 69) : item.cuerpo;
            
            let card = document.createElement("article");
            card.className = "article";
            card.setAttribute("id", item.id)
            card.innerHTML = `
                <h3 id="${item.id}" class="title-article">${item.titulo} - <span>${item.fecha}</span></h3>
                <p id="${item.id}" class="text-article"> ${descripcion} </p>
                <p id="${item.id}" class="autor-article" > ${item.autor.username} </p>
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


printAllArticles();


const Login = document.getElementById("login");

const goToLogin = (e) => {
    if (e.target.textContent == "Cerrar sesion") {
        LogOut();
    } else {
        window.location.href = "/login.html"
    }

    // ;
}

Login.addEventListener("click", goToLogin);

