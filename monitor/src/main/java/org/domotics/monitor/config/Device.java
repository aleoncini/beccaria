package org.domotics.monitor;

public class Device {
    private String controller;
    private int pin;
    private String description;

    public String getController() {
        return controller;
    }

    public Device setController(String controller) {
        this.controller = controller;
        return this;
    }

    public int getPin() {
        return pin;
    }

    public Device setPin(int pin) {
        this.pin = pin;
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
    public String toString(){
        return description;
    }
}
