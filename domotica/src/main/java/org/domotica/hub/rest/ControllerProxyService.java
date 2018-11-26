package org.domotica.hub.rest;

import org.domotica.core.model.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hub")
public class ControllerProxyService {
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet(@QueryParam("id") String id, @QueryParam("gpio") String gpio, @QueryParam("val") String val) {
        String result = null;

        if (id == null){
            return Response.status(400).build();
        }

        if (gpio == null){
            return Response.status(400).build();
        }

        String url = config.getUrl(id);
        if (url == null){
            return Response.status(404).build();
        }

        if (val == null){
            result = new ControllerClient().setUrl(config.getUrl(id)).setGpio(gpio).invoke();
        }

        result = new ControllerClient().setUrl(config.getUrl(id)).setGpio(gpio).setValue(val).invoke();

        logger.info("[ControllerProxyService] result: " + result);
        return Response.status(200).entity(result).build();
    }

}
