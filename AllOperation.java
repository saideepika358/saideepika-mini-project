import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Alloperation {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n=================================");
            System.out.println(" HOSPITAL PATIENT MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addPatient();
                    break;

                case 2:
                    viewPatients();
                    break;

                case 3:
                    searchPatient();
                    break;

                case 4:
                    updatePatient();
                    break;

                case 5:
                    deletePatient();
                    break;

                case 6:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // =========================
    // ADD PATIENT
    // =========================

    static void addPatient() {

        String sql = """
                INSERT INTO patients
                (patient_name, age, gender, disease, phone, address, admission_date)
                VALUES (?, ?, ?, ?, ?, ?, CURDATE())
                """;

        try (Connection con = DBconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter patient name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter gender: ");
            String gender = sc.nextLine();

            System.out.print("Enter disease: ");
            String disease = sc.nextLine();

            System.out.print("Enter phone: ");
            String phone = sc.nextLine();

            System.out.print("Enter address: ");
            String address = sc.nextLine();

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, disease);
            ps.setString(5, phone);
            ps.setString(6, address);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Patient added successfully!");
            }

        } catch (SQLException e) {

            System.out.println("Error adding patient.");
            e.printStackTrace();
        }
    }

    // =========================
    // VIEW ALL PATIENTS
    // =========================

    static void viewPatients() {

        String sql = "SELECT * FROM patients";

        try (Connection con = DBconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--------------------------------------------------------------------------");
            System.out.printf(
                    "%-5s %-20s %-5s %-10s %-15s%n",
                    "ID",
                    "Name",
                    "Age",
                    "Gender",
                    "Disease"
            );
            System.out.println("--------------------------------------------------------------------------");

            while (rs.next()) {

                System.out.printf(
                        "%-5d %-20s %-5d %-10s %-15s%n",
                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("disease")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error viewing patients.");
            e.printStackTrace();
        }
    }

    // =========================
    // SEARCH PATIENT
    // =========================

    static void searchPatient() {

        System.out.print("Enter patient ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql =
                "SELECT * FROM patients WHERE patient_id = ?";

        try (Connection con = DBconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    System.out.println("\nPatient Details");
                    System.out.println("-----------------------------");

                    System.out.println(
                            "ID       : " +
                            rs.getInt("patient_id")
                    );

                    System.out.println(
                            "Name     : " +
                            rs.getString("patient_name")
                    );

                    System.out.println(
                            "Age      : " +
                            rs.getInt("age")
                    );

                    System.out.println(
                            "Gender   : " +
                            rs.getString("gender")
                    );

                    System.out.println(
                            "Disease  : " +
                            rs.getString("disease")
                    );

                    System.out.println(
                            "Phone    : " +
                            rs.getString("phone")
                    );

                    System.out.println(
                            "Address  : " +
                            rs.getString("address")
                    );

                    System.out.println(
                            "Admission : " +
                            rs.getDate("admission_date")
                    );

                } else {

                    System.out.println("Patient not found!");
                }
            }

        } catch (SQLException e) {

            System.out.println("Error searching patient.");
            e.printStackTrace();
        }
    }

    // =========================
    // UPDATE PATIENT
    // =========================

    static void updatePatient() {

        System.out.print("Enter patient ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new disease: ");
        String disease = sc.nextLine();

        System.out.print("Enter new phone: ");
        String phone = sc.nextLine();

        String sql =
                "UPDATE patients SET disease = ?, phone = ? " +
                "WHERE patient_id = ?";

        try (Connection con = DBconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, disease);
            ps.setString(2, phone);
            ps.setInt(3, id);

            int result = ps.executeUpdate();

            if (result > 0) {

                System.out.println(
                        "Patient updated successfully!"
                );

            } else {

                System.out.println("Patient not found!");
            }

        } catch (SQLException e) {

            System.out.println("Error updating patient.");
            e.printStackTrace();
        }
    }

    // =========================
    // DELETE PATIENT
    // =========================

    static void deletePatient() {

        System.out.print("Enter patient ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql =
                "DELETE FROM patients WHERE patient_id = ?";

        try (Connection con = DBconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            if (result > 0) {

                System.out.println(
                        "Patient deleted successfully!"
                );

            } else {

                System.out.println("Patient not found!");
            }

        } catch (SQLException e) {

            System.out.println("Error deleting patient.");
            e.printStackTrace();
        }
    }
}