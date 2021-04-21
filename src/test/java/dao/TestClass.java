/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import domain.Subscription;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Luke Tang
 */
public class TestClass {
    //Format code below doesnt seem to allow the test to run on my computer
    /*private CustomerCollectionsDAO CustDAO = new CustomerCollectionsDAO();
    //private Customer cust1 = new Customer();
    private Customer cust2 = new Customer();*/

    @BeforeEach
    public void setUp() {
        CustomerCollectionsDAO CustDAO = new CustomerCollectionsDAO();
        Customer cust1 = new Customer();
        Customer cust2 = new Customer();
        
        Subscription sub1 = new Subscription();
        sub1.setName("Netflix");
        sub1.setSubscriptionId(123);
        sub1.setPaid(false);
        sub1.setCategory("Leisure");
        sub1.setSubscriptionPrice(BigDecimal.TEN);
        sub1.setDescription("Movies and TV");
        sub1.setCompanyName("Netflix Inc.");
        //This customer setup becomes redundant due to my set up
        cust1.setFirstName("Taine");
        cust1.setLastName("Bayly");
        cust1.setUsername("bayta267");
        cust1.setPassword("INFO310");
        cust1.setPhoneNumber("0273842");
        cust1.setEmailAddress("bayta@student.com");
        cust1.setCustomerId(1);

        //CustDAO.saveCustomer(cust1);
    }
    
    @AfterEach
    public void tearDown() {
        
    }
    
    @Test
    public void testSaveCustomer() {
        
        //Weird set up in test method due to errors I was having using setup.
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
        cust2.setPassword("INF0");
        cust2.setPhoneNumber("042");
        cust2.setEmailAddress("tudent.com");
        cust2.setCustomerId(2);
        
        CustomerCollectionsDAO CustDAO = new CustomerCollectionsDAO();
        CustDAO.saveCustomer(cust1);

        //Use of assert equals to check that get customer method, actually retrieves customer 1 from list based of username.
        //This test also confirms our save function is working successfully
        Customer retrieved = CustDAO.getCustomer("bayta267");
        
        assertEquals("Retreived customer should be the same", cust1, retrieved);
        
        
        /**assertTrue("cust1 should exist", CustDAO.contains(cust1));*/
        
        //Follow up testing of save and get function testing manual attributes
        CustDAO.saveCustomer(cust2);
        Customer secondRetreieved = CustDAO.getCustomer("a267");
        
        //Manual checks of each function
        assertEquals(cust2.getFirstName(), secondRetreieved.getFirstName());
        assertEquals(cust2.getLastName(), secondRetreieved.getLastName());
        assertEquals(cust2.getUsername(), secondRetreieved.getUsername());
        assertEquals(cust2.getPassword(), secondRetreieved.getPassword());
        assertEquals(cust2.getPhoneNumber(), secondRetreieved.getPhoneNumber());
        assertEquals(cust2.getEmailAddress(), secondRetreieved.getEmailAddress());
        assertEquals(cust2.getCustomerId(), secondRetreieved.getCustomerId());
        
    }
    
}
