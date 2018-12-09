package org.domotica.core.model;

import org.bson.Document;

public class Timer {
    public static String DEFAULT_GROUP = "default-group";

    private String id;
    private String name;
    private String trigger;
    private String deviceId;
    private String command;

    public String getId() {
        return id;
    }

    public Timer setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Timer setName(String name) {
        this.name = name;
        return this;
    }

    public String getTrigger() {
        return trigger;
    }

    public Timer setTrigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Timer setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public Timer setCommand(String command) {
        this.command = command;
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

        if (name != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"name\": \"").append(name).append("\"");
            notFirst = true;
        }

        if (deviceId != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"deviceId\": \"").append(deviceId).append("\"");
            notFirst = true;
        }

        if (command != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"command\": \"").append(command).append("\"");
            notFirst = true;
        }

        if (trigger != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"trigger\": \"").append(trigger).append("\"");
            //notFirst = true;
        }

        buffer.append(" }");
        return  buffer.toString();
    }

    public Timer build(String jsonString){
        return this.build(Document.parse(jsonString));
    }

    public Timer build(Document document){

        if (document.containsKey("id")){
            this.id = document.getString("id");
        }

        if (document.containsKey("name")){
            this.name = document.getString("name");
        }

        if (document.containsKey("deviceId")){
            this.deviceId = document.getString("deviceId");
        }

        if (document.containsKey("command")){
            this.command = document.getString("command");
        }

        if (document.containsKey("trigger")){
            this.trigger = document.getString("trigger");
        }

        return this;
    }

}
