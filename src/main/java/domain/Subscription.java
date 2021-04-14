/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;



import java.time.LocalDateTime;
/**
 *
 * @author yeah2
 */
public class Subscription {
    private String subsciptionID;
    private String subscriptionName;
    private LocalDateTime dateIssued;
    private LocalDateTime dateDue;
    private String subscriptionType;

    public String getSubsciptionID() {
        return subsciptionID;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public LocalDateTime getDateDue() {
        return dateDue;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubsciptionID(String subsciptionID) {
        this.subsciptionID = subsciptionID;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    public void setDateDue(LocalDateTime dateDue) {
        this.dateDue = dateDue;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
    
}
