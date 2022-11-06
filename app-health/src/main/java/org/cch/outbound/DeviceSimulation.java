package org.cch.outbound;


public class DeviceSimulation {
    public String name;
    public String model;
    public double volume;
    public double battery;
    public Long created;

    public DeviceSimulation(String name, String model, double volume, double battery, Long created) {
        this.name = name;
        this.model = model;
        this.volume = volume;
        this.battery = battery;
        this.created = created;
    }

    public DeviceSimulation() {
    }

    @Override
    public String toString() {
        return "DeviceSimulation{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", volume=" + volume +
                ", battery=" + battery +
                ", created=" + created +
                '}';
    }
}
