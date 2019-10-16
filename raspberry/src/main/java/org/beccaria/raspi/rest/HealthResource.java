package org.beccaria.raspi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("health")
public class HealthResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response healthCheck() {
        return Response.ok("ok\n").build();
    }
}
