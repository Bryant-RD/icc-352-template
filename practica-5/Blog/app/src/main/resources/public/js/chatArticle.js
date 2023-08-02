import { getArticlebyId } from "./api/article.controller.js";
import { getUserActive } from "./api/user.controller.js";

const title = document.getElementById("chat-tittle");

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');

console.log(urlParams);

const getUser = async () => {
  let user = null;
  if (urlParams.size == 1) {
    user = await getUserActive();
  } else {
    user = urlParams.get('usuario');
  }
  return user;
}


const loadingChat = async () => {

// const article = await getArticlebyId(id);

// title.innerText = `${article.article.titulo}`;

}

loadingChat();



/* ----------------------WEB SOCKET -------------------------- */

//abriendo el objeto para el websocket
var webSocket;
var tiempoReconectar = 5000;


$(document).ready(function(){
  console.info("Iniciando Jquery -  Ejemplo WebServices");

  conectar();

  $("#send-button").click(async function(){

    let user = await getUser();


    let obj = {
      idChat: id,
      username: user.username ? user.username : user,
      foto: user.foto ? user.foto.fotoBase64 : null,
      mensaje: $("#message-input").val()
    }

    let jsonData = JSON.stringify(obj); // Convertir el objeto a una cadena JSON
    webSocket.send(jsonData); // Enviar la cadena JSON a través del WebSocket


    // console.log(obj);

  });
});

function recibirInformacionServidor(mensaje){

  if (mensaje.data == "sala eliminada") {

    alert("Chat cerrado")

    window.location.href = "homeLogOut.html"
  
    return;
  }
  const json = JSON.parse(mensaje.data)
  // console.log(json)


  if (Array.isArray(json)) {
    
    json.forEach(item => {
      const cardMessage = document.createElement("div");
      cardMessage.className = "cardMessage";

      cardMessage.innerHTML = `
          <div class="containerMessageImage">
              
              ${item.foto ? `<img class="messageImage" src="${item.foto}" alt="" />` : `<p class="userName">${item.username}: </p>`}
          </div>
          <div class="containerMessage">
              <p class="message">${item.mensaje}</p>
          </div>
      `
      document.getElementById('chat-messages').appendChild(cardMessage);
    });
  } else {
    if (json.mensaje !== '') {
      const cardMessage = document.createElement("div");
      cardMessage.className = "cardMessage";
  
      cardMessage.innerHTML = `
          <div class="containerMessageImage">
              
              ${json.foto ? `<img class="messageImage" src="${json.foto}" alt="" />` : `<p class="userName">${json.username}: </p>`}
          </div>
          <div class="containerMessage">
              <p class="message">${json.mensaje}</p>
          </div>
  
      `
      
      document.getElementById('chat-messages').appendChild(cardMessage);
    }
  }

  


}


function conectar() {
  webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/mensajeServidor/" + id);

  //indicando los eventos:
  webSocket.onmessage = function(data){recibirInformacionServidor(data);};
  webSocket.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
  webSocket.onclose = function(e){
      console.log("Desconectado - status "+this.readyState);
  };
}

function verificarConexion(){



  if(!webSocket || webSocket.readyState == 3){

    while (document.getElementById('chat-messages').firstChild) {
      document.getElementById('chat-messages').firstChild.remove();
  }

    conectar();
  }
}

setInterval(verificarConexion, tiempoReconectar); //para reconectar.



