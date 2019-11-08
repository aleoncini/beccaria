package org.domotica.core.rest;

import org.bson.Document;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

public class ControllerClient {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    private String url;

    public String getUrl() {
        return url;
    }

    public ControllerClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public String invoke(){
        Client client = new ResteasyClientBuilder()
                .establishConnectionTimeout(2, TimeUnit.SECONDS)
                .socketTimeout(4, TimeUnit.SECONDS)
                .build();
        ResteasyWebTarget target = (ResteasyWebTarget) client.target(url);
        logger.info("[ControllerClient] invoking " + target.getUri().toString());
        return this.get(target);
    }

    private String get(ResteasyWebTarget target){
        Response response = null;
        String result = null;
        try {
            response = target.request().get();
            if ( response.getStatus() == Response.Status.OK.getStatusCode() ) {
                result = (String) response.readEntity(String.class);
            } else {
                logger.warn("[ControllerClient] problem to get status: " + response.getStatusInfo().getReasonPhrase());
            }
        } catch (Exception e) {
            logger.warn("[ControllerClient] unable to connect with controller. " + e.getLocalizedMessage());
        } finally {
            if (response != null){
                response.close();  // You should always close connections!
            }
        }
        return result;
    }

    public boolean isOn(){
        Document document = Document.parse(this.invoke());
        if (! document.containsKey("status")){
            return false;
        }
        String status = document.getString("status");
        if (status.equals("1")){
            return true;
        }
        if (status.equalsIgnoreCase("on")){
            return true;
        }
        return false;
    }

    public String getStatus(){
        String status = "off";
        String jsonString = this.invoke();
        Document document = Document.parse(jsonString);
        if (document.containsKey("status")){
            status = document.getString("status");
        }
        return status;
    }

}
