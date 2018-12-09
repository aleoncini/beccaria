package org.domotica.core.model;

import org.bson.Document;

public class Device {
    private String id;
    private String controllerId;
    private String gpio;
    private String name;
    private String type;
    private String value;
    private String status;

    public final static String SWITCH_TYPE = "switch";
    public final static String SENSOR_TYPE = "sensor";
    public final static String STATE_ENABLED = "enabled";
    public final static String STATE_DISABLED = "disabled";
    public final static String STATE_RUNNING = "running";
    public final static String STATE_FAIL = "fail";

    public String getId() {
        return id;
    }

    public Device setId(String id) {
        this.id = id;
        return this;
    }

    public String getControllerId() {
        return controllerId;
    }

    public Device setControllerId(String id) {
        this.controllerId = id;
        return this;
    }

    public String getGpio() {
        return gpio;
    }

    public Device setGpio(String gpio) {
        this.gpio = gpio;
        return this;
    }

    public String getType() {
        return type;
    }

    public Device setType(String type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Device setValue(String value) {
        this.value = value;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Device setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getName() {
        return name;
    }

    public Device setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");

        if (id != null){
            //if (notFirst) buffer.append(", ");
            buffer.append("\"id\": \"").append(id).append("\"");
            notFirst = true;
        }

        if (controllerId != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"controllerId\": \"").append(controllerId).append("\"");
            notFirst = true;
        }

        if (gpio != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"gpio\": \"").append(gpio).append("\"");
            notFirst = true;
        }

        if (name != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"name\": \"").append(name).append("\"");
            notFirst = true;
        }

        if (type != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"type\": \"").append(type).append("\"");
            notFirst = true;
        }

        if (value != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"value\": \"").append(value).append("\"");
            notFirst = true;
        }

        if (status != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"status\": \"").append(status).append("\"");
            //notFirst = true;
        }

        buffer.append(" }");
        return  buffer.toString();
    }

    public Device build(String jsonString){
        return this.build(Document.parse(jsonString));
    }

    public Device build(Document document){

        if (document.containsKey("id")){
            this.id = document.getString("id");
        }

        if (document.containsKey("controllerId")){
            this.controllerId = document.getString("controllerId");
        }

        if (document.containsKey("gpio")){
            this.gpio = document.getString("gpio");
        }

        if (document.containsKey("name")){
            this.name = document.getString("name");
        }

        if (document.containsKey("type")){
            this.type = document.getString("type");
        }

        if (document.containsKey("value")){
            this.value = document.getString("value");
        }

        if (document.containsKey("status")){
            this.status = document.getString("status");
        }

        return this;
    }

    public String prettyPrint(){
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n        {\n");

        if (id != null){
            buffer.append("          \"id\": \"").append(id).append("\"");
            notFirst = true;
        }

        if (controllerId != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("          \"controllerId\": \"").append(controllerId).append("\"");
            notFirst = true;
        }

        if (gpio != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("          \"gpio\": \"").append(gpio).append("\"");
            notFirst = true;
        }

        if (name != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("          \"name\": \"").append(name).append("\"");
            notFirst = true;
        }

        if (type != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("          \"type\": \"").append(type).append("\"");
            notFirst = true;
        }

        if (value != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("          \"value\": \"").append(value).append("\"");
            notFirst = true;
        }

        if (status != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("          \"status\": \"").append(status).append("\"");
            //notFirst = true;
        }

        buffer.append("\n        }");
        return  buffer.toString();
    }

}
