import { createUser, getUserById,updateUserById } from "./api/user.controller.js"

const registrar = document.getElementById("registrar");
const username = document.getElementById("username");
const name = document.getElementById("name");
const pass1 = document.getElementById("password");
const pass2 = document.getElementById("confirmPassword");


const titulo = document.getElementById("titulo");
const formulario = document.getElementsByClassName("login-form")[0];

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
let userEditing;
let userAction;


const rol = document.createElement("div");
rol.innerHTML = `
    <label for="rol">Rol:</label>
    <select id="rol" name="rol" required>
    <option value="Usuario">Usuario</option>
    <option value="Administrador">Administrador</option>
    </select>
`;



registrar.addEventListener("click", async (e) => {
    e.preventDefault();
    let obj

    if(pass1.value != pass2.value) {
        alert("las contrasenas no coinciden");
        console.log(pass1.value + " != " + pass2.value);
        return;
    }

    if (id != null) {

        obj = {
            id_user: id,
            userName: username.value,
            nombre: name.value,
            password: pass1.value,
            rol: document.getElementById("rol").value
        }

        userAction = await updateUserById(id, obj);
    }else {


        obj = {
            id_user: Date.now(),
            userName: username.value,
            nombre: name.value,
            password: pass1.value,
            rol: "usuario"

        }

        console.log(obj);

        username.value = "";
        name.value = "";
        pass1.value = "";
        pass2.value = "";
        userAction = await createUser(obj)

       window.location.href = "/index.html"
    }
})


const printUserEditing = () => {
    console.log(userEditing);
    

    username.value = userEditing.userName;
    name.value = userEditing.nombre;
    pass1.value  = userEditing.password;
    pass2.value = userEditing.password;
    document.getElementById("rol").value


}

if (id != null) {

    const penultimoElemento = formulario.children[formulario.children.length - 2];
    formulario.insertBefore(rol, penultimoElemento.nextSibling);


    titulo.innerText = "Editar Usuario"
    registrar.innerText = "Guardar";
    titulo.innerText = "Editar Usuario";
    userEditing = await getUserById(id);
    printUserEditing();

}