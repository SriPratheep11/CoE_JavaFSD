import java.sql.*;
import java.util.Scanner;

class DBConn {
    public static Connection getConn() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/panimalar";
            return DriverManager.getConnection(url, "root", "root");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }
}

class AdminDAO {
    public boolean checkLogin(String username, String password) {
        try (Connection conn = DBConn.getConn()) {
            String sql = "SELECT * FROM admin WHERE username=? AND password=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
        return false;
    }
}

class AccountantDAO {
    public boolean addAccountant(String name, String email, String phone, String password) {
        try (Connection conn = DBConn.getConn()) {
            String sql = "INSERT INTO accountant (name, email, phone, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, password);
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error adding accountant: " + e.getMessage());
        }
        return false;
    }

    public void viewAccountants() {
        try (Connection conn = DBConn.getConn()) {
            String sql = "SELECT * FROM accountant";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                            ", Email: " + rs.getString("email") + ", Phone: " + rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing accountants: " + e.getMessage());
        }
    }

    public boolean editAccountant(int id, String name, String email, String phone, String password) {
        try (Connection conn = DBConn.getConn()) {
            String sql = "UPDATE accountant SET name=?, email=?, phone=?, password=? WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, password);
                ps.setInt(5, id);
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error editing accountant: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteAccountant(int id) {
        try (Connection conn = DBConn.getConn()) {
            String sql = "DELETE FROM accountant WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting accountant: " + e.getMessage());
        }
        return false;
    }
}

class StudentDAO {
    public boolean addStudent(String name, String email, String course, double fee, double paid, double due, String address, String phone) {
        try (Connection conn = DBConn.getConn()) {
            String sql = "INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, course);
                ps.setDouble(4, fee);
                ps.setDouble(5, paid);
                ps.setDouble(6, due);
                ps.setString(7, address);
                ps.setString(8, phone);
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
        return false;
    }

    public void viewStudents() {
        try (Connection conn = DBConn.getConn()) {
            String sql = "SELECT * FROM student";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                            ", Email: " + rs.getString("email") + ", Course: " + rs.getString("course"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    public boolean editStudent(int id, String name, String email, String course, double fee, double paid, double due, String address, String phone) {
        try (Connection conn = DBConn.getConn()) {
            String sql = "UPDATE student SET name=?, email=?, course=?, fee=?, paid=?, due=?, address=?, phone=? WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, course);
                ps.setDouble(4, fee);
                ps.setDouble(5, paid);
                ps.setDouble(6, due);
                ps.setString(7, address);
                ps.setString(8, phone);
                ps.setInt(9, id);
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error editing student: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        try (Connection conn = DBConn.getConn()) {
            String sql = "DELETE FROM student WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int result = ps.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
        return false;
    }

    public void viewPendingFees() {
        try (Connection conn = DBConn.getConn()) {
            String sql = "SELECT * FROM student WHERE due > 0";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                            ", Email: " + rs.getString("email") + ", Due: " + rs.getDouble("due"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error viewing students with pending fees: " + e.getMessage());
        }
    }
}

public class FeeReportSystem {
    private static Scanner sc = new Scanner(System.in);
    private static AdminDAO adminDAO = new AdminDAO();
    private static AccountantDAO accountantDAO = new AccountantDAO();
    private static StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        System.out.println("Welcome to Fee Report Software");
       
        while (true) {
            if (adminLogin()) {
                askUserRole();
            } else {
                System.out.println("Invalid login credentials.");
            }
        }
    }

    private static boolean adminLogin() {
        System.out.println("Enter Admin Username:");
        String username = sc.nextLine();
        System.out.println("Enter Admin Password:");
        String password = sc.nextLine();

        return adminDAO.checkLogin(username, password);
    }

    private static void askUserRole() {
        while (true) {
            System.out.println("\nAre you an Admin or Accountant?");
            System.out.println("1. Admin");
            System.out.println("2. Accountant");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    showAdminMenu();
                    return;
                case 2:
                    showAccountantMenu();
                    return;
                case 3:
                    System.out.println("Thanks for Using");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Accountant");
            System.out.println("2. View Accountants");
            System.out.println("3. Edit Accountant");
            System.out.println("4. Delete Accountant");
            System.out.println("5. Back To Menu");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addAccountant();
                    break;
                case 2:
                    accountantDAO.viewAccountants();
                    break;
                case 3:
                    editAccountant();
                    break;
                case 4:
                    deleteAccountant();
                    break;
                case 5:
                    askUserRole();
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showAccountantMenu() {
        while (true) {
            System.out.println("\nAccountant Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Edit Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View Pending Fees");
            System.out.println("6. Back To Menu");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    studentDAO.viewStudents();
                    break;
                case 3:
                    editStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    studentDAO.viewPendingFees();
                    break;
                case 6:
                    askUserRole();
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addAccountant() {
        System.out.println("Enter Accountant Name:");
        String name = sc.nextLine();
        System.out.println("Enter Accountant Email:");
        String email = sc.nextLine();
        System.out.println("Enter Accountant Phone:");
        String phone = sc.nextLine();
        System.out.println("Enter Accountant Password:");
        String password = sc.nextLine();

        boolean result = accountantDAO.addAccountant(name, email, phone, password);
        if (result) {
            System.out.println("Accountant added successfully.");
        } else {
            System.out.println("Error adding accountant.");
        }
    }

    private static void editAccountant() {
        System.out.println("Enter Accountant ID to Edit:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter New Name:");
        String name = sc.nextLine();
        System.out.println("Enter New Email:");
        String email = sc.nextLine();
        System.out.println("Enter New Phone:");
        String phone = sc.nextLine();
        System.out.println("Enter New Password:");
        String password = sc.nextLine();

        boolean result = accountantDAO.editAccountant(id, name, email, phone, password);
        if (result) {
            System.out.println("Accountant updated successfully.");
        } else {
            System.out.println("Error updating accountant.");
        }
    }

    private static void deleteAccountant() {
        System.out.println("Enter Accountant ID to Delete:");
        int id = sc.nextInt();
        sc.nextLine();

        boolean result = accountantDAO.deleteAccountant(id);
        if (result) {
            System.out.println("Accountant deleted successfully.");
        } else {
            System.out.println("Error deleting accountant.");
        }
    }

    private static void addStudent() {
        System.out.println("Enter Student Name:");
        String name = sc.nextLine();
        System.out.println("Enter Student Email:");
        String email = sc.nextLine();
        System.out.println("Enter Student Course:");
        String course = sc.nextLine();
        System.out.println("Enter Total Fee:");
        double fee = sc.nextDouble();
        System.out.println("Enter Paid Fee:");
        double paid = sc.nextDouble();
        System.out.println("Enter Due Fee:");
        double due = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter Address:");
        String address = sc.nextLine();
        System.out.println("Enter Phone Number:");
        String phone = sc.nextLine();

        boolean result = studentDAO.addStudent(name, email, course, fee, paid, due, address, phone);
        if (result) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Error adding student.");
        }
    }

    private static void editStudent() {
        System.out.println("Enter Student ID to Edit:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter New Name:");
        String name = sc.nextLine();
        System.out.println("Enter New Email:");
        String email = sc.nextLine();
        System.out.println("Enter New Course:");
        String course = sc.nextLine();
        System.out.println("Enter Total Fee:");
        double fee = sc.nextDouble();
        System.out.println("Enter Paid Fee:");
        double paid = sc.nextDouble();
        System.out.println("Enter Due Fee:");
        double due = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter New Address:");
        String address = sc.nextLine();
        System.out.println("Enter New Phone Number:");
        String phone = sc.nextLine();

        boolean result = studentDAO.editStudent(id, name, email, course, fee, paid, due, address, phone);
        if (result) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Error updating student.");
        }
    }

    private static void deleteStudent() {
        System.out.println("Enter Student ID to Delete:");
        int id = sc.nextInt();
        sc.nextLine();

        boolean result = studentDAO.deleteStudent(id);
        if (result) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Error deleting student.");
        }
    }
}
