package jdbc;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Customer entity class representing a customer in the database
 */
public class Customer {
    
    // Private fields
    private int customerID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String gender;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int membershipID;
    
    // Default constructor
    public Customer() {
    }
    
    // Constructor with all fields
    public Customer(int customerID, String firstName, String lastName, 
                   String phoneNumber, String email, String password, 
                   Date dateOfBirth, String gender, Timestamp createdAt, 
                   Timestamp updatedAt, int membershipID) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.membershipID = membershipID;
    }
    
    // Constructor without timestamps (for new customers)
    public Customer(String firstName, String lastName, String phoneNumber, 
                   String email, String password, Date dateOfBirth, 
                   String gender, int membershipID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.membershipID = membershipID;
    }
    
    // Getter and Setter methods
    public int getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public int getMembershipID() {
        return membershipID;
    }
    
    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
    }
    
    // Utility methods
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    // toString method for easy printing
    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", membershipID=" + membershipID +
                '}';
    }
    
    // equals method for comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Customer customer = (Customer) obj;
        return customerID == customer.customerID;
    }
    
    // hashCode method
    @Override
    public int hashCode() {
        return Integer.hashCode(customerID);
    }
}