package org.domotics.timers;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Singleton
@Named("config")
public class Configuration {
    String filename = ".domotica/timers.txt";
    Scheduler scheduler;

    Configuration(){
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException, SchedulerException {
        scheduler.clear();
        BufferedReader reader = new BufferedReader(new FileReader(fileName()));
        String line = null;
        while((line = reader.readLine()) != null) {
            createJob(line);
        }
    }

    private void createJob(String line) throws SchedulerException {
        String[] params = line.split(" ");
        String triggerData = params[4] + " " + params[5] + " " + params[6] + " " + params[7] + " " + params[8] + " " + params[9];
        String message = params[1] + "/" + params[2] + "_" + params[3];

        JobDetail job = newJob(TimerJob.class).withIdentity(params[0], "jobs-default-group").build();
        CronTrigger trigger = newTrigger()
                .withIdentity(params[0] + "-trigger", "triggers-default-group")
                .withSchedule(cronSchedule(triggerData))
                .build();
        job.getJobDataMap().put("message",message);
        scheduler.scheduleJob(job, trigger);
    }

    private String fileName(){
        return System.getProperty("user.home") + "/.domotica/timers.txt";
    }

}