import { LogOut } from "./api/user.controller.js";
import { getJWT } from "./api/storageController.js";

const menuIcon = document.querySelector(".menu-icon");
const navbarLinks = document.querySelector(".navbar-links");
const logoutBtn = document.getElementById("logout");


if (!getJWT()) {
  window.location.href = "./login.html"
}

//Abre menu en telefono
menuIcon.addEventListener("click", () => {
    navbarLinks.classList.toggle("show");
});

logoutBtn.addEventListener("click", () => {
  LogOut();
})
