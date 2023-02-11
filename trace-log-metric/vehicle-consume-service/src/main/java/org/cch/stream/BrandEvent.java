package org.cch.stream;

import javax.inject.Inject;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.apache.camel.component.mongodb.MongoDbOperation;
import org.cch.model.Brand;
import org.cch.model.Vehicle;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import java.util.Map;

public class BrandEvent {
    
    @Inject
    Logger logger;

    ProducerTemplate template;



    @Incoming("brands")
    public void createBrand(Brand brand) {
        logger.infov("New brand: {0}", brand.toString());
    }


    @Incoming("played-vehicles")
    public void vehicleInsert(Vehicle vehicle) {
        Map<String, Object> scoreMap = Map.of(MongoDbConstants.COLLECTION, "vehicles", MongoDbConstants.DATABASE, "vehicle", MongoDbConstants.OPERATION_HEADER, MongoDbOperation.insert);
        template.requestBodyAndHeaders("direct:vihicleMongoInsert", vehicle, scoreMap);
    }
}
