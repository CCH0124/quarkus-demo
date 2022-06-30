package kafka.route;

import entity.Product;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Random;
import java.util.UUID;


public class ProducerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:tick?period={{kafka.time.period}}")
                .setBody( exchange -> new Product(UUID.randomUUID().toString(), new Random().nextInt(200)+1, new Random().nextInt(10)+1))
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(KafkaConstants.KEY, constant("Camel"))
                .log("New Producer: ${body}")
                .to("kafka:{{kafka.topic}}?brokers={{kafka.brokers.host}}");
    }
}