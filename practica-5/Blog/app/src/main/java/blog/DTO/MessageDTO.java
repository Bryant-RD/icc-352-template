package blog.DTO;

public class MessageDTO {
    
    private Long idChat;
    private String username;
    private String foto;
    private String mensaje;

    public MessageDTO () {};

    public MessageDTO (Long idChat, String username, String foto ,String mensaje) {

        this.idChat = idChat;
        this.username = username;
        this.foto = foto;
        this.mensaje = mensaje;
    }


    public Long getIdChat() {
        return idChat;
    }
    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


}
