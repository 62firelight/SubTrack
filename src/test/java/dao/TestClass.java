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

    @BeforeEach
    public void setUp() {
       

    CustomerCollectionsDAO CustDAO = new CustomerCollectionsDAO();
    Customer cust1;
    Customer cust2;

        Subscription sub1 = new Subscription();
        sub1.setName("Netflix");
        sub1.setSubscriptionId(123);
        sub1.setPaid(false);
        sub1.setCategory("Leisure");
        sub1.setSubscriptionPrice(BigDecimal.TEN);
        sub1.setDescription("Movies and TV");
        sub1.setCompanyName("Netflix Inc.");
       
                }

    @AfterEach
    public void tearDown() {
        
    }

    @Test
    public void testSomething() {
        assertThat(1 + 1, equalTo(2));
    }
    
}
