package org.domotica.core.scheduling;

import org.domotica.core.model.Configuration;
import org.domotica.core.rest.ControllerClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

public class CommandJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("config")
    private Configuration config;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String id = jobExecutionContext.getJobDetail().getKey().getName();
        String url = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("url");
        logger.info("[CommandJob] " + id + " calling " + url);
        String result = new ControllerClient().setUrl(url).invoke();
        logger.info("[CommandJob] result: " + result);
    }

}
