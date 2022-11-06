package org.cch.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import org.cch.entity.register.DeviceName;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(	name = "device")
public class Device extends PanacheEntityBase {

    @Id
    @Column(nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    public UUID id;
    @Column(name = "name", nullable = false)
    public String name;
    @Column(name = "model", nullable = false)
    public String model;
    @Column(name = "volume", nullable = false)
    public double volume;
    @Column(name = "battery", nullable = false)
    public double battery;
    @Column(name = "created", nullable = false)
    public Instant created;


    public static List<Device> listDeviceLog (String name, Instant timestamp) {
        return Device.list("name = :name and created >= :created ",
                Sort.ascending("created"),
                Parameters.with("name", name).and("created", timestamp));
    }

    public static List<DeviceName> listDeviceName() {
        return Device.findAll().project(DeviceName.class).stream().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", volume=" + volume +
                ", battery=" + battery +
                ", created=" + created +
                '}';
    }
}
