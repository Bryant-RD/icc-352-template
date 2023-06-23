package blog.encapsulaciones;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Tag {
    @Id
    private Long id;
    @Column
    private String etiqueta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Article articulo;

    public Tag () {

    }

    public Tag (Long id, String etiqueta, Article articulo) {
        this.id = id;
        this.etiqueta = etiqueta;
        this.articulo = articulo;
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

    public Article getArticulo() {
        return articulo;
    }

    public void setArticulo(Article articulo) {
        this.articulo = articulo;
    }


}
