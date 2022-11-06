package org.cch;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.cch.entity.Device;
import org.jboss.resteasy.reactive.RestQuery;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.stream.Collectors;

@Path("/device")
public class DeviceResource {

    @GET
    @Path("/logs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceLogsFromTimestamp(@RestQuery String name, @RestQuery Long timestamp) {

        var devices = Device.listDeviceLog(name, Instant.ofEpochMilli(timestamp));
        if (devices.size() == 0 || devices.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(devices).build();
    }

    @GET
    @Path("/logs/names")
    @Timed(value = "logs.names", longTask = true, description = "How log it get data")
    @Counted(value = "get.logs.names", description = "Number of called API")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceNameLogs() {

        var names = Device.listDeviceName().stream().distinct().map(d -> d.name).collect(Collectors.toList());
        if (names.size() == 0 ) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(names).build();
    }
}
