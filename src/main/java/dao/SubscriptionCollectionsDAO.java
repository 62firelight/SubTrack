/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Subscription;
import java.util.Collection;

/**
 *
 * @author Luke Tang
 */
public class SubscriptionCollectionsDAO implements SubscriptionDAO {

    private static final Multimap<String, Subscription> subscriptions
            = HashMultimap.create();

    @Override
    public void saveSubscription(Subscription subscription) {
        subscriptions.put(subscription.getCustomer().getUsername(), 
                subscription);
    }

    @Override
    public Collection<Subscription> getSubscriptionsByUsername(String username) {
        System.out.println(subscriptions.get(username));
        return subscriptions.get(username);
    }

    @Override
    public Subscription getSubscriptionById(Integer id) {
        // loop through stored subscriptions and find the one with the right ID
        for (Subscription sub : subscriptions.values()) {
            if (sub.getSubscriptionId().equals(id)) {
                return sub;
            }
        }
        // ***there is probably a better way to find a matching sub***
        return null; // if no matching sub was found
    }

    @Override
    public void deleteSubscription(Subscription subscription) {
        if (subscriptions.containsKey(subscription.getCustomer().getUsername())) {
            subscriptions.remove(subscription.getCustomer().getUsername(), subscription);
        }
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        subscriptions.put(subscription.getCustomer().getUsername(), subscription);
    }

}
