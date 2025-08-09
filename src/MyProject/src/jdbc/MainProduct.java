package jdbc;

import java.sql.*;
import java.util.Scanner;

public class MainProduct {
    public static void main(String[] args) {
    	 try (Connection conn = DriverManager.getConnection(
                 "jdbc:mysql://127.0.0.1:3306/ecommerce_db", "root", "kanombe12");
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- Product Management ---");
                System.out.println("1 - View Products");
                System.out.println("2 - Add Product");
                System.out.println("3 - Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    String selectQuery = "SELECT * FROM product";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(selectQuery)) {
                        while (rs.next()) {
                            Product p = new Product(
                                    rs.getInt("ProductID"),
                                    rs.getString("ProductName"),
                                    rs.getString("ProductDesc"),
                                    rs.getInt("StockQuantity"),
                                    rs.getDouble("Price"),
                                    rs.getString("Size"),
                                    rs.getString("Brand"),
                                    rs.getString("SKU"),
                                    rs.getString("ImageURL"),
                                    rs.getTimestamp("CreatedAt"),
                                    rs.getTimestamp("UpdatedAt"),
                                    rs.getBoolean("IsActive"),
                                    rs.getInt("SupplierID"),
                                    rs.getInt("CategoryID")
                            );
                            System.out.println(p);
                        }
                    }

                } else if (choice.equals("2")) {
                    String name, desc, size, brand, sku, imageUrl;
                    int stockQty = 0, supplierID = 0, categoryID = 0;
                    double price = 0;
                    boolean isActive;

                    System.out.print("Enter Product Name: ");
                    name = scanner.nextLine();

                    System.out.print("Enter Product Description: ");
                    desc = scanner.nextLine();

                    while (true) {
                        System.out.print("Enter Stock Quantity (number): ");
                        String input = scanner.nextLine();
                        try {
                            stockQty = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Please enter a valid number.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Price (e.g. 49.99): ");
                        String input = scanner.nextLine();
                        try {
                            price = Double.parseDouble(input);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Please enter a decimal number.");
                        }
                    }

                    System.out.print("Enter Size: ");
                    size = scanner.nextLine();

                    System.out.print("Enter Brand: ");
                    brand = scanner.nextLine();

                    System.out.print("Enter SKU: ");
                    sku = scanner.nextLine();

                    System.out.print("Enter Image URL: ");
                    imageUrl = scanner.nextLine();

                    while (true) {
                        System.out.print("Is the product active? (true/false): ");
                        String activeInput = scanner.nextLine();
                        if (activeInput.equalsIgnoreCase("true") || activeInput.equalsIgnoreCase("false")) {
                            isActive = Boolean.parseBoolean(activeInput);
                            break;
                        } else {
                            System.out.println("Invalid. Type 'true' or 'false'.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Supplier ID (number): ");
                        String input = scanner.nextLine();
                        try {
                            supplierID = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Please enter a number.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter Category ID (number): ");
                        String input = scanner.nextLine();
                        try {
                            categoryID = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid. Please enter a number.");
                        }
                    }

                    String insertSQL = "INSERT INTO product (ProductName, ProductDesc, StockQuantity, Price, Size, Brand, SKU, ImageURL, IsActive, SupplierID, CategoryID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, name);
                        pstmt.setString(2, desc);
                        pstmt.setInt(3, stockQty);
                        pstmt.setDouble(4, price);
                        pstmt.setString(5, size);
                        pstmt.setString(6, brand);
                        pstmt.setString(7, sku);
                        pstmt.setString(8, imageUrl);
                        pstmt.setBoolean(9, isActive);
                        pstmt.setInt(10, supplierID);
                        pstmt.setInt(11, categoryID);
                        pstmt.executeUpdate();
                        System.out.println("Product added successfully.");
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
