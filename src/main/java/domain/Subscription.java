/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 *
 * @author yeah2
 */
public class Subscription {
    private Integer subscriptionId = 0;
    private String name = "defaultName";
    private Boolean type = true; // rename this to paid?
    private BigDecimal subscriptionPrice = new BigDecimal(0);
    private String description = "defaultDescription";
    private String companyName = "defaultCompanyName";
    private LocalDateTime dueDate = LocalDateTime.now().plusMonths(1);
    private LocalDateTime issueDate = LocalDateTime.now();

    @Override
    public String toString() {
        return "Subscription{" + "subscriptionId=" + subscriptionId + ", name=" + name + ", type=" + type + ", subscriptionPrice=" + subscriptionPrice + ", description=" + description + ", companyName=" + companyName + ", dueDate=" + dueDate + ", issueDate=" + issueDate + '}';
    }     

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public BigDecimal getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(BigDecimal subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

}
