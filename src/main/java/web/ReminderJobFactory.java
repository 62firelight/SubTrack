/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.SubscriptionDAO;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 *
 * @author Luke Tang
 */
public class ReminderJobFactory implements JobFactory {
    
    private SubscriptionDAO subscriptionDao;

    public ReminderJobFactory(SubscriptionDAO subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }    
    
    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler sch) throws SchedulerException {
        try {
            Class jobClass = bundle.getJobDetail().getJobClass();
            
            if (jobClass == CheckReminderJob.class) {
                // inject DAO dependency
                return new CheckReminderJob(subscriptionDao);
            }
            
            return (Job) jobClass.newInstance();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }        
    }
    
}
