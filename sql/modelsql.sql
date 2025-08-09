-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.7.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for ecommerce_db
DROP DATABASE IF EXISTS `ecommerce_db`;
CREATE DATABASE IF NOT EXISTS `ecommerce_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `ecommerce_db`;

-- Dumping structure for table ecommerce_db.address
DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `AddressID` int(11) NOT NULL AUTO_INCREMENT,
  `FullName` varchar(200) NOT NULL,
  `Street` varchar(255) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(50) NOT NULL,
  `PostalCode` varchar(20) NOT NULL,
  `Country` varchar(100) NOT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `AddressType` varchar(20) DEFAULT 'Shipping',
  `CustomerID` int(11) NOT NULL,
  `OrderID` int(11) NOT NULL,
  PRIMARY KEY (`AddressID`),
  UNIQUE KEY `uix_orderid_addresstype` (`OrderID`,`AddressType`),
  KEY `idx_address_customer` (`CustomerID`),
  KEY `idx_address_order` (`OrderID`),
  KEY `idx_address_type` (`AddressType`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`) ON DELETE CASCADE,
  CONSTRAINT `address_ibfk_2` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2055 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.address: ~6 rows (approximately)
DELETE FROM `address`;
INSERT INTO `address` (`AddressID`, `FullName`, `Street`, `City`, `State`, `PostalCode`, `Country`, `Phone`, `AddressType`, `CustomerID`, `OrderID`) VALUES
	(43, 'Jane Doe', '321 Work Plaza, Floor 5', 'Los Angeles', 'CA', '90211', 'USA', '+1-555-0102', 'Work', 2, 2),
	(44, 'Bob Johnson', '654 Pine Road', 'Chicago', 'IL', '60601', 'USA', '+1-555-0103', 'Home', 3, 3),
	(45, 'Alice Brown', '987 Elm Street', 'Houston', 'TX', '77001', 'USA', '+1-555-0104', 'Home', 4, 4),
	(46, 'Charlie Wilson', '147 Maple Drive', 'Phoenix', 'AZ', '85001', 'USA', '+1-555-0105', 'Home', 5, 5),
	(47, 'John Buteera', '369 Birch Court', 'San Antonio', 'TX', '78201', 'USA', '+1-555-0102', 'Billing', 2, 7),
	(48, 'Mucyo Kevin', '741 Willow Way', 'San Diego', 'CA', '92101', 'USA', '+1-555-0103', 'Work', 3, 8);

-- Dumping structure for table ecommerce_db.carrier
DROP TABLE IF EXISTS `carrier`;
CREATE TABLE IF NOT EXISTS `carrier` (
  `CarrierID` int(11) NOT NULL AUTO_INCREMENT,
  `CarrierName` varchar(100) NOT NULL,
  `ContactInfo` varchar(255) DEFAULT NULL,
  `Website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CarrierID`),
  UNIQUE KEY `CarrierName` (`CarrierName`),
  KEY `idx_carrier_name` (`CarrierName`)
) ENGINE=InnoDB AUTO_INCREMENT=1850 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.carrier: ~9 rows (approximately)
DELETE FROM `carrier`;
INSERT INTO `carrier` (`CarrierID`, `CarrierName`, `ContactInfo`, `Website`) VALUES
	(1, 'FedEx', '+1-800-463-3340', 'https://www.fedex.com/tracking'),
	(2, 'UPS', '+1-800-742-5878', 'https://www.ups.com'),
	(3, 'DHL', '+1-800-225-5345', 'https://www.dhl.com'),
	(4, 'USPS', '+1-800-275-8777', 'https://www.usps.com'),
	(5, 'Amazon Logistics', '+1-888-280-4331', 'https://logistics.amazon.com'),
	(6, 'OnTrac', '+1-800-334-5000', 'https://www.ontrac.com'),
	(7, 'LaserShip', '+1-804-414-2590', 'https://www.lasership.com'),
	(8, 'Canada Post', '+1-866-607-6301', 'https://www.canadapost.ca'),
	(10, 'DHL Express', '+1-800-225-5345', 'https://www.dhl.com');

