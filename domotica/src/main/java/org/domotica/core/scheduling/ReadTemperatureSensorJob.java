package org.domotica.core.scheduling;

import org.bson.Document;
import org.domotica.core.rest.ControllerClient;
import org.domotica.core.util.CoreUtils;
import org.domotica.monitoring.temperature.TemperatureCache;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadTemperatureSensorJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");
    public static final double BAD_VALUE = -999.0;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");
        TemperatureCache cache = (TemperatureCache) jobExecutionContext.getJobDetail().getJobDataMap().get("cache");
        String cacheName = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("cacheName");
        logger.info("[ReadTemperatureSensorJob] calling " + url);
        double temperature = BAD_VALUE;
        while (temperature == BAD_VALUE){
            temperature = readSensor(url);
        }
        logger.info("[ReadTemperatureSensorJob] temperature: " + temperature);
        cache.logValue(cacheName,temperature);
    }

    private double readSensor(String url){
        Document document = Document.parse(new ControllerClient().setUrl(url).invoke());
        String statusString = document.getString("status");
        if (statusString.equalsIgnoreCase("nan")){
            return BAD_VALUE;
        }
        Double temperature = CoreUtils.truncateDouble(Double.parseDouble(statusString));
        return temperature.doubleValue();
    }
}