package org.domotica.core.rest;

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
        Response response = null;
        String result = null;

        try {
            Client client = new ResteasyClientBuilder()
                    .establishConnectionTimeout(2, TimeUnit.SECONDS)
                    .socketTimeout(4, TimeUnit.SECONDS)
                    .build();
            ResteasyWebTarget target = (ResteasyWebTarget) client.target(url);
            logger.info("[ControllerClient] invoking " + target.getUri().toString());
            response = target.request().get();
            if ( response.getStatus() == Response.Status.OK.getStatusCode() ) {
                result = (String) response.readEntity(String.class);
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