-- Dumping structure for table ecommerce_db.cartitem
DROP TABLE IF EXISTS `cartitem`;
CREATE TABLE IF NOT EXISTS `cartitem` (
  `CartItemID` int(11) NOT NULL AUTO_INCREMENT,
  `Quantity` int(11) NOT NULL DEFAULT 1,
  `UnitPrice` decimal(10,2) NOT NULL,
  `AddedAt` timestamp NULL DEFAULT current_timestamp(),
  `UpdatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CartID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  PRIMARY KEY (`CartItemID`),
  KEY `idx_cartitem_cart` (`CartID`),
  KEY `idx_cartitem_product` (`ProductID`),
  CONSTRAINT `cartitem_ibfk_1` FOREIGN KEY (`CartID`) REFERENCES `shoppingcart` (`CartID`) ON DELETE CASCADE,
  CONSTRAINT `cartitem_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2023 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.cartitem: ~5 rows (approximately)
DELETE FROM `cartitem`;
INSERT INTO `cartitem` (`CartItemID`, `Quantity`, `UnitPrice`, `AddedAt`, `UpdatedAt`, `CartID`, `ProductID`) VALUES
	(18, 2, 899.99, '2025-08-01 08:30:00', '2025-08-01 08:30:00', 1, 1),
	(19, 1, 199.99, '2025-08-02 12:00:00', '2025-08-02 12:00:00', 1, 2),
	(20, 3, 15.99, '2025-08-03 10:15:00', '2025-08-03 10:15:00', 2, 3),
	(21, 1, 1299.99, '2025-08-04 14:45:00', '2025-08-04 14:45:00', 3, 4),
	(22, 2, 49.99, '2025-08-05 09:20:00', '2025-08-05 09:20:00', 3, 5);

-- Dumping structure for table ecommerce_db.category
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `CategoryID` int(11) NOT NULL AUTO_INCREMENT,
  `CategoryName` varchar(100) NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `CategoryDesc` text DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`CategoryID`),
  UNIQUE KEY `CategoryName` (`CategoryName`),
  KEY `idx_category_name` (`CategoryName`),
  KEY `idx_category_active` (`IsActive`)
) ENGINE=InnoDB AUTO_INCREMENT=1892 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.category: ~12 rows (approximately)
DELETE FROM `category`;
INSERT INTO `category` (`CategoryID`, `CategoryName`, `CreatedAt`, `CategoryDesc`, `IsActive`) VALUES
	(1, 'Electronics & Tech', '2025-08-05 08:30:36', 'Latest electronic devices, gadgets, and tech accessories', 1),
	(2, 'Clothing', '2025-08-05 08:30:36', 'Fashion apparel, shoes, and accessories for all ages', 1),
	(3, 'Books', '2025-08-05 08:30:36', 'Books and educational materials', 1),
	(4, 'Home & Garden', '2025-08-05 08:30:36', 'Home improvement and garden supplies', 1),
	(5, 'Smartphones', '2025-08-05 08:53:47', 'Mobile phones and accessories', 1),
	(6, 'Laptops', '2025-08-05 08:53:47', 'Portable computers and accessories', 1),
	(7, 'Men\'s Clothing', '2025-08-05 08:53:47', 'Clothing for men', 1),
	(8, 'Sports & Outdoor', '2025-08-07 07:52:00', 'Sports equipment and outdoor gear', 1),
	(9, 'Beauty & Health', '2025-08-07 07:52:00', 'Beauty products and health supplements', 1),
	(10, 'Automotive', '2025-08-07 07:52:00', 'Car parts and automotive accessories', 1),
	(11, 'Toys & Games', '2025-08-07 07:52:00', 'Toys for children and board games', 1),
	(16, 'Books & Media', '2025-08-07 07:58:10', 'Books, movies, music and digital content', 1);

