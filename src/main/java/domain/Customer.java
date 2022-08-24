/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Objects;

/**
 *
 * @author yeah2
 */
public class Customer {

    private Integer customerId = 0;
    private String username = "defaultUsername";
    private String password = "defaultPassword";
//    private String firstName = "defaultFirstName";
//    private String lastName = "defaultLastName";
//    private String phoneNumber = "defaultPhoneNumber";
//    private String emailAddress = "defaultEmailAddress";
    private Integer reminderThreshold = 3;

    public Customer(Integer customerID, String username, String password, Integer reminderThreshold) {
        this.customerId = customerID;
        this.username = username;
        this.password = password;
        this.reminderThreshold = reminderThreshold;
    }

    public Customer() {

    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", username=" + username + ", password=" + password + ", reminderThreshold=" + reminderThreshold + '}';
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getReminderThreshold() {
        return reminderThreshold;
    }

    public void setReminderThreshold(Integer reminderThreshold) {
        this.reminderThreshold = reminderThreshold;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.customerId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        return true;
    }

}
