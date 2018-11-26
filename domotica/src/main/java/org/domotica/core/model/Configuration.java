package org.domotica.core.model;

import org.bson.Document;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Named("config")
public class Configuration {
    private String id;
    private String description;
    private List<Controller> controllers;

    public String getUrl(String id){
        Controller controller = this.getController(id);
        StringBuffer url = new StringBuffer();

        if (controller == null) return null;

        if (controller.getIp() == null) return null;

        if (controller.getProtocol() == null) controller.setProtocol(Controller.HTTP_PROTOCOL);

        if (controller.getPort() == 0) controller.setPort(80);

        url.append(controller.getProtocol()).append("://");
        url.append(controller.getIp()).append(":").append(controller.getPort());
        url.append("/rs/gpio/");
        return url.toString();
    }

    private Controller getController(String id){
        for (Controller controller : controllers) {
            if (controller.getId().equals(id)) return controller;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public Configuration setId(String id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Configuration setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Controller> getControllers() {
        return controllers;
    }

    public Configuration setControllers(List<Controller> controllers) {
        this.controllers = controllers;
        return this;
    }

    public Configuration addController(Controller controller) {
        if (controllers == null){
            controllers = new ArrayList<Controller>();
        }
        this.controllers.add(controller);
        return this;
    }

    @Override
    public String toString() {
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");

        if (id != null){
            buffer.append("\"id\": \"").append(id).append("\"");
            notFirst = true;
        }

        if (description != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"description\": \"").append(description).append("\"");
            notFirst = true;
        }

        if (controllers != null){
            boolean notFirstController = false;
            if (notFirst) buffer.append(", ");
            buffer.append("\"controllers\": { ");

            for (Controller controller : controllers) {
                if (notFirstController) buffer.append(", ");
                buffer.append(controller.toString());
                notFirstController = true;
            }

            buffer.append(" }");
        }

        buffer.append(" }");
        return  buffer.toString();
    }

    public Configuration build(String jsonString){
        return this.build(Document.parse(jsonString));
    }

    public Configuration build(Document configDocument){

        if (configDocument.containsKey("id")){
            this.id = configDocument.getString("id");
        }

        if (configDocument.containsKey("description")){
            this.description = configDocument.getString("description");
        }

        if (configDocument.containsKey("controllers")){
            this.controllers = new ArrayList<Controller>();
            List<Document> controllerDocuments = (List<Document>) configDocument.get("controllers");
            for (Document ctrl : controllerDocuments){
                this.controllers.add(new Controller().build(ctrl));
            }
        }

        return this;
    }

}
