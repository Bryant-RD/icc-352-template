import { LogOut } from "./services/api.js";
import { getJWT } from "./services/storageController.js";

const menuIcon = document.querySelector(".menu-icon");
const navbarLinks = document.querySelector(".navbar-links");
const logoutBtn = document.getElementById("logout");


if (!getJWT()) {
  window.location.href = "./index.html"
}

//Abre menu en telefono
menuIcon.addEventListener("click", () => {
    navbarLinks.classList.toggle("show");
});

logoutBtn.addEventListener("click", () => {
  LogOut();
})
