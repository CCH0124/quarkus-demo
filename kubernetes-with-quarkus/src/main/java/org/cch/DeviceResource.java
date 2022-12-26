package org.cch;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.cch.entity.Vehicle;

import io.quarkus.panache.common.Sort;

@Path("/device")
public class DeviceResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehicle> getTachometerGreaterThan(@QueryParam(value = "tachometer") double tachometer) {
        return Vehicle.list("tachometer >= :tachometer ", Sort.by("timestamp"), Map.of("tachometer", tachometer));
    }
}
