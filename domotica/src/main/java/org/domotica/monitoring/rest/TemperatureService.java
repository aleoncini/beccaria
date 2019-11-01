package org.domotica.monitoring.rest;

import org.domotica.monitoring.temperature.TemperatureCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("monitor")
public class TemperatureService {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("tcache")
    private TemperatureCache tcache;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfig() {
        StringBuffer buffer = new StringBuffer("{ \n");
        buffer.append("  \"name\":\"Temperature Cache\" \n");
        buffer.append("}\n");
        logger.info(buffer.toString());
        return Response.ok().entity(buffer.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("temperature")
    public Response temperatureCache() {
        return Response.ok().entity(tcache.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("test")
    public Response test() {
        tcache.logValue(TemperatureCache.internalCacheName,21.32);
        logger.info("================= " + tcache.toString());
        tcache.logValue(TemperatureCache.internalCacheName,52.91);
        logger.info("================= " + tcache.toString());
        tcache.logValue(TemperatureCache.internalCacheName,33.78);
        logger.info("================= " + tcache.toString());
        return Response.ok().entity(tcache.toString()).build();
    }
}