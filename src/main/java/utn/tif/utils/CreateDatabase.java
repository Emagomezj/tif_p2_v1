package utn.tif.utils;

import utn.tif.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateDatabase {
    public static boolean checkearExistencia() {
        String dbName = DatabaseConnection.getProperties().getProperty("db.name");
        String sql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + dbName + "'";
        try (Connection conn = DatabaseConnection.getRawConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next();

        } catch (Exception e) {
            System.err.println("Error verificando existencia de base de datos: " + e.getMessage());
            return false;
        }
    }

    public static void crearBaseDeDatos() {
        String[] scripts = {
                "CREATE DATABASE IF NOT EXISTS biblioteca_db",
                "USE biblioteca_db",
                """
                CREATE TABLE IF NOT EXISTS libro (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    eliminado BOOLEAN DEFAULT FALSE,
                    titulo VARCHAR(150) NOT NULL,
                    autor VARCHAR(120) NOT NULL,
                    editorial VARCHAR(100),
                    anio_edicion INT
                )
                """,
                """
                CREATE TABLE IF NOT EXISTS ficha_bibliografica (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    eliminado BOOLEAN DEFAULT FALSE,
                    isbn VARCHAR(17) UNIQUE,
                    clasificacion_dewey VARCHAR(20),
                    estanteria VARCHAR(20),
                    idioma VARCHAR(30),
                    libro_id BIGINT UNIQUE,
                    FOREIGN KEY (libro_id) REFERENCES libro(id) ON DELETE CASCADE
                )
                """,
                "CREATE INDEX idx_libro_titulo ON libro(titulo)",
                "CREATE INDEX idx_libro_autor ON libro(autor)",
                "CREATE INDEX idx_ficha_isbn ON ficha_bibliografica(isbn)"
        };

        try (Connection conn = DatabaseConnection.getRawConnection();
             Statement stmt = conn.createStatement()) {

            for (String sql : scripts) {
                stmt.executeUpdate(sql);
            }

            System.out.println("Base de datos y tablas creadas exitosamente");

        } catch (Exception e) {
            System.err.println("Error creando base de datos: " + e.getMessage());
        }
    }
}