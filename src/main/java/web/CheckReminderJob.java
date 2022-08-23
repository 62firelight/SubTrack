/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import io.jooby.quartz.Scheduled;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CheckReminderJob implements Job {    

    @Override
    @Scheduled("5s")
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String text = currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        
        System.out.println("The date and time is " + text);
    }
    
}
