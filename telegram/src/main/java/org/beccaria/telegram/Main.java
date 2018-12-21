package org.beccaria.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    public Main(){
        logger.info("[Beccaria Bot] Main application started.");
    }

}
