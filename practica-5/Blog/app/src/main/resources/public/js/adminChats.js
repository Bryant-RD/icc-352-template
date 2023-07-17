
var webSocket;
var tiempoReconectar = 5000;


conectar();


function recibirInformacionServidor(mensaje) {

    const json = JSON.parse(mensaje.data)
    console.log(json)

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