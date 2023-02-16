package org.cch.route;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

import com.mongodb.client.MongoClient;

import io.quarkus.mongodb.MongoClientName;

@ApplicationScoped
public class MongoRoute extends RouteBuilder {
    // multiple MongoDB servers
    // @Inject
    // @MongoClientName("vehicle")
    // MongoClient mongoVehicleClient;

    @Override
    public void configure() throws Exception {


        from("direct:vihicleMongoInsert")
                .routeId("VehicleToMongo")
                .log(LoggingLevel.INFO, "MongoDbOperation: ${headers[CamelMongoDbOperation]}")
                .log(LoggingLevel.INFO, "CamelMongoDbResultTotalSize: ${headers[CamelMongoDbResultTotalSize]}")
                .log(LoggingLevel.INFO, "CamelMongoDbDatabase: ${headers[CamelMongoDbDatabase]}")
                .log(LoggingLevel.INFO, "CamelMongoDbCollection: ${headers[CamelMongoDbCollection]}")
                .threads(5).maxQueueSize(10)
                .to("mongodb:camelMongoClient?database=vehicle&collection=vehicles&operation=save&createCollection=true")
                .log(LoggingLevel.INFO, "CamelMongoWriteResult: ${headers[CamelMongoWriteResult]}")
                .log(LoggingLevel.INFO, "_id: ${headers[_id]}");
                

    }

}
