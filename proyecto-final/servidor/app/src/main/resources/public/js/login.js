import { login } from "./api/user.controller.js"

const username = document.getElementById("username");
const password = document.getElementById("password");
const sendData = document.getElementById("sendData");


sendData.addEventListener("click", (e) => {
    e.preventDefault();

    
    const test = login(username.value, password.value)
    console.log(test);
    

})