-- Dumping structure for table ecommerce_db.coupon
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE IF NOT EXISTS `coupon` (
  `CouponID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(50) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `StartDate` date DEFAULT NULL,
  `EndDate` date DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL DEFAULT 1,
  `UsageCount` int(11) DEFAULT 0,
  `MembershipID` int(11) NOT NULL,
  PRIMARY KEY (`CouponID`),
  UNIQUE KEY `Code` (`Code`),
  KEY `MembershipID` (`MembershipID`),
  KEY `idx_coupon_code` (`Code`),
  KEY `idx_coupon_active` (`IsActive`),
  KEY `idx_coupon_dates` (`StartDate`,`EndDate`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`MembershipID`) REFERENCES `membership` (`MembershipID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=369 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.coupon: ~6 rows (approximately)
DELETE FROM `coupon`;
INSERT INTO `coupon` (`CouponID`, `Code`, `Type`, `StartDate`, `EndDate`, `IsActive`, `UsageCount`, `MembershipID`) VALUES
	(1, 'WELCOME20', 'Percentage', '2024-01-01', '2024-12-31', 1, 25, 1),
	(2, 'PREMIUM15', 'Percentage', '2024-02-01', '2024-11-30', 1, 15, 2),
	(3, 'VIP25', 'Percentage', '2024-01-15', '2024-12-15', 1, 0, 3),
	(4, 'SAVE50', 'Fixed Amount', '2024-03-01', '2024-09-30', 1, 0, 1),
	(6, 'STUDENT25', 'Percentage', '2024-01-15', '2024-12-15', 1, 0, 4),
	(7, 'SENIOR20', 'Percentage', '2024-01-01', '2024-12-31', 1, 0, 5);

-- Dumping structure for table ecommerce_db.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `CustomerID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `Gender` varchar(20) DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `UpdatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `MembershipID` int(11) NOT NULL,
  PRIMARY KEY (`CustomerID`),
  UNIQUE KEY `Email` (`Email`),
  KEY `idx_customer_email` (`Email`),
  KEY `idx_customer_name` (`LastName`,`FirstName`),
  KEY `idx_customer_membership` (`MembershipID`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`MembershipID`) REFERENCES `membership` (`MembershipID`)
) ENGINE=InnoDB AUTO_INCREMENT=1179 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.customer: ~11 rows (approximately)
DELETE FROM `customer`;
INSERT INTO `customer` (`CustomerID`, `FirstName`, `LastName`, `PhoneNumber`, `Email`, `Password`, `DateOfBirth`, `Gender`, `CreatedAt`, `UpdatedAt`, `MembershipID`) VALUES
	(1, 'John', 'Smith', '+15551234567', 'john.smith@example.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1985-05-15', 'Male', '2025-08-05 08:52:17', '2025-08-05 08:52:17', 1),
	(2, 'Emily', 'Johnson', '+15559876543', 'emily.j@example.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1990-08-22', 'Female', '2025-08-05 08:52:17', '2025-08-05 08:52:17', 2),
	(3, 'Michael', 'Williams', '+15554567890', 'michael.w@example.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '1982-11-30', 'Male', '2025-08-05 08:52:17', '2025-08-05 08:52:17', 3),
	(4, 'jack', 'j', NULL, 'jk@gmail.com', '12234455', '2025-08-06', 'Male', '2025-08-06 09:05:06', '2025-08-07 14:17:40', 2),
	(5, 'John', 'Anderson', '+1-555-9999', 'john.anderson.updated@email.com', 'hashed_password_1', '1990-05-15', 'Male', '2025-08-07 07:52:00', '2025-08-07 07:52:00', 1),
	(6, 'Sarah', 'Johnson', '+1-555-1002', 'sarah.johnson@email.com', 'hashed_password_2', '1985-08-22', 'Female', '2025-08-07 07:52:00', '2025-08-07 07:52:00', 3),
	(7, 'Michael', 'Brown', '+1-555-1003', 'michael.brown@email.com', 'hashed_password_3', '1992-12-03', 'Male', '2025-08-07 07:52:00', '2025-08-07 07:52:00', 3),
	(8, 'Emily', 'Davis', '+1-555-1004', 'emily.davis@email.com', 'hashed_password_4', '1988-07-18', 'Female', '2025-08-07 07:52:00', '2025-08-07 07:52:00', 1),
	(10, 'John', 'Anderson', '+1-555-1001', 'john.anderson@email.com', 'hashed_password_1', '1990-05-15', 'Male', '2025-08-07 07:58:10', '2025-08-07 07:58:10', 2),
	(11, 'David', 'Wilson', '+1-555-1005', 'david.wilson@email.com', 'hashed_password_5', '1995-03-10', 'Male', '2025-08-07 07:58:10', '2025-08-07 07:58:10', 5),
	(13, 'p', 'o', '9999', 'aa@gmail.com', '142425226', '1998-05-01', 'Male', '2025-08-07 09:38:05', '2025-08-07 14:17:33', 2);

-- Dumping structure for table ecommerce_db.membership
DROP TABLE IF EXISTS `membership`;
CREATE TABLE IF NOT EXISTS `membership` (
  `MembershipID` int(11) NOT NULL AUTO_INCREMENT,
  `MembershipType` varchar(100) NOT NULL,
  `Description` text DEFAULT NULL,
  `DiscountRate` decimal(5,2) DEFAULT NULL,
  `ShippingBenefit` varchar(50) DEFAULT NULL,
  `IsActive` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`MembershipID`),
  KEY `idx_membership_type` (`MembershipType`),
  KEY `idx_membership_active` (`IsActive`)
) ENGINE=InnoDB AUTO_INCREMENT=2015 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.membership: ~14 rows (approximately)
DELETE FROM `membership`;
INSERT INTO `membership` (`MembershipID`, `MembershipType`, `Description`, `DiscountRate`, `ShippingBenefit`, `IsActive`) VALUES
	(1, 'Basic', 'Enhanced basic membership with new features', 0.00, 'Free shipping on orders over $75', 1),
	(2, 'Premium', 'Premium membership with exclusive access to sales', 7.50, NULL, 1),
	(3, 'VIP', 'VIP membership with exclusive benefits', 10.00, NULL, 1),
	(4, 'Student', 'Special discounts for students with valid ID', 15.00, 'Free shipping on orders over $25', 1),
	(5, 'Senior', 'Exclusive benefits for senior citizens', 12.00, 'Free shipping on all orders', 1),
	(6, 'Corporate', 'Bulk purchase benefits for businesses', 8.00, 'Express shipping at standard rates', 1),
	(7, 'Platinum', 'Ultimate membership with maximum benefits', 20.00, 'Free express shipping worldwide', 1),
	(8, 'Family', 'Family plan with shared benefits', 7.50, 'Free shipping on orders over $50', 0),
	(9, 'Student', 'Special discounts for students with valid ID', 15.00, 'Free shipping on orders over $25', 1),
	(10, 'Senior', 'Exclusive benefits for senior citizens', 12.00, 'Free shipping on all orders', 1),
	(11, 'Corporate', 'Bulk purchase benefits for businesses', 8.00, 'Express shipping at standard rates', 1),
	(12, 'Student', 'Special discounts for students with valid ID', 15.00, 'Free shipping on orders over $25', 1),
	(13, 'Senior', 'Exclusive benefits for senior citizens', 12.00, 'Free shipping on all orders', 1),
	(14, 'Corporate', 'Bulk purchase benefits for businesses', 8.00, 'Express shipping at standard rates', 1);

