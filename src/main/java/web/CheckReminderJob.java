/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.SubscriptionDAO;
import domain.Subscription;
import io.jooby.quartz.Scheduled;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

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
        
        Collection<Subscription> subs = this.subscriptionDao.getSubscriptions();
        
        LocalDate currentDate = currentDateTime.toLocalDate();
        for (Subscription sub : subs) {
            LocalDate dueDate = LocalDate.parse(sub.getDueDate());
            
            if (currentDate.compareTo(dueDate) >= 0) {
                System.out.println(sub.getName() + " is expired");
            }
        }
        
//        System.out.println(time + " - There are " + subs.size() + " subscription(s).");
    }
    
}
