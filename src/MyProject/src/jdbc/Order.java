package jdbc;

import java.sql.Timestamp;
import java.sql.Date;

public class Order {
    private int orderID;
    private String orderNumber;
    private Timestamp orderDate;
    private Date requiredDate;
    private double totalAmount;
    private String orderStatus;
    private String paymentMethod;
    private String paymentStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int customerID;
    private Integer couponID;
    private String currency;
    private double shippingCost;
    private Date shippedDate;
    private Date deliveredDate;
    private String shippingMethod;
    private Integer carrierID;

    public Order() {}
 
    // Getters and setters for all fields
    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public Date getRequiredDate() { return requiredDate; }
    public void setRequiredDate(Date requiredDate) { this.requiredDate = requiredDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public Integer getCouponID() { return couponID; }
    public void setCouponID(Integer couponID) { this.couponID = couponID; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public double getShippingCost() { return shippingCost; }
    public void setShippingCost(double shippingCost) { this.shippingCost = shippingCost; }

    public Date getShippedDate() { return shippedDate; }
    public void setShippedDate(Date shippedDate) { this.shippedDate = shippedDate; }

    public Date getDeliveredDate() { return deliveredDate; }
    public void setDeliveredDate(Date deliveredDate) { this.deliveredDate = deliveredDate; }

    public String getShippingMethod() { return shippingMethod; }
    public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }

    public Integer getCarrierID() { return carrierID; }
    public void setCarrierID(Integer carrierID) { this.carrierID = carrierID; }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderDate=" + orderDate +
                ", requiredDate=" + requiredDate +
                ", totalAmount=" + totalAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", customerID=" + customerID +
                ", couponID=" + couponID +
                ", currency='" + currency + '\'' +
                ", shippingCost=" + shippingCost +
                ", shippedDate=" + shippedDate +
                ", deliveredDate=" + deliveredDate +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", carrierID=" + carrierID +
                '}';
    }

    // equals method for comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Order order = (Order) obj;
        return orderID == order.orderID;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Integer.hashCode(orderID);
    }
}
