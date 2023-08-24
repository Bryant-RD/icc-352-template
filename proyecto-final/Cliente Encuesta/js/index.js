import { login } from "./services/api.js";

const username = document.getElementById("username");
const password = document.getElementById("password");
const sendData = document.getElementById("sendData");


sendData.addEventListener("click", (e) => {
    e.preventDefault();

    
    login(username.value, password.value)
    

})

