package org.domotica.hub.rest;

import org.domotica.core.model.Configuration;
import org.domotica.core.model.Device;
import org.domotica.core.rest.ControllerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/dashboard")
public class DashBoard {
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
    @Path("devlist")
    public Response list() {
        // id, nome e stato
        List<Device> devices = config.getDevices();

        StringBuffer buffer = new StringBuffer("{\"devices\": [");

        boolean notFirstElement = false;
        for (Device dev: devices) {
            if (notFirstElement) buffer.append(", ");
            buffer.append(getDashBoardInfo(dev));
            notFirstElement = true;
        }
        buffer.append(" ] }");

        return Response.ok().entity(buffer.toString()).build();
    }

    private String getDashBoardInfo(Device device){
        String devUrl = config.getDeviceUrl(device.getId());
        String status = new ControllerClient().setUrl(devUrl).getStatus();
        logger.info("================> " + status);
        device.setStatus(status);
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("\"id\":\"").append(device.getId()).append("\",");
        buffer.append("\"name\":\"").append(device.getName()).append("\",");
        buffer.append("\"status\":\"").append(device.getStatus()).append("\"");
        buffer.append("}");
        return buffer.toString();
    }

}
