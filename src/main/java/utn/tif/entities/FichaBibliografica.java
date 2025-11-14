package utn.tif.entities;

public class FichaBibliografica {
    private Long id;
    private Boolean eliminado;
    private String isbn;
    private String clasificacionDewey;
    private String estanteria;
    private String idioma;
    private Long libroId;

    public FichaBibliografica() {}

    public FichaBibliografica(Long id, Boolean eliminado, String isbn,
                              String clasificacionDewey, String estanteria, String idioma, Long libroId) {
        this.id = id;
        this.eliminado = eliminado;
        this.isbn = isbn;
        this.clasificacionDewey = clasificacionDewey;
        this.estanteria = estanteria;
        this.idioma = idioma;
        this.libroId = libroId;
    }

    public Long getLibroId() {
        return libroId;
    }
    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getClasificacionDewey() { return clasificacionDewey; }
    public void setClasificacionDewey(String clasificacionDewey) { this.clasificacionDewey = clasificacionDewey; }

    public String getEstanteria() { return estanteria; }
    public void setEstanteria(String estanteria) { this.estanteria = estanteria; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    @Override
    public String toString() {
        return String.format("Ficha: [id=%d, isbn='%s', clasificación='%s', estantería='%s']",
                id, isbn, clasificacionDewey, estanteria);
    }
}