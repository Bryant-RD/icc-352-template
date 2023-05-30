package blog.encapsulaciones;

public class Tag {
    private Long id;
    private String etiqueta;

    public Tag (Long id, String etiqueta) {
        id = this.id;
        etiqueta = this.etiqueta;
    }

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getEtiqueta() {
    return etiqueta;
}

public void setEtiqueta(String etiqueta) {
    this.etiqueta = etiqueta;
}


}
