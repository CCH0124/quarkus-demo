package org.cch;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cch.model.Vehicle;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import io.smallrye.mutiny.Multi;

@Path("/vehicles")
public class VehicleResource {

    @Inject
    @Channel("played-vehicles")
    Publisher<Vehicle> vehicles;
 
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<Vehicle> stream() {
        return vehicles;
    }
}
