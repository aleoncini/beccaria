package org.beccaria.raspi.rest;

import org.beccaria.raspi.hardware.RaspberryPi3BPlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("ctrl")
public class ControllerResource {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @Named("raspi")
    private RaspberryPi3BPlus board;

    @GET
    public Response getInfo() {
        return Response.ok(board.serial()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getStatus(@PathParam("id") int pinNumber) {
        return Response.ok(board.status(pinNumber) + "\n").build();
    }

    @GET
    @Path("{id}/on")
    @Produces("application/json")
    public Response setOn(@PathParam("id") int pinNumber) {
        return Response.ok(board.on(pinNumber) + "\n").build();
    }

    @GET
    @Path("{id}/off")
    @Produces("application/json")
    public Response setOff(@PathParam("id") int pinNumber) {
        return Response.ok(board.off(pinNumber) + "\n").build();
    }

}
