package web;

/**
 *
 * @author emmabrothers
 */
import dao.SubscriptionDAO;
import domain.Customer;
import domain.Subscription;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static java.time.LocalDate.parse;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
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

            Customer c = subscription.getCustomer();
            String customerEmail = c.getEmailAddress();
            String fName = c.getFirstName();
            String lName = c.getLastName();
            String date = subscription.getDueDate();

            CompletableFuture.runAsync(() -> {
                try {
                    Email email = new SimpleEmail();
                    email.setHostName("localhost");
                    email.setSmtpPort(2525);
                    email.setFrom("trbayly145@gmail.com");
                    email.setSubject("Subscription Renewal Warning");
                    email.setMsg("Customer: " + fName + " " + lName + "\n" + " This is an email to warn you that your subscription expires Date: " + date);
                    //email.setMsg("hey");
                    email.addTo(customerEmail);
                    //email.addTo("foo@bar.com");
                    email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(SubscriptionModule.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });

        delete("/api/subscriptions/:id", (req, rsp) -> {
            Integer id = Integer.valueOf(req.param("id").value());
            Subscription subscription = subscriptionDao.getSubscriptionById(id);

            subscriptionDao.deleteSubscription(subscription);
            rsp.status(Status.NO_CONTENT);
        });
    }
}
