package org.cch.stream;

import javax.inject.Inject;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.apache.camel.component.mongodb.MongoDbOperation;
import org.apache.camel.component.mongodb.converters.MongoDbBasicConverters;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.cch.model.Brand;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import java.util.Map;

public class BrandEvent {
    
    @Inject
    Logger logger;

    @Inject
    ProducerTemplate template;



    @Incoming("brands")
    public void createBrand(Brand brand) {
        logger.infov("New brand: {0}", brand.toString());
    }


    @Incoming("played-vehicles")
    public void vehicleInsert(ConsumerRecord<String, String> record) {
        logger.infov("Vehicle: {0}", record.value());
        Map<String, Object> scoreMap = Map.of(MongoDbConstants.COLLECTION, "vehicles", MongoDbConstants.DATABASE, "vehicle", MongoDbConstants.OPERATION_HEADER, MongoDbOperation.insert);
        Document fromStringToDocument = MongoDbBasicConverters.fromStringToDocument(record.value());
        logger.infov("Document: {0}", fromStringToDocument.toString());
        template.sendBodyAndHeaders("direct:vihicleMongoInsert", fromStringToDocument, scoreMap);

    }
}
