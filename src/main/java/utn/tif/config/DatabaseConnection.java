package utn.tif.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    private static Properties properties = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (InputStream input = DatabaseConnection.class
                    .getClassLoader().getResourceAsStream("database.properties")) {
                if (input == null) {
                    throw new RuntimeException("No se pudo encontrar database.properties");
                }
                properties.load(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando DatabaseConnection", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException("Configuración de base de datos incompleta en database.properties");
        }

        return DriverManager.getConnection(url, user, password);
    }

    public static Connection getRawConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException("Configuración de base de datos incompleta en database.properties");
        }

        String baseUrl = url.replace("/biblioteca_db", "");

        return DriverManager.getConnection(baseUrl, user, password);
    }

    public static Properties getProperties() {
        return properties;
    }
}