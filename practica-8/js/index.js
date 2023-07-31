import { createUser, deleteUserById, getUserById, getUsers, updateUserById } from "./user.controller.js";

const userId = document.getElementById("userId");
const buttonGetUser = document.getElementById("buttonGetUser")
const buttonDeleteUser = document.getElementById("buttonDeleteUser")
const buttonGetAllUsers = document.getElementById("buttonGetAllUsers")
const buttonCreateUser = document.getElementById("createUser");
const buttonUpdate = document.getElementById("buttonUpdateUser")


const usersContainer = document.getElementById("usersContainer")


const matricula = document.getElementById("matricula")
const nombre = document.getElementById("nombre")
const carrera = document.getElementById("carrera")


buttonGetAllUsers.addEventListener("click", async (e) => {
    e.preventDefault();

    while(usersContainer.firstChild) {
        usersContainer.remove()
    }

    const users = await getUsers();
    
    users.forEach(item => {
        const card = document.createElement("div");

        card.innerHTML = `
            <p>Matricula: ${item.matricula}</p>
            <p>Nombre: ${item.nombre}</p>
            <p>Carrera: ${item.carrera}</p>
        `

        usersContainer.appendChild(card);
    });
    
})



buttonGetUser.addEventListener("click", async (e) => {
    e.preventDefault();

    while(usersContainer.firstChild) {
        usersContainer.remove()
    }

    const user = await getUserById(userId.value);
    console.log(user);
    
    const card = document.createElement("div");

    card.innerHTML = `
        <p>Matricula: ${user.matricula}</p>
        <p>Nombre: ${user.nombre}</p>
        <p>Carrera: ${user.carrera}</p>
        `

    usersContainer.appendChild(card);
    
})

buttonDeleteUser.addEventListener("click", async (e) => {
    e.preventDefault();

    const user = await deleteUserById(userId.value);
    console.log(user);
    
})

buttonCreateUser.addEventListener("click", async (e) => {
    e.preventDefault();

    if (matricula.value === "" || nombre.value === "" || carrera.value === "") {
        alert("Todos los campos deben estar llenos");
        return;
    }

    let obj = {
        matricula: matricula.value,
        nombre: nombre.value,
        carrera: carrera.value
    }

    if (buttonCreateUser.textContent == "Crear usuario") {
        await createUser(obj);

    } else {
        await updateUserById(obj)
    }


    matricula.value = "";
    nombre.value = "";
    carrera.value = "";
})

buttonUpdate.addEventListener("click", async e => {
    e.preventDefault();

    const oldUser = await getUserById(userId.value);

    matricula.value = oldUser.matricula;
    nombre.value = oldUser.nombre;
    carrera.value = oldUser.carrera;

    buttonCreateUser.innerText = "Actualizar usuario"




})



