package jdbc;

import java.sql.*;
import java.util.Scanner;

public class ReportDAOWithMain {
    private final Connection conn;

    public ReportDAOWithMain(Connection conn) {
        this.conn = conn;
    }

    // 1. Orders with customer name and total payment
    public void listOrdersWithCustomerAndPayment() throws SQLException {
        String sql = """
            SELECT o.OrderID, c.FirstName, c.LastName, p.AmountPaid, o.OrderDate
            FROM `order` o
            JOIN customer c ON o.CustomerID = c.CustomerID
            LEFT JOIN payment p ON o.OrderID = p.OrderID
            ORDER BY o.OrderDate DESC
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Orders with Customers and Payments ---");
            while (rs.next()) {
                System.out.printf("OrderID: %d | Customer: %s %s | Paid: %.2f | Date: %s\n",
                        rs.getInt("OrderID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getDouble("AmountPaid"),
                        rs.getTimestamp("OrderDate"));
            }
        }
    }

    // 2. Top 5 best-selling products
    public void topProductsBySales() throws SQLException {
        String sql = """
            SELECT p.ProductName, SUM(od.Quantity) AS TotalSold
            FROM orderitem od
            JOIN product p ON od.ProductID = p.ProductID
            GROUP BY p.ProductID
            ORDER BY TotalSold DESC
            LIMIT 5
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Top 5 Best-Selling Products ---");
            while (rs.next()) {
                System.out.printf("Product: %s | Sold: %d\n",
                        rs.getString("ProductName"),
                        rs.getInt("TotalSold"));
            }
        }
    }

    // 3. Total paid by each customer
    public void totalPaidPerCustomer() throws SQLException {
        String sql = """
            SELECT c.CustomerID, c.FirstName, c.LastName, SUM(p.AmountPaid) AS TotalPaid
            FROM customer c
            JOIN `order` o ON c.CustomerID = o.CustomerID
            JOIN payment p ON o.OrderID = p.OrderID
            GROUP BY c.CustomerID
            ORDER BY TotalPaid DESC
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Total Payments by Customer ---");
            while (rs.next()) {
                System.out.printf("Customer: %s %s | Total Paid: %.2f\n",
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getDouble("TotalPaid"));
            }
        }
    }

    // 4. Orders with product and customer details
    public void ordersWithProductAndCustomer() throws SQLException {
        String sql = """
            SELECT o.OrderID, c.FirstName, c.LastName, p.ProductName, od.Quantity, od.UnitPrice
            FROM `order` o
            JOIN customer c ON o.CustomerID = c.CustomerID
            JOIN orderitem od ON o.OrderID = od.OrderID
            JOIN product p ON od.ProductID = p.ProductID
            ORDER BY o.OrderID DESC
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Orders with Products and Customer Info ---");
            while (rs.next()) {
                System.out.printf("OrderID: %d | Customer: %s %s | Product: %s | Qty: %d | Price: %.2f\n",
                        rs.getInt("OrderID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("ProductName"),
                        rs.getInt("Quantity"),
                        rs.getDouble("UnitPrice"));
            }
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/ecommerce_db", "root", "kanombe12");
             Scanner scanner = new Scanner(System.in)) {

            ReportDAOWithMain dao = new ReportDAOWithMain(conn);

            while (true) {
                System.out.println("\n--- REPORT MENU ---");
                System.out.println("1 - List Orders with Customer & Payment");
                System.out.println("2 - Top 5 Best-Selling Products");
                System.out.println("3 - Total Paid per Customer");
                System.out.println("4 - Orders with Products & Customers");
                System.out.println("5 - Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> dao.listOrdersWithCustomerAndPayment();
                    case "2" -> dao.topProductsBySales();
                    case "3" -> dao.totalPaidPerCustomer();
                    case "4" -> dao.ordersWithProductAndCustomer();
                    case "5" -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
