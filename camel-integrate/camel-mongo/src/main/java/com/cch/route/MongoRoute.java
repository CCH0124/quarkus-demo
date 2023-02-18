package com.cch.route;

import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.apache.camel.component.mongodb.MongoDbOperation;

import com.cch.model.Device;
import com.mongodb.client.MongoClient;

import io.quarkus.mongodb.MongoClientName;


public class MongoRoute extends RouteBuilder {

    @Inject
    @MongoClientName("device")
    MongoClient mongoDeviceClient;
    
    @Override
    public void configure() throws Exception {

        from("timer:gen?delay=2000&period=1500")
        .setBody( exchange -> new Device(UUID.randomUUID().toString(), new Random().nextDouble(70), new Random().nextDouble(100)))
        .log(LoggingLevel.INFO, "${body}")
        .setHeader(MongoDbConstants.COLLECTION, constant("vehicles"))
        .setHeader( MongoDbConstants.DATABASE, constant("vehicle"))
        .setHeader( MongoDbConstants.OPERATION_HEADER, constant(MongoDbOperation.insert))
        .to("direct:vihicleMongoInsert");
        
        from("direct:vihicleMongoInsert")
                .routeId("VehicleToMongo")
                .log(LoggingLevel.INFO, "MongoDbOperation: ${headers[CamelMongoDbOperation]}")
                .log(LoggingLevel.INFO, "CamelMongoDbResultTotalSize: ${headers[CamelMongoDbResultTotalSize]}")
                .log(LoggingLevel.INFO, "CamelMongoDbDatabase: ${headers[CamelMongoDbDatabase]}")
                .log(LoggingLevel.INFO, "CamelMongoDbCollection: ${headers[CamelMongoDbCollection]}")
                // .threads(5).maxQueueSize(10)
                .to("mongodb:device?database=vehicle&collection=vehicles&operation=insert&createCollection=true")
                .log(LoggingLevel.INFO, "CamelMongoWriteResult: ${headers[CamelMongoWriteResult]}")
                .log(LoggingLevel.INFO, "_id: ${headers[_id]}");
    }
    
}
