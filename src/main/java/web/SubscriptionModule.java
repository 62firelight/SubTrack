/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import org.jooby.Jooby;

/**
 *
 * @author emmabrothers
 */

import dao.SubscriptionDAO;
import domain.Customer;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Status;

/**
 *
 * @author emmabrothers
 */
public class SubscriptionModule extends Jooby{
   // saveSubscription
   // getSubscriptionsByUsername
    public SubscriptionModule(SubscriptionDAO subscriptionDao){
        get("/api/subscription/:username", (req) -> {
            String username = req.param("username").value();
            if(subscriptionDao.getSubscriptionsByUsername(username) == null){
                return new Result().status(Status.NOT_FOUND);
            }else{
                return subscriptionDao.getSubscriptionsByUsername(username);
            }
        });
        
            
    // used similar get method as CustomerModule.java as they both were 
    
}
}
    
    

