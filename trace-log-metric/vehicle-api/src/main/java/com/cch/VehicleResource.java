package com.cch;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cch.model.Vehicle;
import com.oracle.svm.core.annotate.Delete;

import io.quarkus.panache.common.Sort;

@Path("/vehicle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleResource {

    @Delete
    @Path("/{id}")
    public Response delete(@PathParam("id") String vehicleId) {
        Vehicle.deleteFromVehicleId(vehicleId);
        return Response.accepted().build();
    }

    @GET
    @Path("/")
    public Response vehicleList(@QueryParam("id") String vehicleId, @QueryParam("page") int page, @DefaultValue("20") @QueryParam("size") int size,  @QueryParam("orderBy") String orderBy) {
        if (page < 1) page = 1;
        if (size < 1) size = 20;
        page = page - 1;
        Sort.Direction direction = Sort.Direction.Ascending;
        if (Objects.equals(orderBy, "desc")) {
            direction = Sort.Direction.Descending;
        }
       List<Vehicle> findByVehivleId = Vehicle.findByVehicleId(vehicleId, page, size, direction);
        if (findByVehivleId.size() == 0) {
            return Response.status(Status.NOT_FOUND).build();
        }
       return Response.ok(findByVehivleId).build();
    }
}