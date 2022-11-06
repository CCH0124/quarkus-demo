package org.cch.outbound;

import io.smallrye.mutiny.Multi;
import org.cch.configuration.MqttDeviceMessageProducerConfig;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.*;

@ApplicationScoped
public class MqttDeviceMessageProducer {
    @Inject
    MqttDeviceMessageProducerConfig config;

    private static final Logger LOG = Logger.getLogger(MqttDeviceMessageProducer.class);

    Map<String, String> devices = new HashMap<>();

    @Outgoing("deviceOut")
    public Multi<DeviceSimulation> generate() {
        devices.put("itachi-01", "model01");
        devices.put("itachi-02", "model02");
        devices.put("naruto-01", "model03");
        devices.put("naruto-02", "model04");
        devices.put("madara-01", "model05");
        ArrayList<String> deviceNames = new ArrayList<>(devices.keySet());
        LOG.info("set device data.");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(config.generate().duration()))
                .map(x -> {
                    var r = (int)(Math.random()*5);
                    String deviceName = deviceNames.get(r);
                    String deviceModel = devices.get(deviceName);
                    return new DeviceSimulation(deviceName, deviceModel, (int)(Math.random()*100), (int)(Math.random()*100), System.currentTimeMillis());
                });
    }
}
