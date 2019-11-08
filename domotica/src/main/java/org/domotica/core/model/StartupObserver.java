package org.domotica.core.model;

import org.domotica.core.util.CoreUtils;
import org.domotica.metering.SwitchMeter;
import org.domotica.monitoring.temperature.TemperatureCache;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class StartupObserver {

    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("config")
    private Configuration config;

    @Inject
    @Named("tcache")
    private TemperatureCache tcache;

    @Inject
    @Named("switch_meter")
    private SwitchMeter meter;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        logger.info("[Startup] ==================== initializing application...");
        logger.info("[Startup] ==================== loading config...");
        loadConfiguration();
        logger.info("[Startup] ==================== starting timers...");
        try {
            CoreUtils.startTimers(config);
        } catch (SchedulerException e) {
            logger.warn("[Startup] problem while starting timers: " + e.getLocalizedMessage());
        }
        logger.info("[Startup] ==================== starting monitoring...");
        CoreUtils.startMonitoring(tcache);
        logger.info("[Startup] ==================== starting metering...");
        try {
            CoreUtils.startMeters(config, meter);
        } catch (SchedulerException e) {
            logger.warn("[Startup] problem while starting meters: " + e.getLocalizedMessage());
        }
        logger.info("[Startup] ==================== initialization complete.");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        logger.info("===============> Exiting Application");
    }

    private void loadConfiguration(){
        try {
            config.build(new ConfigurationHelper().load());
        } catch (Throwable throwable) {
            logger.error(throwable.toString());
        }
        if (config.getId() == null){
            logger.warn("Unable to load configuration. Starting from scratch.");
        }
    }
}