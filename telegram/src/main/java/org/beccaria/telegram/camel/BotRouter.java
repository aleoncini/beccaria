package org.beccaria.telegram.camel;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotRouter extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Autowired
    private Bot bot;

    public BotRouter(){
        logger.info("[Beccaria Bot] BotRouter instantiated.");
    }

    @Override
    public void configure() throws Exception {

        from("telegram:bots")
                .bean(bot)
                .to("telegram:bots");

    }
}
