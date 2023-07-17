package blog.encapsulaciones;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.List;

@Entity
public class Chat {

    @Id
    private String idChat;

    @JsonIgnore
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatMessage> mensajes;

    public Chat() {}

    public Chat(String idChat, List<ChatMessage> mensajes) {

        this.idChat = idChat;
        this.mensajes = mensajes;
    }

    public String getIdChat() {
        return idChat;
    }
    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public List<ChatMessage> getMensajes() {
        return mensajes;
    }
    public void setMensajes(List<ChatMessage> mensajes) {
        this.mensajes = mensajes;
    }

}


