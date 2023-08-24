const menuIcon = document.querySelector(".menu-icon");
const navbarLinks = document.querySelector(".navbar-links");


//Abre menu en telefono
menuIcon.addEventListener("click", () => {
    navbarLinks.classList.toggle("show");
  });