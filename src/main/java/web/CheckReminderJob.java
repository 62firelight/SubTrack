/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dao.SubscriptionDAO;
import domain.Customer;
import domain.Subscription;
import io.jooby.quartz.Scheduled;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class CheckReminderJob implements Job {

    private SubscriptionDAO subscriptionDao;

    public CheckReminderJob(SubscriptionDAO subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    @Scheduled("1h")
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String time = currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        
        Collection<Subscription> subs = this.subscriptionDao.getSubscriptions();
        Multimap<Customer, Subscription> expiredSubs = HashMultimap.create();

        // check if any subs have expired
        LocalDate currentDate = currentDateTime.toLocalDate();
        for (Subscription sub : subs) {
            LocalDate dueDate = LocalDate.parse(sub.getDueDate());

            if (currentDate.compareTo(dueDate) >= 0) {
                expiredSubs.put(sub.getCustomer(), sub);
            }

        }

        // send email(s)
//        for (Customer customer : expiredSubs.keySet()) {
////            System.out.println(customer.getUsername() + " has " + expiredSubs.get(customer).size() + " expired subscription(s).");
//            CompletableFuture.runAsync(() -> {
//                try {                    
//                    Email email = new SimpleEmail();
//                    
//                    // send to default FakeSMTP server
//                    email.setHostName("localhost");
//                    email.setSmtpPort(2525);
//
//                    // send to Gmail's SMTP server
////                    email.setHostName("smtp.googlemail.com");
////                    email.setSmtpPort(465);                    
////                    email.setAuthenticator(new DefaultAuthenticator("username", "password"));
////                    email.setSSLOnConnect(true);
//                    
//                    email.setFrom("SubTrack@example.com");
//                    email.setSubject("Subscription Renewal Warning");
//                    email.setMsg("Hi " + customer.getUsername() + ", \n\nThis is an email to "
//                            + "warn you that " + expiredSubs.get(customer).size() + " of your subscription(s) "
//                            + "will expire soon.\n\nRemember to renew or delete your "
//                            + "subscription(s) before it's too late!\n\n"
//                            + "This message was automatically generated "
//                            + "by SubTrek. Change your account settings "
//                            + "if you wish to receive notifications at a "
//                            + "different time.");
//                    email.addTo(customer.getEmailAddress());
//                    email.send();
//                    
//                    System.out.println(time + " - Successfully sent email to " + customer.getEmailAddress() + " (" + customer.getUsername() + ")");
//                } catch (EmailException ex) {
////                    Logger.getLogger(SubscriptionModule.class.getName()).log(Level.SEVERE, null, ex);
////                    System.out.println(ex.getMessage());
////                    System.out.println("Couldn't send an email to the FakeSMTP server. Make sure that the FakeSMTP server is active and listening on port 2525.");
////                    System.out.println("Don't have FakeSMTP? Download it from http://nilhcem.com/FakeSMTP/");
//
//                    System.out.println(time + " - Failed to send email to " + customer.getEmailAddress() + " (" + customer.getUsername() + ")");
//                }
//            });
//        }

        System.out.println(time + " - Reporting " 
                + expiredSubs.keySet().size() + " customer(s) with " 
                + expiredSubs.values().size() + " total expired subscription(s).");
    }

}
