package org.cch.route;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.cch.config.MqttConfig;
import org.cch.entity.Vehicle;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.paho;

@ApplicationScoped
public class MqttRouteBuilder extends RouteBuilder {

    @Inject
    MqttConfig mqttConfig;

    Properties properties = new Properties();
    
    @PostConstruct
    public void init() {
        properties.setProperty("com.ibm.ssl.keyStore", mqttConfig.connect().clientKeystore());
        properties.setProperty("com.ibm.ssl.keyStorePassword", mqttConfig.connect().clientKeypass());
        properties.setProperty("com.ibm.ssl.trustStore", mqttConfig.connect().trustKeystore());
        properties.setProperty("com.ibm.ssl.trustStorePassword", mqttConfig.connect().trustKeypass());
    }


    @Override
    public void configure() throws Exception {
        // TODO Auto-generated method stub

        from("timer:tick?period={{time.period}}")
        .routeId("Faker-Data")
        .setHeader("vehicleId", constant("itachi-" + new Random().nextInt(3) + 1))
        .setBody( exchange -> {
            Vehicle ve = new Vehicle();
            ve.vehicleId = exchange.getIn().getHeader("vehicleId", String.class);
            ve.speed = new Random().nextFloat(100);
            ve.direction = new Random().nextDouble(100);
            ve.tachometer = new Random().nextDouble(1000, 3800);
            ve.dynamical = new Random().nextFloat(50);
            ve.timestamp = new Date();
            return ve;
        })
        .marshal().json(JsonLibrary.Jackson)
        .log("New Vehicle: ${body}")
        .to(paho(mqttConfig.connect().topic().replace("+", "${vehicleId}")).brokerUrl(mqttConfig.connect().brokerUrl())
        .sslClientProps(properties));

        from(paho(mqttConfig.connect().topic()).brokerUrl(mqttConfig.connect().brokerUrl())
        .sslClientProps(properties))
                .routeId("EMQX-vehicle")
                .log(LoggingLevel.INFO, "start vehicle route")
                .log(LoggingLevel.DEBUG,"${headers}")
                .log(LoggingLevel.DEBUG, "${body}")
                .unmarshal().json(Vehicle.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        // TODO Auto-generated method stub
                        Vehicle body = exchange.getIn().getBody(Vehicle.class);
                        body.persist();
                        // exchange.getMessage().setHeader("VehicleId", constant(body.vehicleId));
                        exchange.getIn().setHeader("VehicleId", constant(body.vehicleId));
                    }
                    
                })
                .log(LoggingLevel.INFO, "vehicle persist");

    }
    
}
