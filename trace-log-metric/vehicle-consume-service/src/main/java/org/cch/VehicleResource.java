package org.cch;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cch.model.Vehicle;
import org.eclipse.microprofile.reactive.messaging.Channel;

import io.smallrye.mutiny.Multi;

@Path("/vehicles")
public class VehicleResource {
    @Channel("played-vehicles")
    Multi<Vehicle> vehicles;
 
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Vehicle> stream() {
        return vehicles;
    }
}
