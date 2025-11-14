package utn.tif.dao;

import utn.tif.entities.FichaBibliografica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FichaBibliograficaDao implements GenericDao<FichaBibliografica> {

    @Override
    public FichaBibliografica crear(FichaBibliografica ficha, Connection conn) throws SQLException {
        String sql = "INSERT INTO ficha_bibliografica (isbn, clasificacion_dewey, estanteria, idioma, libro_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ficha.getIsbn());
            stmt.setString(2, ficha.getClasificacionDewey());
            stmt.setString(3, ficha.getEstanteria());
            stmt.setString(4, ficha.getIdioma());
            stmt.setLong(5, ficha.getLibroId());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ficha.setId(generatedKeys.getLong(1));
                }
            }
        }
        return ficha;
    }

    public void asociarConLibro(Long fichaId, Long libroId, Connection conn) throws SQLException {
        String sql = "UPDATE ficha_bibliografica SET libro_id = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, libroId);
            stmt.setLong(2, fichaId);
            stmt.executeUpdate();
        }
    }

    @Override
    public FichaBibliografica leer(Long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM ficha_bibliografica WHERE id = ? AND eliminado = FALSE";
        FichaBibliografica ficha = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ficha = mapResultSetToFicha(rs);
                }
            }
        }
        return ficha;
    }

    public FichaBibliografica leerPorLibroId(Long libroId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM ficha_bibliografica WHERE libro_id = ? AND eliminado = FALSE";
        FichaBibliografica ficha = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, libroId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ficha = mapResultSetToFicha(rs);
                }
            }
        }
        return ficha;
    }

    @Override
    public List<FichaBibliografica> leerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM ficha_bibliografica WHERE eliminado = FALSE";
        List<FichaBibliografica> fichas = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                fichas.add(mapResultSetToFicha(rs));
            }
        }
        return fichas;
    }

    @Override
    public FichaBibliografica actualizar(FichaBibliografica ficha, Connection conn) throws SQLException {
        String sql = "UPDATE ficha_bibliografica SET isbn = ?, clasificacion_dewey = ?, estanteria = ?, idioma = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ficha.getIsbn());
            stmt.setString(2, ficha.getClasificacionDewey());
            stmt.setString(3, ficha.getEstanteria());
            stmt.setString(4, ficha.getIdioma());
            stmt.setLong(5, ficha.getId());

            stmt.executeUpdate();
        }
        return ficha;
    }

    @Override
    public void eliminar(Long id, Connection conn) throws SQLException {
        String sql = "UPDATE ficha_bibliografica SET eliminado = TRUE WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public FichaBibliografica buscarPorIsbn(String isbn, Connection conn) throws SQLException {
        String sql = "SELECT * FROM ficha_bibliografica WHERE isbn = ? AND eliminado = FALSE";
        FichaBibliografica ficha = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ficha = mapResultSetToFicha(rs);
                }
            }
        }
        return ficha;
    }

    private FichaBibliografica mapResultSetToFicha(ResultSet rs) throws SQLException {
        return new FichaBibliografica(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getString("isbn"),
                rs.getString("clasificacion_dewey"),
                rs.getString("estanteria"),
                rs.getString("idioma"),
                rs.getLong("libro_id")
        );
    }
}