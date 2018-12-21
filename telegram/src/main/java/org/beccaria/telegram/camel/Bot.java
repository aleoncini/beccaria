package org.beccaria.telegram.camel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Bot {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    public Bot(){
        logger.info("[Beccaria Bot] new instance.");
    }

    /**
     * This method processes incoming messages and return responses.
     *
     * @param message a message coming from a human user in a chat
     * @return the reply of the bot. Return null if you don't want to answer
     */
    public String process(String message) {
        if (message == null) {
            return null; // skip non-text messages
        }

        logger.info("Domus BOT - message from chat: " + message);

        return "Someone said " + message;

    }
}
