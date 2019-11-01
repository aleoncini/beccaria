package org.domotica.core.util;

import org.domotica.core.model.Configuration;
import org.domotica.core.model.Device;
import org.domotica.core.model.Timer;
import org.domotica.core.scheduling.CheckSwitchStatusJob;
import org.domotica.core.scheduling.CommandJob;
import org.domotica.core.scheduling.ReadOpenWeatherJob;
import org.domotica.core.scheduling.ReadTemperatureSensorJob;
import org.domotica.metering.SwitchMeter;
import org.domotica.monitoring.temperature.TemperatureCache;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.text.DecimalFormat;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class CoreUtils {

    public static double truncateDouble(double value){
        DecimalFormat df = new DecimalFormat("#.##");
        double newValue = Double.valueOf(df.format(value));
        return newValue;
    }

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

    public static void startTimers(Configuration configuration) throws SchedulerException {
        List<Timer> timers = configuration.getTimers();
        if (timers == null)return;

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        for (Timer timer : timers) {
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

            scheduler.scheduleJob(job, trigger);
        }
    }

    public static void startMeters(Configuration configuration, SwitchMeter meter) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        for (Device device : configuration.getDevices()) {
            JobDetail job = newJob(CheckSwitchStatusJob.class).withIdentity(device.getId(), "metering_group").build();
            job.getJobDataMap().put("url",configuration.getDeviceUrl(device.getId()));
            job.getJobDataMap().put("deviceId",device.getId());
            job.getJobDataMap().put("meter",meter);
            CronTrigger trigger = newTrigger()
                        .withIdentity(device.getId() + "-trigger", "metering_group")
                        .withSchedule(cronSchedule("0 */10 * ? * *"))
                        .build();
            scheduler.scheduleJob(job, trigger);
        }
    }

    public static void startMonitoring(TemperatureCache cache) {
        // monitoring is hardcoded here.
        // TBI: put sensor list in the configuration
        try {
            JobDetail internalTemperatureJob = newJob(ReadTemperatureSensorJob.class).withIdentity("internalSensor", "sensors-group").build();
            internalTemperatureJob.getJobDataMap().put("url","http://192.168.1.53/rs/gpio/2");
            internalTemperatureJob.getJobDataMap().put("cache",cache);
            internalTemperatureJob.getJobDataMap().put("cacheName",TemperatureCache.internalCacheName);
            JobDetail externalTemperatureJob = newJob(ReadTemperatureSensorJob.class).withIdentity("externalSensor", "sensors-group").build();
            externalTemperatureJob.getJobDataMap().put("url","http://192.168.1.49/rs/gpio/2");
            externalTemperatureJob.getJobDataMap().put("cache",cache);
            externalTemperatureJob.getJobDataMap().put("cacheName",TemperatureCache.externalCacheName);
            JobDetail nominalTemperatureJob = newJob(ReadOpenWeatherJob.class).withIdentity("nominalSensor", "sensors-group").build();
            nominalTemperatureJob.getJobDataMap().put("url","http://api.openweathermap.org/data/2.5/weather?q=Roma,IT&APPID=44936065a658cf6e64eaa4f5f1d9a1ae");
            nominalTemperatureJob.getJobDataMap().put("cache",cache);
            nominalTemperatureJob.getJobDataMap().put("cacheName",TemperatureCache.nominalCacheName);
            CronTrigger int_trigger = newTrigger()
                    .withIdentity("internalTemperatureSensor-trigger", "sensors-group")
                    .withSchedule(cronSchedule("0 0 0/2 ? * * *"))
                    .build();
            CronTrigger ext_trigger = newTrigger()
                    .withIdentity("externalTemperatureSensor-trigger", "sensors-group")
                    .withSchedule(cronSchedule("0 0 0/2 ? * * *"))
                    .build();
            CronTrigger owm_trigger = newTrigger()
                    .withIdentity("openWeatherTemperature-trigger", "sensors-group")
                    .withSchedule(cronSchedule("0 0 0/2 ? * * *"))
                    .build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(internalTemperatureJob, int_trigger);
            scheduler.scheduleJob(externalTemperatureJob, ext_trigger);
            scheduler.scheduleJob(nominalTemperatureJob, owm_trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
