package org.cch;

import java.util.Map;

import org.cch.config.MqttConnect;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    @Inject
    MqttConnect mqttConnect;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/mqtt/config")
    @Produces(MediaType.APPLICATION_JSON)
    public Response config() {
        return Response.ok(Map.of("url", mqttConnect.broker())).build();
    } 


}
