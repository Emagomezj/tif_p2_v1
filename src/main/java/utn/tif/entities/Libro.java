package utn.tif.entities;

public class Libro {
    private Long id;
    private Boolean eliminado;
    private String titulo;
    private String autor;
    private String editorial;
    private Integer anioEdicion;
    private FichaBibliografica fichaBibliografica;

    public Libro() {}

    public Libro(Long id, Boolean eliminado, String titulo, String autor,
                 String editorial, Integer anioEdicion, FichaBibliografica fichaBibliografica) {
        this.id = id;
        this.eliminado = eliminado;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioEdicion = anioEdicion;
        this.fichaBibliografica = fichaBibliografica;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Integer getAnioEdicion() { return anioEdicion; }
    public void setAnioEdicion(Integer anioEdicion) { this.anioEdicion = anioEdicion; }

    public FichaBibliografica getFichaBibliografica() { return fichaBibliografica; }
    public void setFichaBibliografica(FichaBibliografica fichaBibliografica) {
        this.fichaBibliografica = fichaBibliografica;
    }

    @Override
    public String toString() {
        return String.format("Libro: [id=%d, titulo='%s', autor='%s', editorial='%s', año=%d]",
                id, titulo, autor, editorial, anioEdicion);
    }
}