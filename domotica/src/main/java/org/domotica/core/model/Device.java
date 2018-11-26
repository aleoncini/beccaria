package org.domotica.core.model;

import org.bson.Document;

public class Device {
    private String type;
    private String gpio;
    private String value;
    private String description;

    public final static String SWITCH_TYPE = "switch";

    public String getType() {
        return type;
    }

    public Device setType(String type) {
        this.type = type;
        return this;
    }

    public String getGpio() {
        return gpio;
    }

    public Device setGpio(String gpio) {
        this.gpio = gpio;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Device setValua(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Device setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");

        if (type != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"type\": \"").append(type).append("\"");
            notFirst = true;
        }

        if (gpio != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"gpio\": \"").append(gpio).append("\"");
            notFirst = true;
        }

        if (value != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"value\": \"").append(value).append("\"");
            notFirst = true;
        }

        if (description != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"description\": \"").append(description).append("\"");
            //notFirst = true;
        }

        buffer.append(" }");
        return  buffer.toString();
    }

    public Device build(String jsonString){
        return this.build(Document.parse(jsonString));
    }

    public Device build(Document document){

        if (document.containsKey("type")){
            this.type = document.getString("type");
        }

        if (document.containsKey("gpio")){
            this.gpio = document.getString("gpio");
        }

        if (document.containsKey("value")){
            this.value = document.getString("value");
        }

        if (document.containsKey("description")){
            this.description = document.getString("description");
        }

        return this;
    }

}
