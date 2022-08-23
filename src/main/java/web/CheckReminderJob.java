/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.SubscriptionDAO;
import dao.SubscriptionJdbcDAO;
import domain.Subscription;
import io.jooby.quartz.Scheduled;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

public class CheckReminderJob implements Job {

    SubscriptionJdbcDAO subscriptionDao;

    @Inject
    public CheckReminderJob(SubscriptionJdbcDAO subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }
    
    @Override
    @Scheduled("5s")
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String time = currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        
        System.out.println(time + " - " + this.subscriptionDao);
//        System.out.println(time + " - There are " + this.subscriptionDao.getSubscriptions().size() + " subscription(s).");
    }
    
}
