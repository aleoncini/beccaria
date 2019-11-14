package org.beccaria.hub;

import org.beccaria.hub.MessagePublisher;
import org.beccaria.hub.HubResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/hub")
public class Hub {
    private static final Logger logger = Logger.getLogger("org.domotics");

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("[HUB] REST service to invoke remote micro controller services.").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/health")
    public Response healthCheck() {
        logger.info("[HUB] requested healthcheck");
        new MessagePublisher().publish("healthcheck");
        return Response.status(200).entity(new HubResult().setId("all controllers").setMessage("request for an healthcheck sent to broker").setResult(HubResult.SUCCESS).toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/{pin}")
    public Response status(@PathParam("id") String id, @PathParam("pin") int pin) {
        logger.info("[HUB] requested status for controller " + id + ", Pin: " + pin);
        String message = "To Be Developed";
        return Response.status(200).entity(new HubResult().setId(id).setMessage(message).setResult(HubResult.SUCCESS).toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/on/{id}/{pin}")
    public Response switchOn(@PathParam("id") String id, @PathParam("pin") int pin) {
        logger.info("[Hub] received request to switch ON the controller " + id + ", pin " + pin);
        String message = id + "/" + pin + "_on";
        new MessagePublisher().publish(message);
        return Response.status(200).entity(new HubResult().setId(id).setMessage("request to switch device ON sent to broker").setResult(HubResult.SUCCESS).toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/off/{id}/{pin}")
    public Response switchOff(@PathParam("id") String id, @PathParam("pin") int pin) {
        logger.info("[Hub] received request to switch OFF the controller" + id + ", pin " + pin);
        String message = id + "/" + pin + "_off";
        new MessagePublisher().publish(message);
        return Response.status(200).entity(new HubResult().setId(id).setMessage("request to switch device OFF sent to broker").setResult(HubResult.SUCCESS).toString()).build();
    }
}
