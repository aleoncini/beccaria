package org.domotica.monitoring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("presence")
public class PresenceService {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getConfig() {
        logger.info("========= presence observed...");
        return Response.ok().entity("{\"status\":\"ok\"}").build();
    }
}