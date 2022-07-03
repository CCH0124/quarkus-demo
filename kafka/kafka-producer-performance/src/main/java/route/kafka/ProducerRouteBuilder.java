package route.kafka;

import entity.MyEvent;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ProducerRouteBuilder extends RouteBuilder {
    /**
     * bufferMemorySize default is 33554432
     * compressionCodec none、gzip、snappy、lz4
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        from("timer:tick?period={{kafka.time.period}}")
                .setBody( exchange -> new MyEvent(UUID.randomUUID().toString(), "event-" + new Random().nextInt(100000)+1, "performance", System.currentTimeMillis()))
                .log("New Producer: ${body}")
                .to("kafka:{{kafka.topic}}?brokers={{kafka.brokers.host}}&clientId=performance" +
                        "&bufferMemorySize={{kafka.producer.bufferMemorySize}}" +
                        "&producerBatchSize={{kafka.producer.producerBatchSize}}" +
                        "&lingerMs={{kafka.producer.lingerMs}}" +
                        "&compressionCodec={{kafka.producer.compressionCodec}}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        List<RecordMetadata> recordMetaData1 = (List<RecordMetadata>)
                                exchange.getIn().getHeader(KafkaConstants.KAFKA_RECORDMETA);
                        for (RecordMetadata rd : recordMetaData1) {
                            log.info("producer topic is:"+ rd.topic());
                            log.info("producer partition is:" + rd.partition());
                            log.info("producer partition message is:" + rd.toString());
                        }
                    }
                });
    }
}
