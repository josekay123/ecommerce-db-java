package jdbc;

import java.sql.*;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class OrderMain {
    private static final Set<String> allowedCurrencies = new HashSet<>() {{
        add("USD"); add("EUR"); add("GBP"); add("RWF"); add("KES"); add("TZS"); add("UGX");
    }};

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/ecommerce_db", "root", "kanombe12");
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- Order Management ---");
                System.out.println("1 - View Orders");
                System.out.println("2 - Add Order");
                System.out.println("3 - Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    String selectQuery = "SELECT * FROM `order`";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(selectQuery)) {
                        while (rs.next()) {
                            Order o = new Order();
                            o.setOrderID(rs.getInt("OrderID"));
                            o.setOrderNumber(rs.getString("OrderNumber"));
                            o.setOrderDate(rs.getTimestamp("OrderDate"));
                            o.setRequiredDate(rs.getDate("RequiredDate"));
                            o.setTotalAmount(rs.getDouble("TotalAmount"));
                            o.setOrderStatus(rs.getString("OrderStatus"));
                            o.setPaymentMethod(rs.getString("PaymentMethod"));
                            o.setPaymentStatus(rs.getString("PaymentStatus"));
                            o.setCreatedAt(rs.getTimestamp("CreatedAt"));
                            o.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                            o.setCustomerID(rs.getInt("CustomerID"));
                            o.setCouponID(rs.getInt("CouponID"));
                            o.setCurrency(rs.getString("Currency"));
                            o.setShippingCost(rs.getDouble("ShippingCost"));
                            o.setShippedDate(rs.getDate("ShippedDate"));
                            o.setDeliveredDate(rs.getDate("DeliveredDate"));
                            o.setShippingMethod(rs.getString("ShippingMethod"));
                            o.setCarrierID(rs.getInt("CarrierID"));
                            System.out.println(o);
                        }
                    }

                } else if (choice.equals("2")) {
                    Order o = new Order();

                    System.out.print("Enter Order Number: ");
                    o.setOrderNumber(scanner.nextLine());

                    o.setOrderDate(new Timestamp(System.currentTimeMillis()));
                    o.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    o.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

                    while (true) {
                        try {
                            System.out.print("Enter Required Date (YYYY-MM-DD): ");
                            o.setRequiredDate(Date.valueOf(scanner.nextLine()));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Total Amount (decimal): ");
                        try {
                            o.setTotalAmount(Double.parseDouble(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Please enter a decimal.");
                        }
                    }

                    System.out.print("Enter Order Status: ");
                    o.setOrderStatus(scanner.nextLine());

                    System.out.print("Enter Payment Method: ");
                    o.setPaymentMethod(scanner.nextLine());

                    System.out.print("Enter Payment Status: ");
                    o.setPaymentStatus(scanner.nextLine());

                    while (true) {
                        System.out.print("Enter Customer ID (number): ");
                        try {
                            o.setCustomerID(Integer.parseInt(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Enter a number.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Coupon ID (optional, blank for null): ");
                        String couponInput = scanner.nextLine();
                        if (couponInput.isEmpty()) {
                            o.setCouponID(null);
                            break;
                        }
                        try {
                            o.setCouponID(Integer.parseInt(couponInput));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Enter a number or leave blank.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Currency (USD, EUR, GBP, RWF, KES, TZS, UGX): ");
                        String currency = scanner.nextLine().toUpperCase();
                        if (allowedCurrencies.contains(currency)) {
                            o.setCurrency(currency);
                            break;
                        } else {
                            System.out.println("Unsupported currency. Try one of: " + allowedCurrencies);
                        }
                    }

                    while (true) {
                        System.out.print("Enter Shipping Cost (decimal): ");
                        try {
                            o.setShippingCost(Double.parseDouble(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Please enter a decimal.");
                        }
                    }

                    while (true) {
                        try {
                            System.out.print("Enter Shipped Date (YYYY-MM-DD): ");
                            o.setShippedDate(Date.valueOf(scanner.nextLine()));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

                    while (true) {
                        try {
                            System.out.print("Enter Delivered Date (YYYY-MM-DD): ");
                            o.setDeliveredDate(Date.valueOf(scanner.nextLine()));
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

                    System.out.print("Enter Shipping Method: ");
                    o.setShippingMethod(scanner.nextLine());

                    while (true) {
                        System.out.print("Enter Carrier ID (number): ");
                        try {
                            o.setCarrierID(Integer.parseInt(scanner.nextLine()));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Enter a number.");
                        }
                    }

                    String insertSQL = "INSERT INTO `order` (OrderNumber, OrderDate, RequiredDate, TotalAmount, OrderStatus, PaymentMethod, PaymentStatus, CreatedAt, UpdatedAt, CustomerID, CouponID, Currency, ShippingCost, ShippedDate, DeliveredDate, ShippingMethod, CarrierID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, o.getOrderNumber());
                        pstmt.setTimestamp(2, o.getOrderDate());
                        pstmt.setDate(3, o.getRequiredDate());
                        pstmt.setDouble(4, o.getTotalAmount());
                        pstmt.setString(5, o.getOrderStatus());
                        pstmt.setString(6, o.getPaymentMethod());
                        pstmt.setString(7, o.getPaymentStatus());
                        pstmt.setTimestamp(8, o.getCreatedAt());
                        pstmt.setTimestamp(9, o.getUpdatedAt());
                        pstmt.setInt(10, o.getCustomerID());
                        if (o.getCouponID() != null) pstmt.setInt(11, o.getCouponID());
                        else pstmt.setNull(11, Types.INTEGER);
                        pstmt.setString(12, o.getCurrency());
                        pstmt.setDouble(13, o.getShippingCost());
                        pstmt.setDate(14, o.getShippedDate());
                        pstmt.setDate(15, o.getDeliveredDate());
                        pstmt.setString(16, o.getShippingMethod());
                        pstmt.setInt(17, o.getCarrierID());
                        pstmt.executeUpdate();
                        System.out.println("Order added successfully.");
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
