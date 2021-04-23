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
    public void saveSubscription(String username, Subscription subscription) {
        subscriptions.put(username, subscription);
    }

    @Override
    public Collection<Subscription> getSubscriptionsByUsername(String username) {
        return subscriptions.get(username);
    }

    @Override
    public void deleteSubscription(String username, Subscription subscription) {
        if (subscriptions.containsKey(username)) {
            subscriptions.remove(username, subscription);
        }
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
