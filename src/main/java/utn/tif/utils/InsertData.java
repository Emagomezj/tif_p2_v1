package utn.tif.utils;

import utn.tif.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class InsertData {
    public static void insertarDatos() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            var rs = stmt.executeQuery("SELECT COUNT(*) FROM libro");
            rs.next();
            int cantidadLibros = rs.getInt(1);

            if (cantidadLibros == 0) {
                String[] scripts = {
                    """
                    INSERT INTO libro (titulo, autor, editorial, anio_edicion)
                    VALUES
                        ('Cien años de soledad', 'Gabriel García Márquez', 'Sudamericana', 1967),
                        ('1984', 'George Orwell', 'Secker & Warburg', 1949),
                        ('El principito', 'Antoine de Saint-Exupéry', 'Reynal & Hitchcock', 1943)
                    """,
                    """
                    INSERT INTO ficha_bibliografica (isbn, clasificacion_dewey, estanteria, idioma, libro_id)
                    VALUES
                        ('978-8437604947', '863', 'FIC-001', 'Español', 1),
                        ('978-0451524935', '823', 'FIC-002', 'Inglés', 2),
                        ('978-0156012195', '843', 'FIC-003', 'Francés', 3)
                    """
                };

                for (String sql : scripts) {
                    int filasAfectadas = stmt.executeUpdate(sql);
                    System.out.println("Filas insertadas: " + filasAfectadas);
                }

                System.out.println("Datos de prueba insertados exitosamente");
            } else {
                System.out.println("Ya existen datos en la base de datos. No se insertaron datos de prueba.");
            }

        } catch (Exception e) {
            System.err.println("Error insertando datos de prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
