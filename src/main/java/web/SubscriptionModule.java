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
import java.math.BigDecimal;
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

            // decide whether a subscription is paid or not based on price
            if (subscription.getSubscriptionPrice().equals(BigDecimal.ZERO)) {
                subscription.setPaid(false);
            }

            subscriptionDao.saveSubscription(subscription);
            rsp.status(Status.CREATED);
        });

        get("api/categories/:username", (req) -> {
            String username = req.param("username").value();
            if (subscriptionDao.getCategories(username) == null) {
                return new Result().status(Status.NOT_FOUND);
            } else {
                return subscriptionDao.getCategories(username);
            }
        });
        get("api/categories/:category", (req) -> {
            String category = req.param("category").value();
            return subscriptionDao.filterByCategory(category);
        });
        delete("/api/subscriptions/:id", (req, rsp) -> {
            Integer id = Integer.valueOf(req.param("id").value());
            Subscription subscription = subscriptionDao.getSubscriptionById(id);

            subscriptionDao.deleteSubscription(subscription);
            rsp.status(Status.NO_CONTENT);
        });

        get("api/total/:username", (req) -> {
            String username = req.param("username").value();
            return subscriptionDao.getTotal(username);
        });

        put("/api/subscriptions/:id", (req, rsp) -> {
            Integer id = Integer.valueOf(req.param("id").value());
            Subscription subscription = subscriptionDao.getSubscriptionById(id);

            subscriptionDao.updateSubscription(subscription);
            rsp.status(Status.NO_CONTENT);
        });
    }
}
