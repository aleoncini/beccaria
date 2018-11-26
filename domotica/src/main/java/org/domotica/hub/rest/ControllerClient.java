package org.domotica.hub.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

public class ControllerClient {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    private String url;
    private String gpio;
    private String value;

    public String getUrl() {
        return url;
    }

    public ControllerClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getGpio() {
        return gpio;
    }

    public ControllerClient setGpio(String gpio) {
        this.gpio = gpio;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ControllerClient setValue(String value) {
        this.value = value;
        return this;
    }

    private String buildUrl() {
        if (url == null) return null;
        if (gpio == null) return null;
        StringBuffer sb = new StringBuffer(url + gpio);
        if (value != null){
            sb.append("/").append(value);
        }
        return sb.toString();
    }

    public String invoke(){
        Response response = null;
        String result = null;

        try {
            Client client = new ResteasyClientBuilder()
                    .establishConnectionTimeout(2, TimeUnit.SECONDS)
                    .socketTimeout(4, TimeUnit.SECONDS)
                    .build();
            ResteasyWebTarget target = (ResteasyWebTarget) client.target(buildUrl());
            logger.info("[ControllerClient] invoking " + target.getUri().toString());

            response = target.request().accept(MediaType.APPLICATION_JSON).get();
            if ( response.getStatus() == Response.Status.OK.getStatusCode() ) {
                result = (String) response.getEntity();
            } else {
                result = response.getStatusInfo().getReasonPhrase();
            }
        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            if (response != null){
                response.close();  // You should close connections!
            }
        }
        return result;
    }

}
