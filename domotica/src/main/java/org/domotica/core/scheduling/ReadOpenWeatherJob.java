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

public class ReadOpenWeatherJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Double temperature = 0.00;
        String url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");
        TemperatureCache cache = (TemperatureCache) jobExecutionContext.getJobDetail().getJobDataMap().get("cache");
        String cacheName = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("cacheName");
        logger.info("[ReadOpenWeatherJob] calling " + url);
        Document document = Document.parse(new ControllerClient().setUrl(url).invoke());
        if (document.containsKey("main")){
            Document mainDoc = (Document) document.get("main");
            if (mainDoc.containsKey("temp")){
                temperature = CoreUtils.truncateDouble(mainDoc.getDouble("temp") - 273.15);
            }
        }
        logger.info("[ReadOpenWeatherJob] temperature: " + temperature);
        cache.logValue(cacheName,temperature.doubleValue());
    }

}