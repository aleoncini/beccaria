package org.domotica.configuration.rest;

import org.domotica.core.model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("config")
public class ConfigurationService {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("config")
    private Configuration config;

    @GET
    @Produces("text/plain")
    @Path("info")
    public Response info() {
        String msg = "BECCARIA - Domotica project version 1.0 - module: HUB configured for: " + config.getDescription();
        logger.info(msg);
        return Response.ok().entity(msg).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response switchOn(String jsonString) {
        logger.info("[ConfigurationService] adding config: " + jsonString);
        config.build(jsonString);
        //new ControllerService().setIpAddress(DEFAULT_IP_ADDRESS).setUuid("94a3171a-5604-460a-bd76-cf261a686b6f").on();
        return Response.status(200).entity(config.toString()).build();
    }

}
