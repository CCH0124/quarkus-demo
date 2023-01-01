package com.cch.router;

import org.apache.camel.Predicate;

import javax.inject.Inject;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.debezium.DebeziumConstants;

import com.cch.config.PostgresqlConfig;
import com.cch.entity.Movie;

import io.debezium.data.Envelope;

public class MovieRoute extends RouteBuilder {

    @Inject
    PostgresqlConfig pgConfig;

    private static final String EVENT_TYPE = ".movie";

    @Override
    public void configure() throws Exception {
        
        final Predicate isCreateEvent  =
                    header(DebeziumConstants.HEADER_OPERATION).in(
                            constant(Envelope.Operation.READ.code()),
                            constant(Envelope.Operation.CREATE.code()));

        final Predicate isMovieEvent =
                            header(DebeziumConstants.HEADER_IDENTIFIER).endsWith(EVENT_TYPE);

        
        from("debezium-postgres:postgres_endpoint?"
                + "databaseHostname="+ pgConfig.database().hostname()
                + "&databasePort=" + pgConfig.database().port()
                + "&databaseUser="+ pgConfig.database().user()
                + "&databasePassword="+ pgConfig.database().password()
                + "&databaseDbname="+ pgConfig.database().name()
                + "&databaseServerName=movies-pg-server"
                + "&schemaIncludeList="+ pgConfig.database().schema()
                + "&tableIncludeList="+ pgConfig.database().tables() // Debezium only sends events for the modifications of OutboxEvent table and not all tables
                + "&offsetStorage=org.apache.kafka.connect.storage.FileOffsetBackingStore"
                + "&offsetStorageFileName=/tmp/offset.dat"
                + "&offsetFlushIntervalMs=60000"
                + "&databaseHistoryFileFilename=/tmp/dbhistory.dat"
        )
        .routeId(MovieRoute.class.getName() + ".movie")
        .log(LoggingLevel.DEBUG, "Incoming message ${body} with headers ${headers}")
        .choice()
            .when(isCreateEvent)
                .filter(isMovieEvent)
                    .convertBodyTo(Movie.class)
                    .log(LoggingLevel.DEBUG, "Converted to logical class ${body}")
                .endChoice()
            .otherwise()
                .log(LoggingLevel.WARN, "Unknown type ${headers[" + DebeziumConstants.HEADER_IDENTIFIER + "]}")
        .endParent();
        ;

    }
    
}
