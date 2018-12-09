package org.domotica.core.model;

import org.domotica.core.util.CoreUtils;
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

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        logger.info("[Startup] ==================== initializing application...");
        logger.info("[Startup] ==================== loading config...");
        loadConfiguration();
        logger.info("[Startup] ==================== starting timers...");
        CoreUtils.startTimers(config);
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
