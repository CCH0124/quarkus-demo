package org.cch.stream;

import javax.inject.Inject;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.apache.camel.component.mongodb.MongoDbOperation;
import org.apache.camel.component.mongodb.converters.MongoDbBasicConverters;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.cch.model.Brand;
import org.cch.model.Vehicle;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class BrandEvent {

    @Inject
    Logger logger;

    @Inject
    ProducerTemplate template;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Incoming("brands")
    public void createBrand(Brand brand) {
        logger.infov("New brand: {0}", brand.toString());
    }

    @Incoming("played-vehicles")
    public void vehicleInsert(ConsumerRecord<String, Vehicle> record) {
        logger.infov("Vehicle: {0}", record.value());
        Map<String, Object> scoreMap = Map.of(MongoDbConstants.COLLECTION, "vehicles", MongoDbConstants.DATABASE,
                "vehicle", MongoDbConstants.OPERATION_HEADER, MongoDbOperation.insert);
        try {
            String writeValueAsString = objectMapper.writeValueAsString(record.value());
            Document fromStringToDocument = MongoDbBasicConverters.fromStringToDocument(writeValueAsString);
            logger.infov("Document: {0}", fromStringToDocument.toString());
            template.sendBodyAndHeaders("direct:vihicleMongoInsert", fromStringToDocument, scoreMap);
        } catch (JsonProcessingException e) {
            logger.error("Parser Error.");
            logger.errorf("Parser Error Message: {0}", e.getMessage());
            e.printStackTrace();
        }

    }
}
