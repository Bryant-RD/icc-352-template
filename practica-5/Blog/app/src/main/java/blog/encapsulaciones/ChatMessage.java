package blog.encapsulaciones;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMensaje;
    @Column
    private String idChat;
    
    @Column
    private String username;
    
    @Column
    private String foto;

    @Column
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Chat chat;

    public ChatMessage() {}

    public ChatMessage(Long idMensaje, String idChat, String username, String foto ,String message) {

        this.idMensaje = idMensaje;
        this.idChat = idChat;
        this.username = username;
        this.foto = foto;
        this.message = message;
    }


    public Long getIdMensaje() {
        return idMensaje;
    }
    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getIdChat() {
        return idChat;
    }
    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat getChat() {
        return chat;
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
