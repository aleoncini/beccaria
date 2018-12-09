package org.domotica.core.model;

import org.bson.Document;
import org.domotica.core.util.CoreUtils;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Named("config")
public class Configuration {
    private String id;
    private String name;
    private List<Controller> controllers;
    private List<Device> devices;
    private List<Zone> zones;
    private List<Timer> timers;

    public String getId() {
        return id;
    }

    public synchronized Configuration setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public synchronized Configuration setName(String name) {
        this.name = name;
        return this;
    }

    public List<Controller> getControllers() {
        return controllers;
    }

    public synchronized Configuration setControllers(List<Controller> controllers) {
        this.controllers = controllers;
        return this;
    }

    public Controller getController(String id){
        for (Controller controller : controllers) {
            if (controller.getId().equals(id)) return controller;
        }
        return null;
    }

    public synchronized Configuration addController(Controller controller) {
        if (controllers == null){
            controllers = new ArrayList<Controller>();
        }
        this.controllers.add(controller);
        return this;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public synchronized Configuration setDevices(List<Device> devices) {
        this.devices = devices;
        return this;
    }

    public Device getDevice(String id){
        if (devices == null)return null;
        for (Device device : devices) {
            if (device.getId().equals(id)) return device;
        }
        return null;
    }

    public synchronized Configuration addDevice(Device device) {
        if (devices == null){
            devices = new ArrayList<Device>();
        }
        this.devices.add(device);
        return this;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public synchronized Configuration setZones(List<Zone> zones) {
        this.zones = zones;
        return this;
    }

    public Zone getZone(String id){
        for (Zone zone : zones) {
            if (zone.getId().equals(id)) return zone;
        }
        return null;
    }

    public synchronized Configuration addZone(Zone zone) {
        if (zones == null){
            zones = new ArrayList<Zone>();
        }
        this.zones.add(zone);
        return this;
    }

    public List<Timer> getTimers() {
        return timers;
    }

    public synchronized Configuration setTimers(List<Timer> timers) {
        this.timers = timers;
        return this;
    }

    public Timer getTimer(String id){
        for (Timer t : timers) {
            if (t.getId().equals(id)) return t;
        }
        return null;
    }

    public synchronized Configuration addTimer(Timer timer) {
        if (timers == null){
            timers = new ArrayList<Timer>();
        }
        this.timers.add(timer);
        return this;
    }

    public String getDeviceUrl(String deviceId){
        Device device = getDevice(deviceId);
        if (device == null){
            return null;
        }
        Controller controller = this.getController(device.getControllerId());
        if (controller == null){
            return null;
        }

        StringBuffer url = new StringBuffer();

        if (controller.getIp() == null) return null;
        if (controller.getProtocol() == null) controller.setProtocol(Controller.HTTP_PROTOCOL);
        if (controller.getPort() == 0) controller.setPort(80);

        url.append(controller.getProtocol()).append("://");
        url.append(controller.getIp()).append(":").append(controller.getPort());
        url.append("/rs/gpio/");
        url.append(device.getGpio());
        return url.toString();
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

        buffer.append(listToString("controllers", controllers, notFirst)) ;
        buffer.append(listToString("devices", devices, notFirst)) ;
        buffer.append(listToString("zones", zones, notFirst)) ;
        buffer.append(listToString("timers", timers, notFirst)) ;

        buffer.append(" }");
        return  buffer.toString();
    }

    private String listToString(String listName, List list, boolean notFirst){
        if (list == null)return "";
        if (list.size() == 0)return "";

        StringBuffer buffer = new StringBuffer();
        if (notFirst)buffer.append(", ");
        buffer.append(CoreUtils.listToString(listName, list));

        return buffer.toString();
    }

    public String prettyPrint() {
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\n");

        if (id != null){
            buffer.append("  \"id\": \"").append(id).append("\"");
            notFirst = true;
        }

        if (name != null){
            if (notFirst) buffer.append(",\n");
            buffer.append("  \"name\": \"").append(name).append("\"");
            notFirst = true;
        }

        if (controllers != null){
            boolean notFirstController = false;
            if (notFirst) buffer.append(",\n");
            buffer.append("  \"controllers\": [ ");

            for (Controller controller : controllers) {
                if (notFirstController) buffer.append(",");
                buffer.append(controller.prettyPrint());
                notFirstController = true;
            }

            buffer.append("\n  ]");
        }

        buffer.append("\n}");
        return  buffer.toString();
    }

    public synchronized Configuration build(String jsonString){
        return this.build(Document.parse(jsonString));
    }

    public synchronized Configuration build(Document configDocument){

        if (configDocument.containsKey("id")){
            this.id = configDocument.getString("id");
        }

        if (configDocument.containsKey("name")){
            this.name = configDocument.getString("name");
        }

        if (configDocument.containsKey("controllers")){
            this.controllers = new ArrayList<Controller>();
            List<Document> controllerDocuments = (List<Document>) configDocument.get("controllers");
            for (Document ctrl : controllerDocuments){
                this.controllers.add(new Controller().build(ctrl));
            }
        }

        if (configDocument.containsKey("devices")){
            this.devices = new ArrayList<Device>();
            List<Document> docs = (List<Document>) configDocument.get("devices");
            for (Document doc : docs){
                this.devices.add(new Device().build(doc));
            }
        }

        if (configDocument.containsKey("zones")){
            this.zones = new ArrayList<Zone>();
            List<Document> docs = (List<Document>) configDocument.get("zones");
            for (Document doc : docs){
                this.zones.add(new Zone().build(doc));
            }
        }

        if (configDocument.containsKey("timers")){
            this.timers = new ArrayList<Timer>();
            List<Document> docs = (List<Document>) configDocument.get("timers");
            for (Document doc : docs){
                this.timers.add(new Timer().build(doc));
            }
        }

        return this;
    }

    public synchronized Configuration build(Configuration configuration){

        this.id = configuration.getId();
        this.name = configuration.getName();
        this.controllers = configuration.getControllers();
        this.devices = configuration.getDevices();
        this.zones = configuration.getZones();
        this.timers = configuration.getTimers();

        return this;
    }
}