package utn.tif.dao;

import utn.tif.entities.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDao implements GenericDao<Libro> {

    @Override
    public Libro crear(Libro libro, Connection conn) throws SQLException {
        String sql = "INSERT INTO libro (titulo, autor, editorial, anio_edicion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getEditorial());
            if (libro.getAnioEdicion() != null) {
                stmt.setInt(4, libro.getAnioEdicion());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    libro.setId(generatedKeys.getLong(1));
                }
            }
        }
        return libro;
    }

    @Override
    public Libro leer(Long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM libro WHERE id = ? AND eliminado = FALSE";
        Libro libro = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    libro = mapResultSetToLibro(rs);
                }
            }
        }
        return libro;
    }

    @Override
    public List<Libro> leerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM libro WHERE eliminado = FALSE";
        List<Libro> libros = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                libros.add(mapResultSetToLibro(rs));
            }
        }
        return libros;
    }

    @Override
    public Libro actualizar(Libro libro, Connection conn) throws SQLException {
        String sql = "UPDATE libro SET titulo = ?, autor = ?, editorial = ?, anio_edicion = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getEditorial());
            if (libro.getAnioEdicion() != null) {
                stmt.setInt(4, libro.getAnioEdicion());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setLong(5, libro.getId());

            stmt.executeUpdate();
        }
        return libro;
    }

    @Override
    public void eliminar(Long id, Connection conn) throws SQLException {
        String sql = "UPDATE libro SET eliminado = TRUE WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Libro> buscarPorTitulo(String titulo, Connection conn) throws SQLException {
        String sql = "SELECT * FROM libro WHERE titulo LIKE ? AND eliminado = FALSE";
        List<Libro> libros = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapResultSetToLibro(rs));
                }
            }
        }
        return libros;
    }

    public List<Libro> buscarPorAutor(String autor, Connection conn) throws SQLException {
        String sql = "SELECT * FROM libro WHERE autor LIKE ? AND eliminado = FALSE";
        List<Libro> libros = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + autor + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapResultSetToLibro(rs));
                }
            }
        }
        return libros;
    }

    private Libro mapResultSetToLibro(ResultSet rs) throws SQLException {
        return new Libro(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("editorial"),
                rs.getInt("anio_edicion"),
                null
        );
    }
}