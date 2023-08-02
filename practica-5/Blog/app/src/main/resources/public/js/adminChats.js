
const tableBody = document.getElementById("tableBody");


var webSocket;
var tiempoReconectar = 1000;


conectar();


function recibirInformacionServidor(mensaje) {


  while (tableBody.firstChild) {
      tableBody.firstChild.remove();
  }

    const json = JSON.parse(mensaje.data)

    console.log(json);
    console.log(json.length);


    if (json.length > 0) {

      json.forEach(item => {
        const tr = document.createElement("tr");
        tr.className = "row"
        tr.id = "row"
        tr.innerHTML = `
        <td id='idChat' >${item.salaId}</td>
        <td>${item.hora}</td>
        <td>${item.mensajes}</td>
        <td style="padding: 0;" ><p class="deleteButton" >X</p></td>
        `;

        tr.addEventListener("click", (e) => {
          if (e.target.classList.contains("deleteButton")) {
                //TODO: Eliminar sala
                let obj = {
                  title: "delete",
                  message: item.salaId
                }

                let jsonData = JSON.stringify(obj);
                webSocket.send(jsonData)

                console.log(e.target.parentNode.parentNode.remove());

                console.log("Eliminar sala: " + item.salaId);
              } else {
                window.location.href = `chatArticle.html?id=${item.salaId}`
              }
        })

        // document.getElementById("row").addEventListener("click", (e) => {
        //   if (e.target.classList.contains("deleteButton")) {
        //     console.log("adentro");
        //   } else {
        //     const idChat = document.getElementById("idChat")
        //     window.location.href = `chatArticle.html?id=${idChat}`
        //   }
        // })
  
        tableBody.appendChild(tr)

      });
    }

}


function conectar() {
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/mensajeServidor/" + "admin");
  
    //indicando los eventos:
    webSocket.onmessage = function(data){recibirInformacionServidor(data);};
    webSocket.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
    webSocket.onclose = function(e){
        console.log("Desconectado - status "+this.readyState);
    };
  }
  
  function verificarConexion(){
    if(!webSocket || webSocket.readyState == 3){
      conectar();
    }
  }
  
  setInterval(verificarConexion, tiempoReconectar); //para reconectar.


const showChats = () => {

}