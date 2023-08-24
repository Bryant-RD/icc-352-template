package servidor.entidades;

public class Encuesta {
    private String id_encuesta;
    private String id_encuestador;
    private String nombre;
    private String sector;
    private String nivel;
    private Coordenadas coordenadas;
    private String foto;

    public Encuesta() {

    }

    public Encuesta(String id_encuesta, String id_encuestador, String nombre, String sector, String nivel, Coordenadas coordenadas, String foto) {
        
        this.id_encuesta = id_encuesta;
        this.id_encuestador = id_encuestador;
        this.nombre = nombre;
        this.sector = sector;
        this.nivel = nivel;
        this.coordenadas = coordenadas;
        this.foto = foto;
    }

    public String getIdEncuesta() {
        return id_encuesta;
    }
    public void setIdEncuesta(String id_encuesta) {
        this.id_encuesta = id_encuesta;
    }

    public void setIdEncuestador(String id_encuestador) {
        this.id_encuestador = id_encuestador;
    }
    public String getIdEncuestador() {
        return id_encuestador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
    public String getSector() {
        return sector;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    public String getNivel() {
        return nivel;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }
    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getFoto() {
        return foto;
    }

}
