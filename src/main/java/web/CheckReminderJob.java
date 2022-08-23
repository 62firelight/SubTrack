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
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class CheckReminderJob implements Job {

    private SubscriptionDAO subscriptionDao;

    public CheckReminderJob(SubscriptionDAO subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    @Scheduled("1m")
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String time = currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));

        /* 
            
            initialize subs // using database
            initialize expiredSubs // multimap that maps customers to sub collections
            
            for each sub in subs
                if sub is expired
                    add subs to expiredSubs
            
            for each customer in expiredSubs.keys
                send email to each email associated with customer // using expiredSubs
        
            print status message    
                    
         */        
        
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
        for (Customer customer : expiredSubs.keySet()) {
//            System.out.println(customer.getUsername() + " has " + expiredSubs.get(customer).size() + " expired subscription(s).");
            CompletableFuture.runAsync(() -> {
                try {                    
                    Email email = new SimpleEmail();
                    email.setHostName("localhost");
                    email.setSmtpPort(2525);
                    email.setFrom("SubTrack@gmail.com");
                    email.setSubject("Subscription Renewal Warning");
                    email.setMsg("Hi " + customer.getUsername() + ", \n\nThis is an email to "
                            + "warn you that " + expiredSubs.get(customer).size() + " of your subscription(s) "
                            + "will expire soon.\n\nRemember to renew or delete your "
                            + "subscription(s) before it's too late!\n\n"
                            + "This message was automatically generated "
                            + "by SubTrack. Change your account settings "
                            + "if you wish to receive notifications at a "
                            + "different time.");
                    email.addTo(customer.getEmailAddress());
                    email.send();
                } catch (EmailException ex) {
//                    Logger.getLogger(SubscriptionModule.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                    System.out.println("Couldn't send an email to the FakeSMTP server. Make sure that the FakeSMTP server is active and listening on port 2525.");
                    System.out.println("Don't have FakeSMTP? Download it from http://nilhcem.com/FakeSMTP/");
                }
            });
        }

//        System.out.println(time + " - Reporting " 
//                + expiredSubs.keySet().size() + " customer(s) with " 
//                + expiredSubs.values().size() + " total expired subscription(s).");
    }

}
