package org.cch.outbound;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.cch.entity.Device;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Objects;

@ApplicationScoped
public class DeviceToPgDatabase {
    private static final Logger LOG = Logger.getLogger(DeviceToPgDatabase.class);

    @Transactional
    @Incoming("my-data-stream")
    @Timed(
            value = "dataInsertToPg",
            description = "How log it take to database"
    )
    @Counted(
            value = "number-of-transactions",
            description = "Insert to database. Number of transactions"
    )
    public void process(DeviceSimulation data) {
        if (Objects.isNull(data)) {
            LOG.error("Producer Receive Error.");
        }
        LOG.infof("Received device name : {}, device model: {} ", data.name, data.model);
        Device device = new Device();
        device.name = data.name;
        device.model = data.model;
        device.volume = data.volume;
        device.battery = data.battery;
        device.created = Instant.ofEpochMilli(data.created);
        LOG.infof("Device : {}", device.toString());
        try {
            device.persistAndFlush();
        } catch (PersistenceException pe) {
            LOG.error("Unable to create the parameter", pe);

        }

//        if (persistent) {
//            LOG.info("Data to db.");
//        } else {
//            LOG.error("Insert to db fault.");
//        }
    }
}
