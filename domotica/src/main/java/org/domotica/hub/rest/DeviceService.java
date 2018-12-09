package org.domotica.hub.rest;

import org.domotica.core.model.Configuration;
import org.domotica.core.model.Device;
import org.domotica.core.rest.ControllerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/device")
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("config")
    private Configuration config;

    @GET
    @Produces("text/plain")
    @Path("info")
    public Response info() {
        String msg = "BECCARIA - Domotica project version 1.0 - module: HUB configured for: " + config.getName();
        logger.info(msg);
        return Response.ok().entity(msg).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response list() {
        List<Device> devices = config.getDevices();

        if ((devices == null) || (devices.size() == 0)){
            return Response.status(404).build();
        }

        StringBuffer buffer = new StringBuffer("{\"devices\": [");

        boolean notFirstElement = false;
        for (Device dev: devices) {
            if (notFirstElement) buffer.append(", ");
            buffer.append(dev.toString());
            notFirstElement = true;
        }
        buffer.append(" ]");

        return Response.ok().entity(buffer.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response doGet(@PathParam("id")String id, @QueryParam("value") String val) {
        String result = null;
        Device device = config.getDevice(id);
        if (device == null){
            return Response.status(404).build();
        }

        String baseUrl = config.getDeviceUrl(id);
        if (baseUrl == null){
            return Response.status(404).build();
        }

        StringBuffer url = new StringBuffer(baseUrl);
        if (val != null){
            url.append("/").append(val);
        }

        result = new ControllerClient().setUrl(url.toString()).invoke();

        logger.info("[ControllerProxyService] result: " + result);
        return Response.status(200).entity(result).build();
    }
}