-- Dumping structure for table ecommerce_db.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `OrderID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderNumber` varchar(50) NOT NULL,
  `OrderDate` timestamp NULL DEFAULT current_timestamp(),
  `RequiredDate` date DEFAULT NULL,
  `TotalAmount` decimal(12,2) NOT NULL,
  `OrderStatus` varchar(20) NOT NULL DEFAULT 'Pending',
  `PaymentMethod` varchar(20) NOT NULL,
  `PaymentStatus` varchar(20) NOT NULL DEFAULT 'Pending',
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `UpdatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CustomerID` int(11) NOT NULL,
  `CouponID` int(11) DEFAULT NULL,
  `Currency` varchar(3) NOT NULL DEFAULT 'USD',
  `ShippingCost` decimal(10,2) DEFAULT 0.00,
  `ShippedDate` date DEFAULT NULL,
  `DeliveredDate` date DEFAULT NULL,
  `ShippingMethod` varchar(50) NOT NULL,
  `CarrierID` int(11) DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  UNIQUE KEY `OrderNumber` (`OrderNumber`),
  KEY `CouponID` (`CouponID`),
  KEY `CarrierID` (`CarrierID`),
  KEY `idx_order_number` (`OrderNumber`),
  KEY `idx_order_customer` (`CustomerID`),
  KEY `idx_order_date` (`OrderDate`),
  KEY `idx_order_status` (`OrderStatus`),
  KEY `idx_order_payment_status` (`PaymentStatus`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`),
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`CouponID`) REFERENCES `coupon` (`CouponID`) ON DELETE SET NULL,
  CONSTRAINT `order_ibfk_3` FOREIGN KEY (`CarrierID`) REFERENCES `carrier` (`CarrierID`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=590 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.order: ~8 rows (approximately)
DELETE FROM `order`;
INSERT INTO `order` (`OrderID`, `OrderNumber`, `OrderDate`, `RequiredDate`, `TotalAmount`, `OrderStatus`, `PaymentMethod`, `PaymentStatus`, `CreatedAt`, `UpdatedAt`, `CustomerID`, `CouponID`, `Currency`, `ShippingCost`, `ShippedDate`, `DeliveredDate`, `ShippingMethod`, `CarrierID`) VALUES
	(2, 'ORD-2024-001', '2025-08-07 07:52:01', '2024-02-15', 1299.98, 'Shipped', 'Credit Card', 'Completed', '2025-08-07 07:52:01', '2025-08-07 07:52:01', 1, NULL, 'USD', 9.99, '2024-02-10', NULL, 'Standard', 1),
	(3, 'ORD-2024-002', '2025-08-07 07:52:01', '2024-02-20', 259.99, 'Delivered', 'PayPal', 'Completed', '2025-08-07 07:52:01', '2025-08-07 07:52:01', 2, NULL, 'USD', 0.00, NULL, '2024-02-22', 'Express', 2),
	(4, 'ORD-2024-003', '2025-08-07 07:52:01', '2024-02-25', 89.99, 'Shipped', 'Debit Card', 'Pending', '2025-08-07 07:52:01', '2025-08-07 21:01:41', 3, NULL, 'USD', 5.99, NULL, NULL, 'Standard', 1),
	(5, 'ORD-2024-004', '2025-08-07 07:52:01', '2024-03-01', 2599.98, 'Completed', 'Credit Card', 'Completed', '2025-08-07 07:52:01', '2025-08-07 07:52:01', 4, NULL, 'USD', 0.00, NULL, NULL, 'Express', 3),
	(7, 'ORD-2025-005', '2025-08-07 07:58:11', '2025-08-17', 1299.98, 'Processing', 'Credit Card', 'Completed', '2025-08-07 07:58:11', '2025-08-07 07:58:11', 10, NULL, 'USD', 9.99, NULL, NULL, 'Standard', 1),
	(8, 'ORD-2025-006', '2025-08-07 07:58:11', '2025-08-22', 259.99, 'Shipped', 'PayPal', 'Completed', '2025-08-07 07:58:11', '2025-08-07 07:58:11', 6, NULL, 'USD', 0.00, NULL, NULL, 'Express', 1),
	(9, 'ORD-2025-007', '2025-08-07 07:58:11', '2025-08-19', 999.99, 'Pending', 'Debit Card', 'Pending', '2025-08-07 07:58:11', '2025-08-07 07:58:11', 7, NULL, 'USD', 5.99, NULL, NULL, 'Standard', 1),
	(589, 'oopp', '2025-08-07 13:00:36', '2020-09-01', 344.00, 'pending', 'blik', 'completed ', '2025-08-07 13:00:36', '2025-08-07 13:00:36', 10, NULL, 'USD', 34.00, '2020-10-10', '2020-12-30', 'Express', 6);

-- Dumping structure for table ecommerce_db.orderitem
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE IF NOT EXISTS `orderitem` (
  `OrderItemID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderNumber` varchar(50) NOT NULL,
  `Quantity` int(11) NOT NULL DEFAULT 1,
  `UnitPrice` decimal(10,2) NOT NULL,
  `TotalPrice` decimal(12,2) NOT NULL,
  `ShipDate` date DEFAULT NULL,
  `Status` varchar(50) NOT NULL DEFAULT 'Pending',
  `VariantInfo` varchar(100) DEFAULT NULL,
  `ProductID` int(11) NOT NULL,
  `OrderID` int(11) NOT NULL,
  PRIMARY KEY (`OrderItemID`),
  KEY `idx_orderitem_order` (`OrderID`),
  KEY `idx_orderitem_product` (`ProductID`),
  KEY `idx_orderitem_number` (`OrderNumber`),
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`),
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2022 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.orderitem: ~9 rows (approximately)
DELETE FROM `orderitem`;
INSERT INTO `orderitem` (`OrderItemID`, `OrderNumber`, `Quantity`, `UnitPrice`, `TotalPrice`, `ShipDate`, `Status`, `VariantInfo`, `ProductID`, `OrderID`) VALUES
	(13, 'ORD-2024-002', 2, 599.99, 1199.98, '2024-01-21', 'Shipped', 'Phantom Black, 256GB', 3, 2),
	(14, 'ORD-2024-002', 1, 15.99, 15.99, '2024-01-21', 'Shipped', 'Blue, Portable', 8, 2),
	(15, 'ORD-2024-003', 1, 1299.99, 1299.99, '2024-01-26', 'Processing', 'Midnight, 8GB RAM', 2, 3),
	(16, 'ORD-2024-003', 1, 129.99, 129.99, '2024-01-26', 'Processing', 'Black, Size M', 7, 3),
	(17, 'ORD-2024-004', 1, 799.99, 799.99, '2024-02-01', 'Pending', 'Space Gray, 256GB', 4, 4),
	(18, 'ORD-2024-004', 2, 49.99, 99.98, '2024-02-01', 'Pending', 'iPhone 15 Compatible', 9, 4),
	(19, 'ORD-2024-005', 1, 1499.99, 1499.99, '2024-02-06', 'Cancelled', 'Platinum Silver, 16GB', 5, 5),
	(20, 'ORD-2024-007', 1, 899.99, 899.99, '2024-02-16', 'Shipped', 'Natural Titanium, 256GB', 1, 7),
	(21, 'ORD-2024-008', 2, 129.99, 259.98, '2024-02-20', 'Processing', 'Pink, Size S', 7, 8);

