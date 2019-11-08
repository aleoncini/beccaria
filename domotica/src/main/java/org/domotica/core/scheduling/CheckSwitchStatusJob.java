package org.domotica.core.scheduling;

import org.domotica.core.rest.ControllerClient;
import org.domotica.metering.SwitchMeter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckSwitchStatusJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");
        if (new ControllerClient().setUrl(url).isOn()){
            String deviceId = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("deviceId");
            SwitchMeter meter = (SwitchMeter) jobExecutionContext.getJobDetail().getJobDataMap().get("meter");
            meter.add(deviceId);
        }
    }

}