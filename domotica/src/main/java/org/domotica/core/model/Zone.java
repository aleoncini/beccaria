package org.domotica.core.model;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Zone {
    private String id;
    private String name;
    private Collection<Zone> zones;

    public String getId() {
        return id;
    }

    public Zone setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Zone setName(String name){
        this.name = name;
        return this;
    }

    public Collection<Zone> getZones(){
        return zones;
    }

    public Zone setZones(List<Zone> zones){
        this.zones = zones;
        return this;
    }

    public Zone addZone(Zone zone){
        if (zones == null){
            this.zones = new ArrayList<Zone>();
        }
        this.zones.add(zone);
        return this;
    }

    public Collection<Zone> traverseZones(){
        Collection traversed = new ArrayList<Zone>();
        if (this.hasChildZones()){
            for (Zone zone: zones) {
                traversed.addAll(zone.traverseZones());
            }
        }
        return traversed;
    }

    public boolean hasChildZones(){
        return (zones != null && zones.size() > 0);
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

        if (hasChildZones()){
            boolean notFirstZone = false;
            buffer.append("\"zones\": [ ");
            for (Zone zone : zones) {
                if (notFirstZone) buffer.append(", ");
                buffer.append(zone.toString());
                notFirstZone = true;
            }
            buffer.append(" ]");
        }

        buffer.append(" }");
        return buffer.toString();
    }

    public String prettyPrint() {
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n    {\n");

        if (name != null){
            buffer.append("      \"name\": \"").append(name).append("\"");
            notFirst = true;
        }

        if (hasChildZones()){
            boolean notFirstZone = false;
            buffer.append("      \"zones\": [ ");
            for (Zone zone : zones) {
                if (notFirstZone) buffer.append(",\n");
                buffer.append(zone.prettyPrint());
                notFirstZone = true;
            }
            buffer.append("\n      ]");
        }

        buffer.append("\n    }");
        return  buffer.toString();
    }

    public synchronized Zone build(Document zoneDocument){
        if (zoneDocument.containsKey("id")){
            this.id = zoneDocument.getString("id");
        }
        if (zoneDocument.containsKey("name")){
            this.name = zoneDocument.getString("name");
        }
        if (zoneDocument.containsKey("zones")){
            this.zones = new ArrayList<Zone>();
            List<Document> zoneDocuments = (List<Document>) zoneDocument.get("zones");
            for (Document zone : zoneDocuments){
                this.zones.add(new Zone().build(zone));
            }
        }
        return this;
    }

}