-- Dumping structure for table ecommerce_db.payment
DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `PaymentID` int(11) NOT NULL AUTO_INCREMENT,
  `AmountPaid` decimal(12,2) NOT NULL,
  `PaymentDate` timestamp NULL DEFAULT current_timestamp(),
  `OrderID` int(11) NOT NULL,
  `PaymentStatusID` int(11) NOT NULL,
  `PaymentMethodID` int(11) NOT NULL,
  PRIMARY KEY (`PaymentID`),
  KEY `PaymentMethodID` (`PaymentMethodID`),
  KEY `idx_payment_order` (`OrderID`),
  KEY `idx_payment_date` (`PaymentDate`),
  KEY `idx_payment_status` (`PaymentStatusID`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE,
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`PaymentStatusID`) REFERENCES `paymentstatus` (`PaymentStatusID`),
  CONSTRAINT `payment_ibfk_3` FOREIGN KEY (`PaymentMethodID`) REFERENCES `paymentmethod` (`PaymentMethodID`)
) ENGINE=InnoDB AUTO_INCREMENT=2014 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.payment: ~6 rows (approximately)
DELETE FROM `payment`;
INSERT INTO `payment` (`PaymentID`, `AmountPaid`, `PaymentDate`, `OrderID`, `PaymentStatusID`, `PaymentMethodID`) VALUES
	(7, 1199.98, '2024-01-21 09:00:00', 2, 2, 1),
	(8, 1315.98, '2024-01-26 13:00:00', 2, 2, 2),
	(9, 1499.99, '2024-02-06 11:00:00', 5, 3, 1),
	(10, 259.98, '2024-02-20 15:45:00', 8, 1, 2),
	(11, 899.99, '2024-02-16 10:20:00', 7, 2, 3),
	(2012, 90.00, '2025-08-07 14:03:18', 2, 3, 1);

-- Dumping structure for table ecommerce_db.paymentmethod
DROP TABLE IF EXISTS `paymentmethod`;
CREATE TABLE IF NOT EXISTS `paymentmethod` (
  `PaymentMethodID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`PaymentMethodID`),
  UNIQUE KEY `Name` (`Name`),
  KEY `idx_payment_method_name` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=1103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.paymentmethod: ~3 rows (approximately)
DELETE FROM `paymentmethod`;
INSERT INTO `paymentmethod` (`PaymentMethodID`, `Name`) VALUES
	(1, 'Credit Card'),
	(2, 'Debit Card'),
	(3, 'PayPal');

-- Dumping structure for table ecommerce_db.paymentstatus
DROP TABLE IF EXISTS `paymentstatus`;
CREATE TABLE IF NOT EXISTS `paymentstatus` (
  `PaymentStatusID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`PaymentStatusID`),
  UNIQUE KEY `Name` (`Name`),
  KEY `idx_payment_status_name` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=1639 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.paymentstatus: ~3 rows (approximately)
DELETE FROM `paymentstatus`;
INSERT INTO `paymentstatus` (`PaymentStatusID`, `Name`) VALUES
	(2, 'Completed'),
	(3, 'Failed'),
	(1, 'Pending');

