package org.cch.route;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
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
        from(direct("mqtt"))
                .routeId("MQTT_Instance")
                .log(LoggingLevel.INFO, "start mqtt route")
                .log(LoggingLevel.DEBUG,"${headers}; ${body}")
                .to(paho(mqttConfig.connect().topic()).brokerUrl(mqttConfig.connect().brokerUrl())
                        .sslClientProps(properties))
                .log(LoggingLevel.DEBUG, "${body}")
                .unmarshal().json(Vehicle.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        // TODO Auto-generated method stub
                        Vehicle body = exchange.getIn().getBody(Vehicle.class);
                        body.persist();
                        exchange.getMessage().setHeader("VehicleId", constant(body.vehicleId));
                    }
                    
                })
                .log(LoggingLevel.INFO, "vehicle ${VehicleId} persist");

    }
    
}
