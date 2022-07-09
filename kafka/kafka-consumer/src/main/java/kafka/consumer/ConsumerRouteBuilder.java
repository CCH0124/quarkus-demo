package kafka.consumer;

import org.apache.camel.builder.RouteBuilder;

public class ConsumerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("kafka:{{kafka.topics}}?brokers={{kafka.brokers}}" +
                "&groupId={{kafka.groupId}}")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");
    }
}
