package servidor.entidades;

public class Coordenadas {

    private String latitud;
    private String longitud;

    public Coordenadas() {

    }

    public Coordenadas(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
    public String getLatitud() {
        return latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    public String getLongitud() {
        return longitud;
    }

}
