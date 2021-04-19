/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Subscription;
import java.util.Collection;

/**
 *
 * @author Luke Tang
 */
public interface SubscriptionDAO {

    void saveSubscription(Subscription subscription);

    Collection<Subscription> getSubscriptionsByUsername(String username);
}