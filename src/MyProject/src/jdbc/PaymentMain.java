package jdbc;

import java.sql.*;
import java.util.Scanner;

public class PaymentMain {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/ecommerce_db", "root", "kanombe12");
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- Payment Management ---");
                System.out.println("1 - View Payments");
                System.out.println("2 - Add Payment");
                System.out.println("3 - Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    String selectQuery = "SELECT * FROM payment";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(selectQuery)) {
                        while (rs.next()) {
                            Payment p = new Payment();
                            p.setPaymentID(rs.getInt("PaymentID"));
                            p.setAmountPaid(rs.getDouble("AmountPaid"));
                            p.setPaymentDate(rs.getTimestamp("PaymentDate"));
                            p.setOrderID(rs.getInt("OrderID"));
                            p.setPaymentStatusID(rs.getInt("PaymentStatusID"));
                            p.setPaymentMethodID(rs.getInt("PaymentMethodID"));
                            System.out.println(p);
                        }
                    }

                } else if (choice.equals("2")) {
                    Payment p = new Payment();
                    p.setPaymentDate(new Timestamp(System.currentTimeMillis()));

                    while (true) {
                        System.out.print("Enter Amount Paid: ");
                        try {
                            p.setAmountPaid(Double.parseDouble(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                           System.out.println("Invalid input. Please enter a numeric value.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Order ID: ");
                        try {
                            p.setOrderID(Integer.parseInt(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a numeric value.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Payment Status ID: ");
                        try {
                            p.setPaymentStatusID(Integer.parseInt(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a numeric value.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Payment Method ID: ");
                        try {
                            p.setPaymentMethodID(Integer.parseInt(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a numeric value.");
                        }
                    }

                    String insertSQL = "INSERT INTO payment (AmountPaid, PaymentDate, OrderID, PaymentStatusID, PaymentMethodID) " +
                            "VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setDouble(1, p.getAmountPaid());
                        pstmt.setTimestamp(2, p.getPaymentDate());
                        pstmt.setInt(3, p.getOrderID());
                        pstmt.setInt(4, p.getPaymentStatusID());
                        pstmt.setInt(5, p.getPaymentMethodID());
                        pstmt.executeUpdate();
                        System.out.println("Payment added successfully.");
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
