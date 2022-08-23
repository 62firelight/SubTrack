/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import io.jooby.quartz.Scheduled;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SampleJob implements Job {    

    @Override
    @Scheduled("5s")
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("Hello world!");
    }
    
}
