package org.domotica.configuration.rest;

import org.domotica.core.model.Configuration;
import org.domotica.core.model.Timer;
import org.domotica.core.util.CoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("timers")
public class TimerService {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("config")
    private Configuration config;

    @GET
    @Produces("text/plain")
    @Path("info")
    public Response info() {
        String msg = "BECCARIA - Domotica project version 1.0 - module: TIMERS " + config.getName();
        return Response.ok().entity(msg).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("start/{id}")
    public Response start(@PathParam("id") String id) {
        if (id.equalsIgnoreCase("all")){
            return startAll();
        }
        logger.info("[ConfigurationService] config loaded.");
        return Response.status(200).entity(config.toString()).build();
    }

    private Response startAll() {
        CoreUtils.startTimers(config);
        return Response.status(200).entity(CoreUtils.listToString("timers", config.getTimers())).build();
    }

    private String getUrl(Timer timer) {
        String deviceId = timer.getDeviceId();
        String command = timer.getCommand();
        String baseUrl = config.getDeviceUrl(deviceId);
        return baseUrl + "/" + command;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response newConfig(String jsonString) {
        Timer timer = new Timer().build(jsonString).setId(UUID.randomUUID().toString());
        config.addTimer(timer);
        return Response.status(200).entity(timer.toString()).build();
    }

}
