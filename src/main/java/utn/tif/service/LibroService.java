package utn.tif.service;

import utn.tif.entities.Libro;
import utn.tif.entities.FichaBibliografica;
import utn.tif.dao.LibroDao;
import utn.tif.dao.FichaBibliograficaDao;
import utn.tif.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class LibroService implements GenericService<Libro> {
    private LibroDao libroDao = new LibroDao();
    private FichaBibliograficaDao fichaDao = new FichaBibliograficaDao();

    private boolean validarAnio(int anio){
        int aniooActual = java.time.Year.now().getValue();
        if(anio > aniooActual) return false;
        return true;
    }

    private boolean validarIsbn(String isbn) {
        String clean = isbn.replace("-", "").trim();
        return Pattern.matches("\\d{10}|\\d{13}", clean);
    }

    private void validarLibro(Libro libro) throws Exception {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new Exception("El título del libro es obligatorio");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new Exception("El autor del libro es obligatorio");
        }
        if (libro.getTitulo().length() > 150) {
            throw new Exception("El título no puede tener más de 150 caracteres");
        }
        if (libro.getAutor().length() > 120) {
            throw new Exception("El autor no puede tener más de 120 caracteres");
        }
        if (libro.getEditorial() == null || libro.getEditorial().trim().isEmpty()) {
            throw new Exception("La editorial del libro es obligatorio");
        }
        if (libro.getAnioEdicion() == null) throw new Exception("El año del libro es obligatorio");
        if (!validarAnio(libro.getAnioEdicion())) throw new Exception("Debe ingresar un año valido");
    }

    @Override
    public Libro insertar(Libro libro) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            validarLibro(libro);

            Libro libroCreado = libroDao.crear(libro, conn);
            if (libro.getFichaBibliografica() != null) {
                FichaBibliografica ficha = libro.getFichaBibliografica();

                if(!validarIsbn(ficha.getIsbn())) throw new Exception("Debe ingresar un isbn valido");

                FichaBibliografica fichaExistente = fichaDao.buscarPorIsbn(ficha.getIsbn(), conn);
                if (fichaExistente != null) {
                    throw new Exception("Ya existe una ficha bibliográfica con el ISBN: " + ficha.getIsbn());
                }
                ficha.setLibroId(libroCreado.getId());
                FichaBibliografica fichaCreada = fichaDao.crear(ficha, conn);
                fichaDao.asociarConLibro(fichaCreada.getId(), libroCreado.getId(), conn);
                libroCreado.setFichaBibliografica(fichaCreada);
            }

            conn.commit();
            return libroCreado;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public Libro actualizar(Libro libro) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            validarLibro(libro);

            Libro libroActualizado = libroDao.actualizar(libro, conn);

            if (libro.getFichaBibliografica() != null) {
                FichaBibliografica ficha = libro.getFichaBibliografica();
                if(!validarIsbn(ficha.getIsbn())) throw new Exception("Debe ingresar un isbn valido");

                FichaBibliografica fichaExistente = fichaDao.buscarPorIsbn(ficha.getIsbn(), conn);
                if (fichaExistente != null && !fichaExistente.getId().equals(ficha.getId())) {
                    throw new Exception("Ya existe otra ficha bibliográfica con el ISBN: " + ficha.getIsbn());
                }

                FichaBibliografica fichaActualizada = fichaDao.actualizar(ficha, conn);
                libroActualizado.setFichaBibliografica(fichaActualizada);
            }

            conn.commit();
            return libroActualizado;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            libroDao.eliminar(id, conn);

            FichaBibliografica ficha = fichaDao.leerPorLibroId(id, conn);
            if (ficha != null) {
                fichaDao.eliminar(ficha.getId(), conn);
            }

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public Libro getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Libro libro = libroDao.leer(id, conn);
            if (libro != null) {
                FichaBibliografica ficha = fichaDao.leerPorLibroId(id, conn);
                libro.setFichaBibliografica(ficha);
            }
            return libro;
        }
    }

    @Override
    public List<Libro> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Libro> libros = libroDao.leerTodos(conn);
            for (Libro libro : libros) {
                FichaBibliografica ficha = fichaDao.leerPorLibroId(libro.getId(), conn);
                libro.setFichaBibliografica(ficha);
            }
            return libros;
        }
    }

    public Optional<?> buscarPorTitulo(String titulo) throws Exception {
        if(titulo.trim().isEmpty()) throw new Exception("Debe ingresar el titulo para buscar");
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Libro>  libros = libroDao.buscarPorTitulo(titulo, conn);
            if(libros == null || libros.isEmpty()) return Optional.of("No hay libros que coincidan con el parámetro ingresado");
            return Optional.of(libros);
        }
    }

    public Optional<?> buscarPorAutor(String autor) throws Exception {
        if(autor.trim().isEmpty()) throw new Exception("Debe ingresar un Autor para buscar");
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Libro> libros = libroDao.buscarPorAutor(autor, conn);
            if(libros == null || libros.isEmpty()) return Optional.of("No hay libros que coincidan con el parámetro ingresado");
            return Optional.of(libros);
        }
    }
}