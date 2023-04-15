package org.cch.deserializer;

import org.cch.model.Vehicle;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class VehicleDeserializer extends ObjectMapperDeserializer<Vehicle>  {

    public VehicleDeserializer() {
        super(Vehicle.class);
    }
    
}
