package org.domotica.configuration.rest;

import org.domotica.core.model.Configuration;
import org.domotica.core.model.ConfigurationHelper;
import org.domotica.core.model.Controller;
import org.domotica.core.model.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;

@Path("config")
public class ConfigurationService {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("config")
    private Configuration config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfig() {
        if (config == null){
            return Response.status(404).build();
        }
        return Response.ok().entity(config.toString()).build();
    }

    @GET
    @Produces("text/plain")
    @Path("info")
    public Response info() {
        logger.info("[ConfigurationService]\n" + config.prettyPrint());
        String msg = "BECCARIA - Domotica project version 1.0 - module: CONFIGURATION " + config.getName();
        return Response.ok().entity(msg).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("new")
    public Response newConfig(String jsonString) {
        config.build(jsonString).setId(UUID.randomUUID().toString());
        return Response.status(200).entity(config.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("load")
    public Response loadConfig() {
        try {
            config.build(new ConfigurationHelper().load());
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }
        logger.info("[ConfigurationService] config loaded.");
        return Response.status(200).entity(config.toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("save")
    public Response saveConfig() {
        if (config == null){
            return Response.status(404).build();
        }
        try {
            new ConfigurationHelper().save(config);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }
        logger.info("[ConfigurationService] config saved.");
        return Response.status(200).entity(config.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("controller/{id}")
    public Response getController(@PathParam("id") String id) {
        if (id == null){
            return Response.status(404).build();
        }
        Controller controller = config.getController(id);
        if (controller == null){
            return Response.status(404).build();
        }
        return Response.ok().entity(controller.toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("controllers/new")
    public Response addController(String jsonString) {
        config.addController(new Controller().build(jsonString).setId(UUID.randomUUID().toString()));
        return Response.status(200).entity(config.toString()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("devices/new")
    public Response addDevice(String jsonString) {
        config.addDevice(new Device().build(jsonString).setId(UUID.randomUUID().toString()));
        return Response.status(200).entity(config.toString()).build();
    }

}