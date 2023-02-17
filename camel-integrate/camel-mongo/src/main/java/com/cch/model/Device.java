package com.cch.model;

public class Device {
    public String id;
    public double memory;
    public double battery;
    public Device(String id, double memory, double battery) {
        this.id = id;
        this.memory = memory;
        this.battery = battery;
    }
    @Override
    public String toString() {
        return "Device [id=" + id + ", memory=" + memory + ", battery=" + battery + "]";
    }

    
}
