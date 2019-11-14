package org.domotics.timers;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/timers")
public class TimerResource {
    private static final Logger logger = LoggerFactory.getLogger("org.domotics.timers");

    @Inject
    @Named("config")
    private Configuration config;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/init")
    public Response init() {
        logger.info("[TimerResource] initializing configuration");
        try {
            config.init();
            return Response.status(200).entity("Timers initialised").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(200).entity(e.getLocalizedMessage()).build();
        }
    }

}
