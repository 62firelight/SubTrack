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
        
        // Create customer objects using data from test classes
        Customer cust1 = new Customer();
        cust1.setFirstName("Taine");
        cust1.setLastName("Bayly");
        cust1.setUsername("bayta267");
        cust1.setPassword("INFO310");
        cust1.setPhoneNumber("0273842");
        cust1.setEmailAddress("bayta@student.com");
        cust1.setCustomerId(1);
        
        Customer cust2 = new Customer();
        cust2.setFirstName("ne");
        cust2.setLastName("ly");
        cust2.setUsername("a267");
        cust2.setPassword("1234");
        cust2.setPhoneNumber("042");
        cust2.setEmailAddress("tudent.com");
        cust2.setCustomerId(2);
        
        // Test customer DAO's save method
        CustomerDAO customerDao = new CustomerJdbcDAO();
        
        customerDao.saveCustomer(cust1);
        customerDao.saveCustomer(cust2);
        
        // Check DAO's get customer and validate credentials method
        System.out.println(customerDao.getCustomer("bayta267"));
        
        System.out.println(customerDao.validateCredentials("bayta267", "INFO310"));
        System.out.println(customerDao.validateCredentials("bayta267", "wrong"));

        // Create subscription objects using data from test classes
        Subscription sub1 = new Subscription();
        sub1.setName("Netflix");
        sub1.setSubscriptionId(123);
        sub1.setPaid(false);
        sub1.setCategory("Leisure");
        sub1.setSubscriptionPrice(BigDecimal.TEN);
        sub1.setDescription("Movies and TV");
        sub1.setCompanyName("Netflix Inc.");
        // issue date and due dates are set to default values
        sub1.setCustomer(cust1);

        Subscription sub2 = new Subscription();
        sub2.setName("flix");
        sub2.setSubscriptionId(1234);
        sub2.setPaid(false);
        sub2.setCategory("Lei");
        sub2.setSubscriptionPrice(BigDecimal.TEN);
        sub2.setDescription("TV");
        sub2.setCompanyName("flix Inc.");
        // issue date and due dates are set to default values
        sub2.setCustomer(cust1);
        
        // Test subscription DAO's save operation
        SubscriptionDAO subscriptionDao = new SubscriptionJdbcDAO();
        
        subscriptionDao.saveSubscription(sub1);
        subscriptionDao.saveSubscription(sub2);

    }
    
}
