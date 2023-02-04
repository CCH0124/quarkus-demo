package org.cch.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Brand {
    public String vehicleId;
    public String name;

    public Brand(String vehicleId, String name) {
        this.vehicleId = vehicleId;
        this.name = name;
    }

}
