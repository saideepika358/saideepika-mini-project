import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnections {

    // MySQL server connection
    private static final String SERVER_URL =
            "jdbc:mysql://localhost:3306";

    // Database connection
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_db";

    private static final String USER = "root";

    private static String getPasswordFromEnv() {

        try {

            for (String line : Files.readAllLines(Path.of(".env"))) {

                line = line.trim();

                if (line.startsWith("DB_PASSWORD=")) {
                    return line.substring("DB_PASSWORD=".length()).trim();
                }
            }

        } catch (IOException e) {

            System.out.println("Could not read .env file.");
        }

        return null;
    }

    // Connection to MySQL server
    public static Connection getServerConnection() throws SQLException {

        String password = getPasswordFromEnv();

        if (password == null || password.isBlank()) {
            throw new SQLException("DB_PASSWORD not found in .env");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!", e);
        }

        return DriverManager.getConnection(
                SERVER_URL,
                USER,
                password
        );
    }

    // Connection to hospital_db
    public static Connection getConnection() throws SQLException {

        String password = getPasswordFromEnv();

        if (password == null || password.isBlank()) {
            throw new SQLException("DB_PASSWORD not found in .env");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!", e);
        }

        return DriverManager.getConnection(
                DB_URL,
                USER,
                password
        );
    }

    // Run this file first
    public static void main(String[] args) {

        try (Connection con = getServerConnection()) {

            System.out.println("MySQL connection successful!");

        } catch (SQLException e) {

            System.out.println("MySQL connection failed!");
            e.printStackTrace();
        }
    }
}