package org.domotica.core.model;

import org.bson.Document;

public class Controller {
    private String id;
    private String name;
    private String protocol;
    private String ip;
    private int port;

    public final static String HTTP_PROTOCOL = "http";
    public final static String HTTPS_PROTOCOL = "https";

    public String getId() {
        return id;
    }

    public Controller setId(String id) {
        this.id = id;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public Controller setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Controller setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Controller setPort(int port) {
        this.port = port;
        return this;
    }

    public String getName() {
        return name;
    }

    public Controller setName(String name) {
        this.name = name;
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

        if (protocol != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"protocol\": \"").append(protocol).append("\"");
            notFirst = true;
        }

        if (ip != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"ip\": \"").append(ip).append("\"");
            notFirst = true;
        }

        if (port > 0){
            if (notFirst) buffer.append(", ");
            buffer.append("\"port\": ").append(port).append("");
            //notFirst = true;
        }

        buffer.append(" }");
        return  buffer.toString();
    }

    public String prettyPrint() {
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n    {\n");

        if (id != null){
            buffer.append("      \"id\": \"").append(id).append("\"");
            notFirst = true;
        }

        if (name != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("      \"name\": \"").append(name).append("\"");
            notFirst = true;
        }

        if (protocol != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("      \"protocol\": \"").append(protocol).append("\"");
            notFirst = true;
        }

        if (ip != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("      \"ip\": \"").append(ip).append("\"");
            notFirst = true;
        }

        if (port > 0){
            if (notFirst) buffer.append(",\n");
            buffer.append("      \"port\": ").append(port).append("");
            //notFirst = true;
        }

        buffer.append("\n    }");
        return  buffer.toString();
    }

    public Controller build(String jsonString){
        return this.build(Document.parse(jsonString));
    }

    public Controller build(Document document){

        if (document.containsKey("id")){
            this.id = document.getString("id");
        }

        if (document.containsKey("name")){
            this.name = document.getString("name");
        }

        if (document.containsKey("protocol")){
            this.protocol = document.getString("protocol");
        }

        if (document.containsKey("ip")){
            this.ip = document.getString("ip");
        }

        if (document.containsKey("port")){
            this.port = document.getInteger("port");
        }

        return this;
    }


}