-- Dumping structure for table ecommerce_db.product
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `ProductID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(255) NOT NULL,
  `ProductDesc` text DEFAULT NULL,
  `StockQuantity` int(11) NOT NULL DEFAULT 0,
  `Price` decimal(10,2) NOT NULL,
  `Size` varchar(50) DEFAULT NULL,
  `Brand` varchar(100) DEFAULT NULL,
  `SKU` varchar(50) DEFAULT NULL,
  `ImageURL` varchar(500) DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `UpdatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `IsActive` tinyint(1) NOT NULL DEFAULT 1,
  `SupplierID` int(11) NOT NULL,
  `CategoryID` int(11) NOT NULL,
  PRIMARY KEY (`ProductID`),
  UNIQUE KEY `SKU` (`SKU`),
  KEY `idx_product_name` (`ProductName`),
  KEY `idx_product_sku` (`SKU`),
  KEY `idx_product_supplier` (`SupplierID`),
  KEY `idx_product_category` (`CategoryID`),
  KEY `idx_product_active` (`IsActive`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`SupplierID`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`CategoryID`) REFERENCES `category` (`CategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=400 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.product: ~11 rows (approximately)
DELETE FROM `product`;
INSERT INTO `product` (`ProductID`, `ProductName`, `ProductDesc`, `StockQuantity`, `Price`, `Size`, `Brand`, `SKU`, `ImageURL`, `CreatedAt`, `UpdatedAt`, `IsActive`, `SupplierID`, `CategoryID`) VALUES
	(1, 'iPhone 15 Pro', 'Latest Apple smartphone with A17 Pro chip', 100, 999.00, NULL, 'Apple', 'IP15P-128-BLK', 'https://example.com/iphone15pro.jpg', '2025-08-05 08:56:31', '2025-08-05 08:56:31', 1, 1, 1),
	(2, 'MacBook Pro 14"', 'Apple laptop with M2 Pro chip', 45, 1999.00, '14-inch', 'Apple', 'MBP14-M2-512', 'https://example.com/mbp14.jpg', '2025-08-05 08:56:31', '2025-08-05 08:56:31', 1, 1, 2),
	(3, 'Men\'s Slim Fit Jeans', 'Classic blue denim jeans', 200, 49.99, '32x32', 'Levi\'s', 'LV-501-32B', 'https://example.com/levis501.jpg', '2025-08-05 08:56:31', '2025-08-05 08:56:31', 1, 2, 3),
	(4, 'The Silent Patient', 'Psychological thriller novel', 75, 14.99, NULL, 'Celadon Books', 'BK-TSP-2020', 'https://example.com/silentpatient.jpg', '2025-08-05 08:56:31', '2025-08-05 08:56:31', 1, 3, 5),
	(5, 'iPhone 14 Pro Max', 'iPhone 14 Pro Max with updated pricing and stock', 45, 1099.99, '6.7 inch', 'Apple', 'APL-IP14PM-256', 'https://images.com/iphone14.jpg', '2025-08-07 07:52:00', '2025-08-07 07:52:01', 1, 1, 1),
	(6, 'Nike Air Jordan 1', 'Classic basketball sneakers in retro colorway', 80, 175.00, '10.5', 'Nike', 'NIK-AJ1-105', 'https://images.com/airjordan1.jpg', '2025-08-07 07:52:00', '2025-08-07 07:52:01', 1, 2, 2),
	(7, 'MacBook Pro M2', 'Professional laptop with M2 chip and Retina display', 30, 2499.99, '16 inch', 'Apple', 'APL-MBP-M2-16', 'https://images.com/macbookpro.jpg', '2025-08-07 07:52:00', '2025-08-07 07:52:00', 1, 1, 1),
	(8, 'Levi\'s 501 Jeans', 'Classic straight-leg denim jeans', 120, 89.99, '32x34', 'Levi\'s', 'LEV-501-3234', 'https://images.com/levis501.jpg', '2025-08-07 07:52:00', '2025-08-07 07:52:01', 0, 2, 2),
	(9, 'The Great Gatsby', 'Classic American novel by F. Scott Fitzgerald', 200, 12.99, 'Paperback', 'Scribner', 'SCR-GG-PB', 'https://images.com/gatsby.jpg', '2025-08-07 07:52:00', '2025-08-07 07:52:00', 1, 3, 3),
	(10, 'Adidas Running Shoes', 'Comfortable running shoes for daily workouts', 60, 120.00, '9.5', 'Adidas', 'ADI-RUN-95', 'https://images.com/adidasrun.jpg', '2025-08-07 07:58:10', '2025-08-07 07:58:10', 1, 11, 8),
	(399, 'zara', 'rregrgrg', 12, 56.00, '0', 'adddidacs', 'xxxxx', 'httttp//', '2025-08-07 12:22:49', '2025-08-07 15:58:32', 1, 10, 2);

-- Dumping structure for view ecommerce_db.productcatalog
DROP VIEW IF EXISTS `productcatalog`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `productcatalog` (
	`ProductID` INT(11) NOT NULL,
	`product_name` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`Brand` VARCHAR(1) NULL COLLATE 'utf8mb4_unicode_ci',
	`price` DECIMAL(10,2) NOT NULL,
	`StockQuantity` INT(11) NOT NULL,
	`SupplierID` INT(11) NOT NULL,
	`supplier_name` VARCHAR(1) NULL COLLATE 'utf8mb4_unicode_ci',
	`Email` VARCHAR(1) NULL COLLATE 'utf8mb4_unicode_ci'
) ENGINE=MyISAM;

-- Dumping structure for view ecommerce_db.product_with_category
DROP VIEW IF EXISTS `product_with_category`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `product_with_category` (
	`productID` INT(11) NOT NULL,
	`productName` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`price` DECIMAL(10,2) NOT NULL,
	`stockQuantity` INT(11) NOT NULL,
	`sku` VARCHAR(1) NULL COLLATE 'utf8mb4_unicode_ci',
	`categoryName` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`categoryActive` TINYINT(1) NOT NULL,
	`createdAt` TIMESTAMP NULL
) ENGINE=MyISAM;

-- Dumping structure for table ecommerce_db.reviews
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE IF NOT EXISTS `reviews` (
  `ReviewID` int(11) NOT NULL AUTO_INCREMENT,
  `Rating` int(11) NOT NULL CHECK (`Rating` between 1 and 5),
  `Comment` text DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `UpdatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `ProductID` int(11) NOT NULL,
  `CustomerID` int(11) NOT NULL,
  PRIMARY KEY (`ReviewID`),
  UNIQUE KEY `Reviews_PK` (`ProductID`,`CustomerID`),
  KEY `idx_reviews_product` (`ProductID`),
  KEY `idx_reviews_customer` (`CustomerID`),
  KEY `idx_reviews_rating` (`Rating`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE,
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.reviews: ~5 rows (approximately)
DELETE FROM `reviews`;
INSERT INTO `reviews` (`ReviewID`, `Rating`, `Comment`, `CreatedAt`, `UpdatedAt`, `ProductID`, `CustomerID`) VALUES
	(1, 5, 'Amazing phone with great battery life!', '2024-01-31 23:00:00', '2024-01-31 23:00:00', 1, 1),
	(2, 4, 'Very good sound quality, but a bit pricey.', '2024-02-01 23:00:00', '2024-02-01 23:00:00', 2, 2),
	(3, 3, 'Works okay, but stopped charging after 6 months.', '2024-02-29 23:00:00', '2024-03-04 23:00:00', 3, 3),
	(4, 5, 'Top performance laptop for developers.', '2024-03-09 23:00:00', '2024-03-09 23:00:00', 4, 1),
	(5, 4, 'Protects well and fits nicely.', '2024-03-14 23:00:00', '2024-03-14 23:00:00', 5, 2);

-- Dumping structure for table ecommerce_db.shoppingcart
DROP TABLE IF EXISTS `shoppingcart`;
CREATE TABLE IF NOT EXISTS `shoppingcart` (
  `CartID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderStatus` varchar(45) DEFAULT 'Active',
  `UpdatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `TotalPrice` decimal(12,2) DEFAULT 0.00,
  `CustomerID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CartID`),
  UNIQUE KEY `ShoppingCart__IDX` (`CustomerID`),
  KEY `idx_cart_customer` (`CustomerID`),
  CONSTRAINT `shoppingcart_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.shoppingcart: ~3 rows (approximately)
DELETE FROM `shoppingcart`;
INSERT INTO `shoppingcart` (`CartID`, `OrderStatus`, `UpdatedAt`, `CreatedAt`, `TotalPrice`, `CustomerID`) VALUES
	(1, 'Active', '2025-08-05 08:57:28', '2025-08-05 08:57:28', 0.00, 1),
	(2, 'Active', '2025-08-05 08:57:28', '2025-08-05 08:57:28', 0.00, 2),
	(3, 'Active', '2025-08-05 08:57:28', '2025-08-05 08:57:28', 0.00, 3);

-- Dumping structure for table ecommerce_db.supplier
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `SupplierID` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyName` varchar(200) NOT NULL,
  `ContactName` varchar(200) DEFAULT NULL,
  `supplier_Address` varchar(500) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `IsActive` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`SupplierID`),
  KEY `idx_supplier_company` (`CompanyName`),
  KEY `idx_supplier_active` (`IsActive`)
) ENGINE=InnoDB AUTO_INCREMENT=2012 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.supplier: ~10 rows (approximately)
DELETE FROM `supplier`;
INSERT INTO `supplier` (`SupplierID`, `CompanyName`, `ContactName`, `supplier_Address`, `Phone`, `Email`, `CreatedAt`, `IsActive`) VALUES
	(1, 'TechSupply Co.', 'John Smith Jr.', '123 Tech Street, Silicon Valley, CA', '+1-555-0124', 'newcontact@techsupply.com', '2025-08-05 08:30:36', 1),
	(2, 'Fashion Hub', 'Jane Doe', '456 Fashion Ave, Suite 100, New York, NY 10001', '+1-555-0456', 'info@fashionhub.com', '2025-08-05 08:30:36', 1),
	(3, 'Book World', 'Mike Johnson', '789 Literature Blvd, Boston, MA', '+1-555-0789', 'sales@bookworld.com', '2025-08-05 08:30:36', 1),
	(4, 'Global Electronics Ltd', 'Robert Chen', '456 Innovation Drive, Austin, TX 78701', '+1-555-2001', 'contact@globalelectronics.com', '2025-08-07 07:52:00', 1),
	(5, 'Fashion Forward Inc', 'Lisa Martinez', '789 Style Boulevard, Los Angeles, CA 90210', '+1-555-2002', 'orders@fashionforward.com', '2025-08-07 07:52:00', 1),
	(6, 'Sports Gear Pro', 'Tom Wilson', '321 Athletic Way, Denver, CO 80202', '+1-555-2003', 'sales@sportsgear.com', '2025-08-07 07:52:00', 1),
	(7, 'Beauty Essentials Co', 'Anna Thompson', '654 Wellness Street, Miami, FL 33101', '+1-555-2004', 'info@beautyessentials.com', '2025-08-07 07:52:00', 1),
	(9, 'Global Electronics Ltd', 'Robert Chen', '456 Innovation Drive, Austin, TX 78701', '+1-555-2001', 'contact@globalelectronics.com', '2025-08-07 07:58:10', 1),
	(10, 'Fashion Forward Inc', 'Lisa Martinez', '789 Style Boulevard, Los Angeles, CA 90210', '+1-555-2002', 'orders@fashionforward.com', '2025-08-07 07:58:10', 1),
	(11, 'Sports Pro Supply', 'Mark Johnson', '321 Athletic Way, Denver, CO 80202', '+1-555-2003', 'info@sportspro.com', '2025-08-07 07:58:10', 1);

-- Dumping structure for view ecommerce_db.vw_carrier_performance
DROP VIEW IF EXISTS `vw_carrier_performance`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `vw_carrier_performance` (
	`CarrierID` INT(11) NOT NULL,
	`CarrierName` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`TotalOrders` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view ecommerce_db.vw_customer_order_summary
DROP VIEW IF EXISTS `vw_customer_order_summary`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `vw_customer_order_summary` (
	`CustomerID` INT(11) NOT NULL,
	`CustomerName` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`Email` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`TotalOrders` BIGINT(21) NOT NULL,
	`TotalSpent` DECIMAL(34,2) NULL,
	`MostUsedPaymentMethod` VARCHAR(1) NULL COLLATE 'utf8mb4_unicode_ci',
	`LastPaymentStatus` VARCHAR(1) NULL COLLATE 'utf8mb4_unicode_ci',
	`LastOrderDate` TIMESTAMP NULL
) ENGINE=MyISAM;

-- Dumping structure for view ecommerce_db.vw_productcatalog
DROP VIEW IF EXISTS `vw_productcatalog`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `vw_productcatalog` (
	`ProductID` INT(11) NOT NULL,
	`ProductName` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`Price` DECIMAL(10,2) NOT NULL,
	`CategoryName` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`Supplier` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci'
) ENGINE=MyISAM;

-- Dumping structure for view ecommerce_db.vw_productsalessummary
DROP VIEW IF EXISTS `vw_productsalessummary`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `vw_productsalessummary` (
	`productID` INT(11) NOT NULL,
	`ProductName` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`Brand` VARCHAR(1) NULL COLLATE 'utf8mb4_unicode_ci',
	`total_quantity_sold` DECIMAL(32,0) NULL,
	`total_revenue` DECIMAL(42,2) NULL
) ENGINE=MyISAM;

-- Dumping structure for table ecommerce_db.wishlist
DROP TABLE IF EXISTS `wishlist`;
CREATE TABLE IF NOT EXISTS `wishlist` (
  `WishlistID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Description` text DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT current_timestamp(),
  `UpdatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CustomerID` int(11) NOT NULL,
  PRIMARY KEY (`WishlistID`),
  KEY `idx_wishlist_customer` (`CustomerID`),
  KEY `idx_wishlist_name` (`Name`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.wishlist: ~2 rows (approximately)
DELETE FROM `wishlist`;
INSERT INTO `wishlist` (`WishlistID`, `Name`, `Description`, `CreatedAt`, `UpdatedAt`, `CustomerID`) VALUES
	(1, 'Tech Wishlist', NULL, '2025-08-05 09:01:38', '2025-08-05 09:01:38', 1),
	(2, 'Clothing Favorites', NULL, '2025-08-05 09:01:38', '2025-08-05 09:01:38', 2);

-- Dumping structure for table ecommerce_db.wishlistitem
DROP TABLE IF EXISTS `wishlistitem`;
CREATE TABLE IF NOT EXISTS `wishlistitem` (
  `WishlistItemID` int(11) NOT NULL AUTO_INCREMENT,
  `AddedAt` timestamp NULL DEFAULT current_timestamp(),
  `Notes` varchar(255) DEFAULT NULL,
  `WishlistID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  PRIMARY KEY (`WishlistItemID`),
  UNIQUE KEY `unique_wishlist_product` (`WishlistID`,`ProductID`),
  KEY `idx_wishlistitem_wishlist` (`WishlistID`),
  KEY `idx_wishlistitem_product` (`ProductID`),
  CONSTRAINT `wishlistitem_ibfk_1` FOREIGN KEY (`WishlistID`) REFERENCES `wishlist` (`WishlistID`) ON DELETE CASCADE,
  CONSTRAINT `wishlistitem_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2023 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table ecommerce_db.wishlistitem: ~2 rows (approximately)
DELETE FROM `wishlistitem`;
INSERT INTO `wishlistitem` (`WishlistItemID`, `AddedAt`, `Notes`, `WishlistID`, `ProductID`) VALUES
	(21, '2025-08-07 09:10:10', NULL, 1, 5),
	(22, '2025-08-07 09:10:10', NULL, 2, 8);

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `productcatalog`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `productcatalog` AS SELECT 
    p.ProductID,
    p.ProductName AS product_name,
    p.Brand,
    p.price,
    p.StockQuantity,
    s.SupplierID,
    s.ContactName AS supplier_name,
    s.Email
FROM Product p
JOIN Supplier s ON p.ProductID = s.SupplierID
WHERE p.StockQuantity > 0 
;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `product_with_category`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `product_with_category` AS SELECT 
    p.productID,
    p.productName,
    p.price,
    p.stockQuantity,
    p.sku,
    c.categoryName,
    c.isActive AS categoryActive,
    p.createdAt
FROM 
    product p
JOIN 
    category c ON p.categoryID = c.categoryID 
;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `vw_carrier_performance`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vw_carrier_performance` AS SELECT ca.CarrierID, ca.CarrierName, COUNT(o.OrderID) AS TotalOrders FROM carrier ca JOIN `order` o ON ca.CarrierID = o.CarrierID GROUP BY ca.CarrierID, ca.CarrierName 
;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `vw_customer_order_summary`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vw_customer_order_summary` AS SELECT      c.CustomerID,     CONCAT(c.FirstName, ' ', c.LastName) AS CustomerName,     c.Email,     COUNT(o.OrderID) AS TotalOrders,     SUM(o.TotalAmount) AS TotalSpent,     pm.Name AS MostUsedPaymentMethod,     ps.Name AS LastPaymentStatus,     MAX(o.OrderDate) AS LastOrderDate FROM Customer c LEFT JOIN `Order` o ON c.CustomerID = o.CustomerID LEFT JOIN Payment p ON o.OrderID = p.OrderID LEFT JOIN PaymentMethod pm ON p.PaymentMethodID = pm.PaymentMethodID LEFT JOIN PaymentStatus ps ON p.PaymentStatusID = ps.PaymentStatusID GROUP BY c.CustomerID, CustomerName, c.Email, pm.Name, ps.Name 
;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `vw_productcatalog`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vw_productcatalog` AS SELECT p.ProductID, p.ProductName, p.Price, c.CategoryName, s.CompanyName AS Supplier FROM Product p JOIN Category c ON p.CategoryID = c.CategoryID JOIN Supplier s ON p.SupplierID = s.SupplierID WHERE p.IsActive = TRUE 
;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `vw_productsalessummary`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vw_productsalessummary` AS SELECT p.productID, p.ProductName, p.Brand,        
SUM(oi.Quantity) AS total_quantity_sold,        
SUM(oi.Quantity * oi.UnitPrice) AS total_revenue 
FROM Product p 
JOIN OrderItem oi ON p.productID = oi.productID 
GROUP BY p.productId, p.ProductName, p.Brand 
;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
