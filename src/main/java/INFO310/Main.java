/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INFO310;

import dao.CustomerDAO;
import dao.CustomerJdbcDAO;
import dao.SubscriptionCollectionsDAO;
import dao.SubscriptionDAO;
import dao.SubscriptionJdbcDAO;
import domain.Customer;
import domain.Subscription;
import java.math.BigDecimal;

/**
 *
 * @author yeah2
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello");    
        
//        Test data for until test classes work
//        Customer cust1 = new Customer();
//        cust1.setFirstName("Taine");
//        cust1.setLastName("Bayly");
//        cust1.setUsername("bayta267");
//        cust1.setPassword("INFO310");
//        cust1.setPhoneNumber("0273842");
//        cust1.setEmailAddress("bayta@student.com");
//        cust1.setCustomerId(1);
//        
//        Customer cust2 = new Customer();
//        cust2.setFirstName("ne");
//        cust2.setLastName("ly");
//        cust2.setUsername("a267");
//        cust2.setPassword("1234");
//        cust2.setPhoneNumber("042");
//        cust2.setEmailAddress("tudent.com");
//        cust2.setCustomerId(2);
//        
//        CustomerDAO customerDao = new CustomerJdbcDAO();
//        customerDao.saveCustomer(cust1);
//        customerDao.saveCustomer(cust2);
//        
//        System.out.println(customerDao.getCustomer("bayta267"));
//        
//        System.out.println(customerDao.validateCredentials("bayta267", "INFO310"));
//        System.out.println(customerDao.validateCredentials("bayta267", "wrong"));

    }
    
}
