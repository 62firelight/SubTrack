/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author yeah2
 */
public class Customer {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Integer contactNum;
    private String emailAddress;
    //private Integer cardNum;

    public Integer getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getContactNum() {
        return contactNum;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContactNum(Integer contactNum) {
        this.contactNum = contactNum;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    
}
