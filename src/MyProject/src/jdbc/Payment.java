package jdbc;

import java.sql.Timestamp;

public class Payment {
    private int paymentID;
    private double amountPaid;
    private Timestamp paymentDate;
    private int orderID;
    private int paymentStatusID;
    private int paymentMethodID;

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getPaymentStatusID() {
        return paymentStatusID;
    }

    public void setPaymentStatusID(int paymentStatusID) {
        this.paymentStatusID = paymentStatusID;
    }

    public int getPaymentMethodID() {
        return paymentMethodID;
    }

    public void setPaymentMethodID(int paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", amountPaid=" + amountPaid +
                ", paymentDate=" + paymentDate +
                ", orderID=" + orderID +
                ", paymentStatusID=" + paymentStatusID +
                ", paymentMethodID=" + paymentMethodID +
                '}';
    }
}
