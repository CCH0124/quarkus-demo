package org.cch.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.cch.entity.Device;
import org.cch.outbound.DeviceSimulation;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class MqttDeviceMessageConsumer {
    private static final Logger LOG = Logger.getLogger(MqttDeviceMessageConsumer.class);
    @Incoming("deviceIn")
    @Outgoing("my-data-stream")
    @Broadcast
    public DeviceSimulation process(byte[] data) {
        try {
            var device = new ObjectMapper().readValue(data, DeviceSimulation.class);
            LOG.info("Consumer Receiving readings: " + device.toString());
            return device;
        } catch (IOException e) {
            LOG.error("convert error.");
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
