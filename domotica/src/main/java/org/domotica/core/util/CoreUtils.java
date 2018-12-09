package org.domotica.core.util;

import org.domotica.core.model.Configuration;
import org.domotica.core.model.Timer;
import org.domotica.core.scheduling.CommandJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class CoreUtils {

    public static String listToString(String listName, List list){
        if (list == null)return "[]";
        if (list.size() == 0)return "[]";

        StringBuffer buffer = new StringBuffer();
        boolean notFirstElement = false;
        buffer.append("\"").append(listName).append("\": [ ");
        for (Object obj : list) {
            if (notFirstElement) buffer.append(", ");
            buffer.append(obj.toString());
            notFirstElement = true;
        }
        buffer.append(" ]");

        return buffer.toString();
    }

    public static void startTimers(Configuration configuration) {
        List<Timer> timers = configuration.getTimers();
        if (timers == null)return;

        for (Timer timer : timers) {
            try {
                JobDetail job = newJob(CommandJob.class).withIdentity(timer.getId(), Timer.DEFAULT_GROUP).build();
                // calculating the URL to be invoked.
                String deviceId = timer.getDeviceId();
                String command = timer.getCommand();
                String baseUrl = configuration.getDeviceUrl(deviceId);
                String url = baseUrl + "/" + command;
                // put the url in the data accessible by the Job
                job.getJobDataMap().put("url",url);

                CronTrigger trigger = newTrigger()
                        .withIdentity(timer.getId() + "-trigger", Timer.DEFAULT_GROUP)
                        .withSchedule(cronSchedule(timer.getTrigger()))
                        .build();

                Scheduler scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

}
