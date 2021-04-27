/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import domain.Subscription;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author trbay
 */
public class SubscriptionsCollectionsDAOTest {

    private SubscriptionCollectionsDAO subDAO = new SubscriptionCollectionsDAO();
    private Subscription sub1;
    private Subscription sub2;
    private Subscription sub3;

    @BeforeEach
    public void setUp() {
        
        Customer cust1 = new Customer();
        cust1.setFirstName("Taine");
        cust1.setLastName("Bayly");
        cust1.setUsername("bayta267");
        cust1.setPassword("INFO310");
        cust1.setPhoneNumber("0273842");
        cust1.setEmailAddress("bayta@student.com");
        cust1.setCustomerId(1);
        
        Customer cust2 = new Customer();
        cust2.setFirstName("Luke");
        cust2.setLastName("Tang");
        cust2.setUsername("tanlu824");
        cust2.setPassword("INFO310");
        cust2.setPhoneNumber("0276292");
        cust2.setEmailAddress("tanlu@student.com");
        cust2.setCustomerId(2);

        sub1 = new Subscription();
        this.sub1.setName("Netflix");
        this.sub1.setSubscriptionId(123);
        this.sub1.setPaid(false);
        this.sub1.setCategory("Leisure");
        this.sub1.setSubscriptionPrice(BigDecimal.TEN);
        this.sub1.setDescription("Movies and TV");
        this.sub1.setCompanyName("Netflix Inc.");
        this.sub1.setCustomer(cust1);

        sub2 = new Subscription();
        this.sub2.setName("flix");
        this.sub2.setSubscriptionId(1234);
        this.sub2.setPaid(false);
        this.sub2.setCategory("Lei");
        this.sub2.setSubscriptionPrice(BigDecimal.TEN);
        this.sub2.setDescription("TV");
        this.sub2.setCompanyName("flix Inc.");
        this.sub2.setCustomer(cust1);

        sub3 = new Subscription();
        this.sub3.setName("Shares");
        this.sub3.setSubscriptionId(1235);
        this.sub3.setPaid(true);
        this.sub3.setCategory("Business");
        this.sub3.setSubscriptionPrice(BigDecimal.TEN);
        this.sub3.setDescription("Investing");
        this.sub3.setCompanyName("Sharsies");
        this.sub3.setCustomer(cust2);

        subDAO.saveSubscription(sub1);
        subDAO.saveSubscription(sub2);

    }

    @AfterEach
    public void tearDown() {
        subDAO.deleteSubscription(sub1);
        subDAO.deleteSubscription(sub2);
        subDAO.deleteSubscription(sub3);
    }

    @Test
    public void testGetSubscriptionByUsername() {

        Collection<Subscription> collection = 
                subDAO.getSubscriptionsByUsername(sub1.getCustomer().getUsername());
        //assertThat(subDAO.hasItem(1));
        assertTrue(collection.contains(sub1));
        assertTrue(collection.contains(sub2));
        assertFalse(collection.contains(sub3));
    }

    @Test
    public void testSaveSubscription() {
        //Testing a new user saving subscription, whilst another user exists already. Test of user Independence
        subDAO.saveSubscription(sub3);
        Collection<Subscription> collection = 
                subDAO.getSubscriptionsByUsername(sub3.getCustomer().getUsername());
        assertTrue(collection.contains(sub3));
        assertFalse(collection.contains(sub2));
        assertFalse(collection.contains(sub1));
        System.out.println(sub3);

        //Testing the other independent user has saved items from setup
        Collection<Subscription> collection2 = 
                subDAO.getSubscriptionsByUsername(sub1.getCustomer().getUsername());
        assertThat(collection2, hasItem(sub1));
        assertThat(collection2, hasItem(sub2));
        assertFalse(collection2.contains(sub3));

    }

    @Test
    public void testDeleteSubscription() {
        subDAO.deleteSubscription(sub1);
        Collection<Subscription> collection = 
                subDAO.getSubscriptionsByUsername(sub1.getCustomer().getUsername());
        assertTrue(collection.contains(sub2));
        assertFalse(collection.contains(sub1));
        //assertNull(CustDAO.getCustomer("bayta267"));
        //assertThat(1 + 1, equalTo(2));
    }

    @Test
    public void testUpdateSubscription() {
        //assertThat(1 + 1, equalTo(2));
    }

}
