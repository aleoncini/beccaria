package org.beccaria.raspi;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class AppEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger("org.beccaria");

    @Inject
    Broker broker;

    @PostConstruct
    public void startUp() {
        LOGGER.info("==========> [AppEvent] Application starting...");
        try {
            broker.subscribeToCommands();
            LOGGER.info("==========> [AppEvent] Controller subscribed to commands topic");
            broker.notifyPresence();
            LOGGER.info("==========> [AppEvent] Controller presence notified");
        } catch (MqttException e) {
            LOGGER.info("==========> [AppEvent] ERROR: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
