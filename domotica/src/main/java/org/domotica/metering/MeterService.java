package org.domotica.metering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("meter")
public class MeterService {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("switch_meter")
    private SwitchMeter meter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfig() {
        return Response.ok().entity(meter.toString()).build();
    }

}
