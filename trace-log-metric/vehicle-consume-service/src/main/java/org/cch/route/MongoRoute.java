package org.cch.route;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import com.mongodb.client.MongoClient;

import io.quarkus.mongodb.MongoClientName;

@ApplicationScoped
public class MongoRoute extends RouteBuilder {

    @Inject
    @MongoClientName("mongoVehicleClient")
    MongoClient mongoClient1;

    @Override
    public void configure() throws Exception {
        from("direct:vihicleMongoInsert")
                .log(LoggingLevel.INFO, "MongoDbOperation: ${headers[CamelMongoDbOperation]}")
                .log(LoggingLevel.INFO, "CamelMongoDbResultTotalSize: ${headers[CamelMongoDbResultTotalSize]}")
                .log(LoggingLevel.INFO, "CamelMongoDbDatabase: ${headers[CamelMongoDbDatabase]}")
                .log(LoggingLevel.INFO, "CamelMongoDbCollection: ${headers[CamelMongoDbCollection]}")
                .to("mongodb:mongoVehicleClient?database=myOtherDb&collection=myCollection&operation=insert")
                .log(LoggingLevel.INFO, "CamelMongoWriteResult: ${headers[CamelMongoWriteResult]}")
                .log(LoggingLevel.INFO, "_id: ${headers[_id]}");
                
    }

}
