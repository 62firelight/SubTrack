/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author emmabrothers
 */
import dao.SubscriptionDAO;
import domain.Subscription;
import java.time.LocalDate;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Status;

/**
 *
 * @author emmabrothers
 */
public class SubscriptionModule extends Jooby {
    // saveSubscription
    // getSubscriptionsByUsername

    public SubscriptionModule(SubscriptionDAO subscriptionDao) {
        get("/api/subscriptions/:username", (req) -> {
            String username = req.param("username").value();
            if (subscriptionDao.getSubscriptionsByUsername(username) == null) {
                return new Result().status(Status.NOT_FOUND);
            } else {
                return subscriptionDao.getSubscriptionsByUsername(username);
            }
        });

        post("/api/subscriptions", (req, rsp) -> {
            Subscription subscription = req.body().to(Subscription.class);

            LocalDate dueDate = LocalDate.parse(subscription.getDueDate().substring(0, 10));
            System.out.println(dueDate);
            subscription.setDueDate(dueDate.toString());

            subscriptionDao.saveSubscription(subscription);
            rsp.status(Status.CREATED);
        });
    }
}
