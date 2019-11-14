package org.domotics.timers;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@ApplicationScoped
public class StartApp {
    private static final Logger logger = LoggerFactory.getLogger("org.domotics.timers");

    @Inject
    @Named("config")
    private Configuration config;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        logger.info("[Startup] ==================== initializing application...");

        try {
            config.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        logger.info("===============> Exiting Application");
    }

}
