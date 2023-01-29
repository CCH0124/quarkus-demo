package org.cch.stream;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cch.model.Brand;
import org.cch.model.Vehicle;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.Record;

@ApplicationScoped
public class VehicleKafkaProducer {

    private List<Brand> brands = List.of(
            new Brand("JN8AZ2NC5B9300256", "Volvo"),
            new Brand("JH4KA8160RC002560", "Honda"),
            new Brand("1MEBM62F2JH693379", "Ford"),
            new Brand("SMT905JN26J262542", "Maserati"),
            new Brand("JH4CC2660PC002236", "BMW"));

    private Random random = new Random();

    @Inject
    Logger logger;

    // Populates movies into Kafka topic
    @Outgoing("brand")
    public Multi<Record<String, Brand>> movies() {
        return Multi.createFrom().items(brands.stream()
                .map(m -> Record.of(m.vehicleId, m)));
    }

    @Outgoing("vehicle")
    public Multi<Record<String, Vehicle>> generate() {
        return Multi.createFrom().ticks().every(Duration.ofMillis(1000))
                .onOverflow().drop()
                .map(tick -> {
                    Brand brand = brands.get(random.nextInt(brands.size()));
                    var speed = new Random().nextFloat(100);
                    var direction = new Random().nextDouble(100);
                    var tachometer = new Random().nextDouble(1000, 3800);
                    var dynamical = new Random().nextFloat(50);
                    var timestamp = new Date();
                    logger.infof("Vehicle id is %s", brand.vehicleId);
                    return Record.of("tw", new Vehicle(brand.vehicleId, speed, direction, tachometer, dynamical, timestamp));
                });
    }
}
