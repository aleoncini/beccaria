package org.domotica.metering;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

@Singleton
@Named("switch_meter")
public class SwitchMeter {
    public final static int INTERVAL = 10;     // expressed in minutes!
    private Map<String, Integer> radiatorMeters;
    private Map<String, Integer> yesterdayMeters;
    private int currentDayOfWeek;

    public SwitchMeter(){
        radiatorMeters = new Hashtable<String, Integer>();
        yesterdayMeters = new Hashtable<String, Integer>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        currentDayOfWeek = getDayOfTheWeek();
    }

    public void add(String deviceId){
        int day = getDayOfTheWeek();
        if (day != currentDayOfWeek){
            yesterdayMeters = radiatorMeters;
            radiatorMeters = new Hashtable<String, Integer>();
            currentDayOfWeek = day;
        }
        radiatorMeters.put(deviceId, this.get(deviceId) + INTERVAL);
    }

    public int get(String deviceId){
        return radiatorMeters.getOrDefault(deviceId, 0);
    }

    private int getDayOfTheWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("{\n");
        buffer.append("  \"today\": ");
        buffer.append(mapToString(radiatorMeters)).append(",\n");
        buffer.append("  \"yesterday\": ");
        buffer.append(mapToString(yesterdayMeters)).append("\n");
        buffer.append("}\n");
        return buffer.toString();
    }

    public String mapToString(Map<String, Integer> map) {
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer("{\n");
        buffer.append("    \"labels\": [ ");
        for (String key : map.keySet()) {
            if (notFirst){
                buffer.append(", ");
            }
            notFirst = true;
            buffer.append("\"").append(key.substring(0,3)).append("\"");
        }
        buffer.append(" ],\n");
        notFirst = false;
        buffer.append("    \"values\": [ ");
        for (Integer val : map.values()) {
            if (notFirst){
                buffer.append(", ");
            }
            notFirst = true;
            buffer.append(val);
        }
        buffer.append(" ]\n  }");
        return buffer.toString();
    }

}