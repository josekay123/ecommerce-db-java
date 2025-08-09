# 🛒 E-Commerce Database Design & Java Implementation

## 📖 Overview
This project is a **complete e-commerce database system** designed using **MariaDB/MySQL** and implemented in **Java (JDBC)**.  
It models real-world e-commerce operations such as:

- Customer management
- Product catalog and inventory
- Order processing
- Payment handling
- Shopping carts and wishlists
- Coupons and loyalty programs
- Reviews and ratings

---

## 📂 Project Structure
ecommerce-db-java/
│
├── docs/
│ ├── Ecom documentation.pdf # Full database design & explanation
│ ├── Logical.pdf # ER diagram / logical model
│
├── sql/
│ └── modelsql.sql # Database schema & sample data
│
├── src/
│ └── (Java source code) # DAO classes, JDBC connection, etc.
│
├── README.md # Project documentation
└── .gitignore # Ignored files for Git


---

## 🗄 Database Design

### Main Entity Groups
- **Customer Management**
  - `Customer`, `Membership`
- **Product Management**
  - `Product`, `Category`, `Supplier`
- **Order Management**
  - `Order`, `OrderItem`, `Address`
- **Payment System**
  - `Payment`, `PaymentMethod`, `PaymentStatus`
- **Shopping Experience**
  - `ShoppingCart`, `CartItem`, `Wishlist`, `WishlistItem`
- **Marketing & Loyalty**
  - `Coupon`, `Carrier`
- **Feedback**
  - `Reviews`

---

## 🔗 Key Relationships
- One customer can place multiple orders.  
- One order can contain multiple order items.  
- One product can appear in multiple order items.  
- One category can have multiple products.  
- One customer has one active shopping cart.  
- One membership level can have multiple coupons.  

Full details are available in **`docs/Ecom documentation.pdf`**.

---

## 🛠 Technology Stack
- **Backend**: Java 22 (OpenJDK)
- **Database**: MariaDB 10.6 (MySQL compatible)
- **Libraries**: JDBC, JFreeChart (for reports/visuals)
- **Build Tools**: Maven / Gradle
- **Database Views**:
  - `ProductCatalog` – Active products with category/supplier
  - `vw_productsalessummary` – Sales quantity & revenue per product
  - `CustomerOrderHistory` – Full order history per customer

---

## ▶️ Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/josekay123/ecommerce-db-java.git
cd ecommerce-db-java
2. Set up the database
bash
mysql -u root -p < sql/modelsql.sql

3.Configure Java application

Create or edit a db.propertiesfile:

properties

db.url=jdbc:mysql://localhost:3306/ecommerce_db
db.username=root
db.password=yourpassword

4. Run the application
bash

mvn clean install
java -jar target/ecommerce-db-java.jar

✍️ Author
Joseph Kayijuka
