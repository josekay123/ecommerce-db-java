package jdbc;

import java.sql.Timestamp;
import java.util.Objects;

public class Product {
    private int productID;
    private String productName;
    private String productDesc;
    private int stockQuantity;
    private double price;
    private String size;
    private String brand;
    private String sku;
    private String imageURL;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isActive;
    private int supplierID;
    private int categoryID;

    public Product() {}

    public Product(int productID, String productName, String productDesc, int stockQuantity, double price,
                   String size, String brand, String sku, String imageURL, Timestamp createdAt,
                   Timestamp updatedAt, boolean isActive, int supplierID, int categoryID) {
        this.productID = productID;
        this.productName = productName;
        this.productDesc = productDesc;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.size = size;
        this.brand = brand;
        this.sku = sku;
        this.imageURL = imageURL;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.supplierID = supplierID;
        this.categoryID = categoryID;
    }

    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductDesc() { return productDesc; }
    public void setProductDesc(String productDesc) { this.productDesc = productDesc; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public int getSupplierID() { return supplierID; }
    public void setSupplierID(int supplierID) { this.supplierID = supplierID; }
    public int getCategoryID() { return categoryID; }
    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean isValidPrice() {
        return price >= 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", brand='" + brand + '\'' +
                ", sku='" + sku + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                ", supplierID=" + supplierID +
                ", categoryID=" + categoryID +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return productID == product.productID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID);
    }
}