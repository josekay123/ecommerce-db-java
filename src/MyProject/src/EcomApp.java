import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EcomApp {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ecommerce_db", "root", "kanombe12");
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n===== ECOMMERCE SYSTEM =====");
                System.out.println("1 - Customer Management");
                System.out.println("2 - Product Management");
                System.out.println("3 - Order Management");
                System.out.println("4 - Payment Management");
                System.out.println("5 - Reports");
                System.out.println("6 - Views");
                System.out.println("7 - Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> manageCustomers(conn, scanner);
                    case "2" -> manageProducts(conn, scanner);
                    case "3" -> manageOrders(conn, scanner);
                    case "4" -> managePayments(conn, scanner);
                    case "5" -> viewReportData(conn, scanner);
                    case "6" -> manageViews(conn, scanner);
                    case "7" -> {
                        System.out.println("✅ Exiting.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ DB Connection Error: " + e.getMessage());
        }
    }

    // Helper methods
    private static boolean askToRetry(Scanner scanner, String operation) {
        System.out.print("\n❌ Operation failed. 1. Try Again | 2. Back to Menu | Choose: ");
        String input = scanner.nextLine();
        while (!input.equals("1") && !input.equals("2")) {
            System.out.print("❌ Invalid choice. Please enter 1 or 2: ");
            input = scanner.nextLine();
        }
        return input.equals("1");
    }

    private static boolean askToContinue(Scanner scanner, String operation) {
        System.out.print("\n1. " + operation.substring(0, 1).toUpperCase() + operation.substring(1) + 
                         " | 2. Back to Menu | Choose: ");
        String input = scanner.nextLine();
        while (!input.equals("1") && !input.equals("2")) {
            System.out.print("❌ Invalid choice. Please enter 1 or 2: ");
            input = scanner.nextLine();
        }
        return input.equals("1");
    }

    private static String getInputOrBack(Scanner scanner) {
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("back") ? "back" : input;
    }

    private static int getValidIntInput(Scanner scanner, String prompt) throws Exception {
        System.out.print(prompt);
        String input = getInputOrBack(scanner);
        if (input.equalsIgnoreCase("back")) {
            throw new Exception("back");
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("❌ Invalid number format. Please enter a valid integer.");
        }
    }

    private static double getValidDoubleInput(Scanner scanner, String prompt) throws Exception {
        System.out.print(prompt);
        String input = getInputOrBack(scanner);
        if (input.equalsIgnoreCase("back")) {
            throw new Exception("back");
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("❌ Invalid number format. Please enter a valid number.");
        }
    }

    // ---------------- CUSTOMER ----------------
    public static void manageCustomers(Connection conn, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1 - View Customers");
            System.out.println("2 - Add Customer");
            System.out.println("3 - Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    boolean retry = true;
                    while (retry) {
                        try (Statement stmt = conn.createStatement();
                             ResultSet rs = stmt.executeQuery("SELECT * FROM customer")) {
                            System.out.println("\n--- Customer List ---");
                            System.out.printf("%-10s %-20s %-15s %-25s %-10s %-15s %-10s%n",
                                    "ID", "Name", "Phone", "Email", "Gender", "MemberID", "DOB");
                            
                            while (rs.next()) {
                                System.out.printf("%-10d %-20s %-15s %-25s %-10s %-15d %-15s%n",
                                        rs.getInt("CustomerID"),
                                        rs.getString("FirstName") + " " + rs.getString("LastName"),
                                        rs.getString("PhoneNumber"),
                                        rs.getString("Email"),
                                        rs.getString("Gender"),
                                        rs.getInt("MembershipID"),
                                        rs.getDate("DateOfBirth"));
                            }
                            retry = false;
                        } catch (SQLException e) {
                            System.err.println("\n❌ Error viewing customers: " + e.getMessage());
                            retry = askToRetry(scanner, "view customers");
                        }
                    }
                }
                case "2" -> {
                    boolean retry = true;
                    while (retry) {
                        try {
                            System.out.println("\n--- Add New Customer --- (Enter 'back' to cancel)");
                            
                            System.out.print("First Name: ");
                            String first = getInputOrBack(scanner);
                            if (first.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Last Name: ");
                            String last = getInputOrBack(scanner);
                            if (last.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Phone Number: ");
                            String phone = getInputOrBack(scanner);
                            if (phone.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Email: ");
                            String email = getInputOrBack(scanner);
                            if (email.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Password: ");
                            String password = getInputOrBack(scanner);
                            if (password.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Date of Birth (YYYY-MM-DD): ");
                            String dobStr = getInputOrBack(scanner);
                            if (dobStr.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Gender: ");
                            String gender = getInputOrBack(scanner);
                            if (gender.equalsIgnoreCase("back")) break;
                            
                            int membershipId = getValidIntInput(scanner, "Membership ID: ");
                            
                            Date dob;
                            try {
                                dob = Date.valueOf(dobStr);
                            } catch (IllegalArgumentException e) {
                                System.err.println("❌ Invalid date format. Please use YYYY-MM-DD.");
                                continue;
                            }

                            String sql = "INSERT INTO customer (FirstName, LastName, PhoneNumber, Email, Password, " +
                                        "DateOfBirth, Gender, MembershipID, CreatedAt, UpdatedAt) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, first);
                            ps.setString(2, last);
                            ps.setString(3, phone);
                            ps.setString(4, email);
                            ps.setString(5, password);
                            ps.setDate(6, dob);
                            ps.setString(7, gender);
                            ps.setInt(8, membershipId);
                            
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("\n✅ Customer added successfully!");
                                retry = askToContinue(scanner, "add another customer");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("\n" + e.getMessage());
                            retry = askToRetry(scanner, "add customer");
                        } catch (SQLException e) {
                            System.err.println("\n❌ Database error: " + e.getMessage());
                            retry = askToRetry(scanner, "add customer");
                        } catch (Exception e) {
                            if (e.getMessage().equals("back")) {
                                retry = false;
                            }
                        }
                    }
                }
                case "3" -> {
                    System.out.println("\n🔙 Returning to main menu.");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    // ---------------- PRODUCT ----------------
    public static void manageProducts(Connection conn, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Product Management ---");
            System.out.println("1 - View Products");
            System.out.println("2 - Add Product");
            System.out.println("3 - Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    boolean retry = true;
                    while (retry) {
                        try (Statement stmt = conn.createStatement();
                             ResultSet rs = stmt.executeQuery("SELECT * FROM product")) {
                            System.out.println("\n--- Product List ---");
                            System.out.printf("%-10s %-20s %-10s %-10s %-10s %-15s %-10s%n",
                                    "ID", "Name", "Price", "Qty", "Active", "Supplier", "Category");
                            
                            while (rs.next()) {
                                System.out.printf("%-10d %-20s %-10.2f %-10d %-10s %-15d %-10d%n",
                                        rs.getInt("ProductID"),
                                        rs.getString("ProductName"),
                                        rs.getDouble("Price"),
                                        rs.getInt("StockQuantity"),
                                        rs.getBoolean("IsActive") ? "Yes" : "No",
                                        rs.getInt("SupplierID"),
                                        rs.getInt("CategoryID"));
                            }
                            retry = false;
                        } catch (SQLException e) {
                            System.err.println("\n❌ Error viewing products: " + e.getMessage());
                            retry = askToRetry(scanner, "view products");
                        }
                    }
                }
                case "2" -> {
                    boolean retry = true;
                    while (retry) {
                        try {
                            System.out.println("\n--- Add New Product --- (Enter 'back' to cancel)");
                            
                            System.out.print("Product Name: ");
                            String name = getInputOrBack(scanner);
                            if (name.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Description: ");
                            String desc = getInputOrBack(scanner);
                            if (desc.equalsIgnoreCase("back")) break;
                            
                            int stock = getValidIntInput(scanner, "Stock Quantity: ");
                            double price = getValidDoubleInput(scanner, "Price: ");
                            
                            System.out.print("Size: ");
                            String size = getInputOrBack(scanner);
                            if (size.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Brand: ");
                            String brand = getInputOrBack(scanner);
                            if (brand.equalsIgnoreCase("back")) break;
                            
                            System.out.print("SKU: ");
                            String sku = getInputOrBack(scanner);
                            if (sku.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Image URL: ");
                            String img = getInputOrBack(scanner);
                            if (img.equalsIgnoreCase("back")) break;
                            
                            System.out.print("Is Active (true/false): ");
                            String activeStr = getInputOrBack(scanner);
                            if (activeStr.equalsIgnoreCase("back")) break;
                            boolean isActive = Boolean.parseBoolean(activeStr);
                            
                            int supplierId = getValidIntInput(scanner, "Supplier ID: ");
                            int categoryId = getValidIntInput(scanner, "Category ID: ");

                            String sql = "INSERT INTO product (ProductName, ProductDesc, StockQuantity, Price, Size, " +
                                        "Brand, SKU, ImageURL, IsActive, SupplierID, CategoryID, CreatedAt, UpdatedAt) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, name);
                            ps.setString(2, desc);
                            ps.setInt(3, stock);
                            ps.setDouble(4, price);
                            ps.setString(5, size);
                            ps.setString(6, brand);
                            ps.setString(7, sku);
                            ps.setString(8, img);
                            ps.setBoolean(9, isActive);
                            ps.setInt(10, supplierId);
                            ps.setInt(11, categoryId);
                            
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("\n✅ Product added successfully!");
                                retry = askToContinue(scanner, "add another product");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("\n" + e.getMessage());
                            retry = askToRetry(scanner, "add product");
                        } catch (SQLException e) {
                            System.err.println("\n❌ Database error: " + e.getMessage());
                            retry = askToRetry(scanner, "add product");
                        } catch (Exception e) {
                            if (e.getMessage().equals("back")) {
                                retry = false;
                            }
                        }
                    }
                }
                case "3" -> {
                    System.out.println("\n🔙 Returning to main menu.");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    // ---------------- ORDER ----------------
    public static void manageOrders(Connection conn, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Order Management ---");
            System.out.println("1 - View Orders");
            System.out.println("2 - Create Order");
            System.out.println("3 - Update Order Status");
            System.out.println("4 - Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    boolean retry = true;
                    while (retry) {
                        try (Statement stmt = conn.createStatement();
                             ResultSet rs = stmt.executeQuery("SELECT * FROM `order`")) {
                            System.out.println("\n--- Order List ---");
                            System.out.printf("%-10s %-10s %-20s %-10s %-15s %-20s %-20s%n",
                                    "OrderID", "CustID", "Date", "Amount", "Status", "Created", "Updated");
                            
                            while (rs.next()) {
                                System.out.printf("%-10d %-10d %-20s %-10.2f %-15s %-20s %-20s%n",
                                        rs.getInt("OrderID"),
                                        rs.getInt("CustomerID"),
                                        rs.getTimestamp("OrderDate"),
                                        rs.getDouble("TotalAmount"),
                                        rs.getString("OrderStatus"),
                                        rs.getTimestamp("CreatedAt"),
                                        rs.getTimestamp("UpdatedAt"));
                            }
                            retry = false;
                        } catch (SQLException e) {
                            System.err.println("\n❌ Error viewing orders: " + e.getMessage());
                            retry = askToRetry(scanner, "view orders");
                        }
                    }
                }
                case "2" -> {
                    boolean retry = true;
                    while (retry) {
                        try {
                            System.out.println("\n--- Create New Order --- (Enter 'back' to cancel)");
                            
                            int customerId = getValidIntInput(scanner, "Customer ID: ");
                            double totalAmount = getValidDoubleInput(scanner, "Total Amount: ");
                            
                            System.out.print("Order Status: ");
                            String orderStatus = getInputOrBack(scanner);
                            if (orderStatus.equalsIgnoreCase("back")) break;

                            String sql = "INSERT INTO `order` (CustomerID, OrderDate, TotalAmount, OrderStatus, CreatedAt, UpdatedAt) " +
                                        "VALUES (?, CURRENT_TIMESTAMP, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
                            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setInt(1, customerId);
                            ps.setDouble(2, totalAmount);
                            ps.setString(3, orderStatus);
                            
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                ResultSet generatedKeys = ps.getGeneratedKeys();
                                if (generatedKeys.next()) {
                                    int orderId = generatedKeys.getInt(1);
                                    System.out.println("\n✅ Order created with ID: " + orderId);
                                    
                                    // Add order items
                                    boolean addItems = true;
                                    while (addItems) {
                                        System.out.print("\nAdd product to order? (y/n/back): ");
                                        String itemChoice = scanner.nextLine();
                                        
                                        if (itemChoice.equalsIgnoreCase("back")) {
                                            addItems = false;
                                            continue;
                                        } else if (!itemChoice.equalsIgnoreCase("y")) {
                                            addItems = false;
                                            continue;
                                        }
                                        
                                        try {
                                            int productId = getValidIntInput(scanner, "Product ID: ");
                                            int quantity = getValidIntInput(scanner, "Quantity: ");
                                            double unitPrice = getValidDoubleInput(scanner, "Unit Price: ");
                                            
                                            String itemSql = "INSERT INTO orderitem (OrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
                                            PreparedStatement itemPs = conn.prepareStatement(itemSql);
                                            itemPs.setInt(1, orderId);
                                            itemPs.setInt(2, productId);
                                            itemPs.setInt(3, quantity);
                                            itemPs.setDouble(4, unitPrice);
                                            itemPs.executeUpdate();
                                            System.out.println("✅ Product added to order.");
                                        } catch (NumberFormatException e) {
                                            System.err.println("\n" + e.getMessage());
                                        } catch (Exception e) {
                                            if (e.getMessage().equals("back")) {
                                                addItems = false;
                                            }
                                        }
                                    }
                                }
                                retry = askToContinue(scanner, "create another order");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("\n" + e.getMessage());
                            retry = askToRetry(scanner, "create order");
                        } catch (SQLException e) {
                            System.err.println("\n❌ Database error: " + e.getMessage());
                            retry = askToRetry(scanner, "create order");
                        } catch (Exception e) {
                            if (e.getMessage().equals("back")) {
                                retry = false;
                            }
                        }
                    }
                }
                case "3" -> {
                    boolean retry = true;
                    while (retry) {
                        try {
                            System.out.println("\n--- Update Order Status --- (Enter 'back' to cancel)");
                            
                            int orderId = getValidIntInput(scanner, "Order ID: ");
                            
                            System.out.print("New Status: ");
                            String newStatus = getInputOrBack(scanner);
                            if (newStatus.equalsIgnoreCase("back")) break;
                            
                            String sql = "UPDATE `order` SET OrderStatus = ?, UpdatedAt = CURRENT_TIMESTAMP WHERE OrderID = ?";
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setString(1, newStatus);
                            ps.setInt(2, orderId);
                            
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("\n✅ Order status updated!");
                            } else {
                                System.out.println("\n❌ No order found with ID: " + orderId);
                            }
                            retry = askToContinue(scanner, "update another order");
                        } catch (NumberFormatException e) {
                            System.err.println("\n" + e.getMessage());
                            retry = askToRetry(scanner, "update order");
                        } catch (SQLException e) {
                            System.err.println("\n❌ Database error: " + e.getMessage());
                            retry = askToRetry(scanner, "update order");
                        } catch (Exception e) {
                            if (e.getMessage().equals("back")) {
                                retry = false;
                            }
                        }
                    }
                }
                case "4" -> {
                    System.out.println("\n🔙 Returning to main menu.");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    // ---------------- PAYMENT ----------------
    public static void managePayments(Connection conn, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Payment Management ---");
            System.out.println("1 - View Payments");
            System.out.println("2 - Process Payment");
            System.out.println("3 - Update Payment Status");
            System.out.println("4 - Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    boolean retry = true;
                    while (retry) {
                        try (Statement stmt = conn.createStatement();
                             ResultSet rs = stmt.executeQuery(
                                 "SELECT p.PaymentID, p.OrderID, p.AmountPaid, " +
                                 "pm.Name as MethodName, ps.Name as StatusName, " +
                                 "p.PaymentDate FROM payment p " +
                                 "JOIN paymentmethod pm ON p.PaymentMethodID = pm.PaymentMethodID " +
                                 "JOIN paymentstatus ps ON p.PaymentStatusID = ps.PaymentStatusID")) {
                            
                            System.out.println("\n--- Payment List ---");
                            System.out.printf("%-10s %-10s %-10s %-20s %-15s %-20s%n",
                                    "PaymentID", "OrderID", "Amount", "Method", "Status", "Date");
                            
                            while (rs.next()) {
                                System.out.printf("%-10d %-10d %-10.2f %-20s %-15s %-20s%n",
                                        rs.getInt("PaymentID"),
                                        rs.getInt("OrderID"),
                                        rs.getDouble("AmountPaid"),
                                        rs.getString("MethodName"),
                                        rs.getString("StatusName"),
                                        rs.getTimestamp("PaymentDate"));
                            }
                            retry = false;
                        } catch (SQLException e) {
                            System.err.println("\n❌ Error viewing payments: " + e.getMessage());
                            retry = askToRetry(scanner, "view payments");
                        }
                    }
                }
                case "2" -> {
                    boolean retry = true;
                    while (retry) {
                        try {
                            System.out.println("\n--- Process Payment --- (Enter 'back' to cancel)");
                            
                            int orderId = getValidIntInput(scanner, "Order ID: ");
                            double amount = getValidDoubleInput(scanner, "Amount Paid: ");
                            int methodId = getValidIntInput(scanner, "Payment Method ID: ");
                            int statusId = getValidIntInput(scanner, "Payment Status ID: ");
                            
                            String sql = "INSERT INTO payment (OrderID, AmountPaid, PaymentMethodID, " +
                                        "PaymentStatusID, PaymentDate) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setInt(1, orderId);
                            ps.setDouble(2, amount);
                            ps.setInt(3, methodId);
                            ps.setInt(4, statusId);
                            
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                // Update order status to paid
                                String updateSql = "UPDATE `order` SET OrderStatus = 'Paid' WHERE OrderID = ?";
                                PreparedStatement updatePs = conn.prepareStatement(updateSql);
                                updatePs.setInt(1, orderId);
                                updatePs.executeUpdate();
                                
                                System.out.println("\n✅ Payment processed and order updated!");
                                retry = askToContinue(scanner, "process another payment");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("\n" + e.getMessage());
                            retry = askToRetry(scanner, "process payment");
                        } catch (SQLException e) {
                            System.err.println("\n❌ Database error: " + e.getMessage());
                            retry = askToRetry(scanner, "process payment");
                        } catch (Exception e) {
                            if (e.getMessage().equals("back")) {
                                retry = false;
                            }
                        }
                    }
                }
                case "3" -> {
                    boolean retry = true;
                    while (retry) {
                        try {
                            System.out.println("\n--- Update Payment Status --- (Enter 'back' to cancel)");
                            
                            int paymentId = getValidIntInput(scanner, "Payment ID: ");
                            int statusId = getValidIntInput(scanner, "New Status ID: ");
                            
                            String sql = "UPDATE payment SET PaymentStatusID = ? WHERE PaymentID = ?";
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setInt(1, statusId);
                            ps.setInt(2, paymentId);
                            
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("\n✅ Payment status updated!");
                            } else {
                                System.out.println("\n❌ No payment found with ID: " + paymentId);
                            }
                            retry = askToContinue(scanner, "update another payment");
                        } catch (NumberFormatException e) {
                            System.err.println("\n" + e.getMessage());
                            retry = askToRetry(scanner, "update payment");
                        } catch (SQLException e) {
                            System.err.println("\n❌ Database error: " + e.getMessage());
                            retry = askToRetry(scanner, "update payment");
                        } catch (Exception e) {
                            if (e.getMessage().equals("back")) {
                                retry = false;
                            }
                        }
                    }
                }
                case "4" -> {
                    System.out.println("\n🔙 Returning to main menu.");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    // ---------------- REPORTS ----------------
    public static void viewReportData(Connection conn, Scanner scanner) {
        try {
            while (true) {
                System.out.println("\n--- REPORT MENU ---");
                System.out.println("1 - List Orders with Customer & Payment");
                System.out.println("2 - Top 5 Best-Selling Products");
                System.out.println("3 - Total Paid per Customer");
                System.out.println("4 - Orders with Products & Customers");
                System.out.println("5 - Back to Main Menu");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
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
                    case "2" -> {
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
                    case "3" -> {
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
                    case "4" -> {
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
                    case "5" -> {
                        System.out.println("\n🔙 Returning to main menu.");
                        return;
                    }
                    default -> System.out.println("\n❌ Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("\n❌ Report error: " + e.getMessage());
        }
    }

    // ---------------- VIEWS ----------------
    public static void manageViews(Connection conn, Scanner scanner) {
        try (Statement stmt = conn.createStatement()) {
            // View 1: vw_customer_order_summary
            String view1 = "CREATE OR REPLACE VIEW vw_customer_order_summary AS " +
                    "SELECT  " +
                    "    c.CustomerID, " +
                    "    CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName, " +
                    "    c.Email, " +
                    "    COUNT(o.OrderID) AS TotalOrders, " +
                    "    SUM(o.TotalAmount) AS TotalSpent, " +
                    "    pm.Name AS MostUsedPaymentMethod, " +
                    "    ps.Name AS LastPaymentStatus, " +
                    "    MAX(o.OrderDate) AS LastOrderDate " +
                    "FROM Customer c " +
                    "LEFT JOIN `Order` o ON c.CustomerID = o.CustomerID " +
                    "LEFT JOIN Payment p ON o.OrderID = p.OrderID " +
                    "LEFT JOIN PaymentMethod pm ON p.PaymentMethodID = pm.PaymentMethodID " +
                    "LEFT JOIN PaymentStatus ps ON p.PaymentStatusID = ps.PaymentStatusID " +
                    "GROUP BY c.CustomerID, CustomerName, c.Email, pm.Name, ps.Name";

            stmt.execute(view1);
            System.out.println("✅ View vw_customer_order_summary created successfully.");

            // View 2: vw_productcatalog
            String view2 = "CREATE OR REPLACE VIEW vw_productcatalog AS " +
                    "SELECT p.ProductID, p.ProductName, p.Price, c.CategoryName, s.CompanyName AS Supplier " +
                    "FROM Product p " +
                    "JOIN Category c ON p.CategoryID = c.CategoryID " +
                    "JOIN Supplier s ON p.SupplierID = s.SupplierID " +
                    "WHERE p.IsActive = TRUE";

            stmt.execute(view2);
            System.out.println("✅ View vw_productcatalog created successfully.");

            // View 3: vw_productsalessummary
            String view3 = "CREATE OR REPLACE VIEW vw_productsalessummary AS " +
                    "SELECT p.productID, p.ProductName, p.Brand, " +
                    "       SUM(oi.Quantity) AS total_quantity_sold, " +
                    "       SUM(oi.Quantity * oi.UnitPrice) AS total_revenue " +
                    "FROM Product p " +
                    "JOIN OrderItem oi ON p.productID = oi.productID " +
                    "GROUP BY p.productId, p.ProductName, p.Brand";

            stmt.execute(view3);
            System.out.println("✅ View vw_productsalessummary created successfully.");

            // View 4: vw_carrier_performance
            String view4 = "CREATE OR REPLACE VIEW vw_carrier_performance AS " +
                    "SELECT ca.CarrierID, ca.CarrierName, COUNT(o.OrderID) AS TotalOrders " +
                    "FROM carrier ca " +
                    "JOIN `order` o ON ca.CarrierID = o.CarrierID " +
                    "GROUP BY ca.CarrierID, ca.CarrierName";

            stmt.execute(view4);
            System.out.println("✅ View vw_carrier_performance created successfully.");

            System.out.println("\nPress Enter to return to main menu...");
            scanner.nextLine();
        } catch (SQLException e) {
            System.err.println("❌ View Error: " + e.getMessage());
        }
    }
}