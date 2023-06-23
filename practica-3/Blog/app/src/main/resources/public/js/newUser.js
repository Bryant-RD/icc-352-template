import { createUser, getUserById, updateUserById } from "./api/user.controller.js";

const registrar = document.getElementById("registrar");
const username = document.getElementById("username");
const name = document.getElementById("name");
const pass1 = document.getElementById("password");
const pass2 = document.getElementById("confirmPassword");
const inputImagen = document.getElementById('imagen');


const titulo = document.getElementById("titulo");

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
let userEditing;
let userAction;
let foto;

registrar.addEventListener("click", async (e) => {
    e.preventDefault();
    let obj
    let actionArticle;

    if(pass1 == pass2) {
        alert("las contrasenas no coinciden");
        return;
    }

    if (id != null) {

        obj = {
            userId: "",
            username: username.value,
            nombre: name.value,
            password: pass1.value,
            administrator: false ,
            autor: true,
            foto: foto
        }

        actionArticle = await updateUserById(id, obj);
    }else {

        obj = {
            userId: "",
            username: username.value,
            nombre: name.value,
            password: pass1.value,
            administrator: false ,
            autor: true,
            foto: foto
        }

        console.log(obj);
       actionArticle = await createUser(obj)
       window.location.href = "/login.html"
    }
})


inputImagen.addEventListener("change", () => {

    const file = inputImagen.files[0];

  // Verificar si se seleccionó un archivo
  if (file) {
    // Leer el archivo como base64
    const reader = new FileReader();
    reader.onload = () => {
      // Crear el objeto JSON que contendrá la imagen
      foto = {
        id: Date.now(),
        nombre: file.name,
        mimeType: file.type,
        fotoBase64: reader.result,
        }
      }
    }   
        
})


const printUserEditing = () => {
    

    username.value = userEditing.username;
    name.value = userEditing.nombre;


}

if (id != null) {
    titulo.innerText = "Editar Usuario"
    registrar.innerText = "Guardar";
    titulo.innerText = "Editar Usuario";
    userEditing = await getUserById(id);
    printUserEditing();

}