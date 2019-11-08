package org.domotica.monitoring.temperature;

public class CacheList {
    public final static int CACHE_SIZE = 24; //last two days with interval of reading of every 2 hours
    private double[] values = new double[CACHE_SIZE];

    public void put(double value){
        shiftValues();
        // always insert at last position the new value
        values[CACHE_SIZE - 1] = value;
    }

    private void shiftValues() {
        for (int i = 1; i < CACHE_SIZE; i++) {
            values[i-1] = values[i];
        }
    }

    @Override
    public String toString(){
        boolean notFirst = false;
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ ");
        for (int i = 0; i < CACHE_SIZE; i++){
            if (notFirst){
                buffer.append(", ");
            }
            notFirst = true;
            buffer.append(values[i]);
        }
        buffer.append(" ]");
        return  buffer.toString();
    }

}