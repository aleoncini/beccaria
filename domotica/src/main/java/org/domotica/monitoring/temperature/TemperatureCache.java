package org.domotica.monitoring.temperature;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Calendar;

@Singleton
@Named("tcache")
public class TemperatureCache {
    public final static String internalCacheName = "internal";
    public final static String externalCacheName = "external";
    public final static String nominalCacheName = "nominal";
    private CacheList internalTemperatureCache = new CacheList();
    private CacheList externalTemperatureCache = new CacheList();
    private CacheList nominalTemperatureCache = new CacheList();

    public void logValue(String cacheName, double value){
        if (cacheName.equalsIgnoreCase(internalCacheName)){
            this.internalTemperatureCache.put(value);
        }
        if (cacheName.equalsIgnoreCase(externalCacheName)){
            this.externalTemperatureCache.put(value);
        }
        if (cacheName.equalsIgnoreCase(nominalCacheName)){
            this.nominalTemperatureCache.put(value);
        }
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer("{\n");
        buffer.append("  \"labels\": ").append(labels()).append(",\n");
        buffer.append("  \"internal\": ").append(internalTemperatureCache.toString()).append(",\n");
        buffer.append("  \"external\": ").append(externalTemperatureCache.toString()).append(",\n");
        buffer.append("  \"nominal\": ").append(nominalTemperatureCache.toString());
        buffer.append("\n}\n");
        return  buffer.toString();
    }

    private String labels(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        hour -= hour % 2;
        int lbls[] = new int[CacheList.CACHE_SIZE];
        for (int i = (lbls.length -1); i >= 0; i--){
            if (hour == 0) {
                hour = 24;
            }
            lbls[i] = hour;
            hour -= 2;
        }
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer("[ ");
        for (int i = 0; i < lbls.length; i++){
            if (notFirst){
                buffer.append(", ");
            }
            notFirst = true;
            buffer.append("\"").append(lbls[i]).append("\"");
        }
        buffer.append(" ]");
        return buffer.toString();
    }
}