package org.domotica.core.scheduling;

import org.domotica.core.rest.ControllerClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String id = jobExecutionContext.getJobDetail().getKey().getName();
        String url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");
        logger.info("[CommandJob] " + id + " is calling " + url);
        String result = new ControllerClient().setUrl(url).invoke();
        logger.info("[CommandJob] result: " + result);
    }

}
