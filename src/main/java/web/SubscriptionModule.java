package web;

/**
 *
 * @author emmabrothers
 */
import dao.SubscriptionDAO;
import domain.Customer;
import domain.Subscription;
import domain.Total;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author emmabrothers
 */
public class SubscriptionModule extends Jooby {

    public SubscriptionModule(SubscriptionDAO subscriptionDao) {
        get("/api/subscriptions/{username}", ctx -> {
            String username = ctx.path("username").value();
            Collection<Subscription> subs = subscriptionDao.getSubscriptionsByUsername(username);

            if (subs == null) {
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return subs;
            }
        });

        post("/api/subscriptions", ctx -> {
            Subscription subscription = ctx.body().to(Subscription.class);

            // perform date conversion to avoid errors when storing in database
            LocalDate dueDate = LocalDate.parse(subscription.getDueDate().substring(0, 10));
//            dueDate = dueDate.plusDays(1); // for accurate date
            System.out.println(dueDate);
            subscription.setDueDate(dueDate.toString());

            // decide whether a subscription is paid or not based on price
            if (subscription.getSubscriptionPrice().equals(BigDecimal.ZERO)) {
                subscription.setPaid(false);
            }

            subscriptionDao.saveSubscription(subscription);

            Customer c = subscription.getCustomer();
            String customerEmail = c.getEmailAddress();
            String fName = c.getFirstName();
            String lName = c.getLastName();
            String subName = subscription.getName();
            String date = subscription.getDueDate();
            CompletableFuture.runAsync(() -> {
                try {
                    Email email = new SimpleEmail();
                    email.setHostName("localhost");
                    email.setSmtpPort(2525);
                    email.setFrom("SubTrack@gmail.com");
                    email.setSubject("Subscription Renewal Warning");
                    email.setMsg("Hi " + fName + ", \n\nThis is an email to "
                            + "warn you that your " + subName + " subscription "
                            + "expires soon on "
                            + date + ".\n\nRemember to renew or delete your "
                            + "subscription before it's too late!\n\n"
                            + "This message was automatically generated "
                            + "by SubTrack. Change your account settings "
                            + "if you wish to receive notifications at a "
                            + "different time.");
                    email.addTo(customerEmail);
                    email.send();
                } catch (EmailException ex) {
//                    Logger.getLogger(SubscriptionModule.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                    System.out.println("Couldn't send an email to the FakeSMTP server. Make sure that the FakeSMTP server is active and listening on port 2525.");
                    System.out.println("Don't have FakeSMTP? Download it from http://nilhcem.com/FakeSMTP/");
                }
            });

            return ctx.send(StatusCode.CREATED);
        });

        get("/api/categories/{username}", ctx -> {
            String username = ctx.path("username").value();
            Collection<String> subs = subscriptionDao.getCategories(username);

            if (subs == null) {
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return subscriptionDao.getCategories(username);
            }
        });

        get("/api/categories/{category}/{username}", ctx -> {
            String category = ctx.path("category").value();
            String username = ctx.path("username").value();
            Collection<Subscription> subs = subscriptionDao.filterByCategory(category, username);

            return subs;
        });

        delete("/api/subscriptions/{id}", ctx -> {
            Integer id = Integer.valueOf(ctx.path("id").value());
            Subscription subscription = subscriptionDao.getSubscriptionById(id);
            subscriptionDao.deleteSubscription(subscription);

            return ctx.send(StatusCode.NO_CONTENT);
        });

        get("/api/total/{username}", ctx -> {
            String username = ctx.path("username").value();
            Total total = subscriptionDao.getTotal(username);

            return total;
        });

        put("/api/subscriptions/{id}", ctx -> {
            Subscription subscription = ctx.body().to(Subscription.class);

            // perform date conversion to avoid errors when storing in database
            LocalDate dueDate = LocalDate.parse(subscription.getDueDate().substring(0, 10));
//            dueDate = dueDate.plusDays(1); // for accurate date
            System.out.println(dueDate);
            subscription.setDueDate(dueDate.toString());

            // decide whether a subscription is paid or not based on price
            if (subscription.getSubscriptionPrice().equals(BigDecimal.ZERO)) {
                subscription.setPaid(false);
            } else {
                subscription.setPaid(true);
            }

            subscriptionDao.updateSubscription(subscription);
            return ctx.send(StatusCode.NO_CONTENT);
        });

        get("/api/sort/{username}", ctx -> {
            String username = ctx.path("username").value();
            return subscriptionDao.sortAscending(username);
        });
    }
}
