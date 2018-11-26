package org.domotica.core.model;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private String id;
    private String protocol;
    private String ip;
    private int port;
    private String description;
    private List<Device> devices;

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

    public String getDescription() {
        return description;
    }

    public Controller setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Controller setDevices(List<Device> devices) {
        this.devices = devices;
        return this;
    }

    public Controller addDevice(Device device) {
        if (devices == null){
            devices = new ArrayList<Device>();
        }
        this.devices.add(device);
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
            notFirst = true;
        }

        if (description != null){
            if (notFirst) buffer.append(", ");
            buffer.append("\"description\": \"").append(description).append("\"");
            //notFirst = true;
        }

        if (devices != null){
            boolean notFirstDevice = false;
            if (notFirst) buffer.append(", ");
            buffer.append("\"devices\": { ");

            for (Device device : devices) {
                if (notFirstDevice) buffer.append(", ");
                buffer.append(device.toString());
                notFirstDevice = true;
            }

            buffer.append(" }");
        }

        buffer.append(" }");
        return  buffer.toString();
    }

    public Controller build(Document document){

        if (document.containsKey("id")){
            this.id = document.getString("id");
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

        if (document.containsKey("description")){
            this.description = document.getString("description");
        }

        if (document.containsKey("devices")){
            this.devices = new ArrayList<Device>();
            List<Document> devDocuments = (List<Document>) document.get("devices");
            for (Document dev : devDocuments){
                this.devices.add(new Device().build(dev));
            }
        }

        return this;
    }


}