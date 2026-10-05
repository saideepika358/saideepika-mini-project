import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args) {

        String sql = """
                CREATE TABLE IF NOT EXISTS patients (
                    patient_id INT PRIMARY KEY AUTO_INCREMENT,
                    patient_name VARCHAR(100) NOT NULL,
                    age INT NOT NULL,
                    gender VARCHAR(20),
                    disease VARCHAR(150),
                    phone VARCHAR(15),
                    address VARCHAR(200),
                    admission_date DATE
                )
                """;

        try (Connection con = DBconnections.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(sql);

            System.out.println("Patients table created successfully!");

        } catch (SQLException e) {

            System.out.println("Failed to create patients table.");
            e.printStackTrace();
        }
    }
}