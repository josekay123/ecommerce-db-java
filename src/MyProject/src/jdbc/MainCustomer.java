package jdbc;
import java.sql.*;
import java.util.Scanner;

public class MainCustomer {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/ecommerce_db", "root", "kanombe12");
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- Welcome to Customer Management ---");
                System.out.println("1 - View Customers");
                System.out.println("2 - Add Customer");
                System.out.println("3 - Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    String selectQuery = "SELECT * FROM customer";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(selectQuery)) {
                        while (rs.next()) {
                            Customer c = new Customer(
                                    rs.getInt("CustomerID"),
                                    rs.getString("FirstName"),
                                    rs.getString("LastName"),
                                    rs.getString("PhoneNumber"),
                                    rs.getString("Email"),
                                    rs.getString("Password"),
                                    rs.getDate("DateOfBirth"),
                                    rs.getString("Gender"),
                                    rs.getTimestamp("CreatedAt"),
                                    rs.getTimestamp("UpdatedAt"),
                                    rs.getInt("MembershipID")
                            );
                            System.out.println(c);
                        }
                    }

                } else if (choice.equals("2")) {
                    String firstName, lastName, phone, email, password, dobStr, gender;
                    int membershipID;
                    java.sql.Date dob;

                    System.out.print("Enter First Name: ");
                    while (true) {
                        firstName = scanner.nextLine();
                        if (firstName.equalsIgnoreCase("back")) break;
                        if (firstName.matches("[a-zA-Z]+")) break;
                        System.out.println("Invalid. Only letters allowed. Retype First Name or type 'back' to return:");
                    }
                    if (firstName.equalsIgnoreCase("back")) continue;

                    System.out.print("Enter Last Name: ");
                    while (true) {
                        lastName = scanner.nextLine();
                        if (lastName.equalsIgnoreCase("back")) break;
                        if (lastName.matches("[a-zA-Z]+")) break;
                        System.out.println("Invalid. Only letters allowed. Retype Last Name or type 'back' to return:");
                    }
                    if (lastName.equalsIgnoreCase("back")) continue;

                    System.out.print("Enter Phone Number: ");
                    while (true) {
                        phone = scanner.nextLine();
                        if (phone.equalsIgnoreCase("back")) break;
                        if (phone.matches("\\d+")) break;
                        System.out.println("Invalid. Only digits allowed. Retype Phone Number or type 'back' to return:");
                    }
                    if (phone.equalsIgnoreCase("back")) continue;

                    System.out.print("Enter Email: ");
                    while (true) {
                        email = scanner.nextLine();
                        if (email.equalsIgnoreCase("back")) break;
                        if (email.contains("@")) break;
                        System.out.println("Invalid. Must contain '@'. Retype Email or type 'back' to return:");
                    }
                    if (email.equalsIgnoreCase("back")) continue;

                    System.out.print("Enter Password: ");
                    password = scanner.nextLine();
                    if (password.equalsIgnoreCase("back")) continue;

                    System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
                    while (true) {
                        dobStr = scanner.nextLine();
                        if (dobStr.equalsIgnoreCase("back")) break;
                        try {
                            dob = java.sql.Date.valueOf(dobStr);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid format. Retype Date (yyyy-mm-dd) or type 'back' to return:");
                        }
                    }
                    if (dobStr.equalsIgnoreCase("back")) continue;

                    System.out.print("Enter Gender (M/F): ");
                    while (true) {
                        gender = scanner.nextLine();
                        if (gender.equalsIgnoreCase("back")) break;
                        if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) break;
                        System.out.println("Invalid. Enter M or F, or type 'back' to return:");
                    }
                    if (gender.equalsIgnoreCase("back")) continue;

                    System.out.print("Enter Membership ID (number): ");
                    while (true) {
                        String memInput = scanner.nextLine();
                        if (memInput.equalsIgnoreCase("back")) {
                            membershipID = -1;
                            break;
                        }
                        try {
                            membershipID = Integer.parseInt(memInput);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Only numbers allowed. Retype or type 'back' to return:");
                        }
                    }
                    if (membershipID == -1) continue;

                    String insertSQL = "INSERT INTO customer (FirstName, LastName, PhoneNumber, Email, Password, DateOfBirth, Gender, MembershipID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, firstName);
                        pstmt.setString(2, lastName);
                        pstmt.setString(3, phone);
                        pstmt.setString(4, email);
                        pstmt.setString(5, password);
                        pstmt.setDate(6, java.sql.Date.valueOf(dobStr));
                        pstmt.setString(7, gender);
                        pstmt.setInt(8, membershipID);
                        pstmt.executeUpdate();
                        System.out.println("Customer added successfully.");
                    }

                } else if (choice.equals("3")) {
                    System.out.println("Goodbye!");
                    break;

                } else {
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
