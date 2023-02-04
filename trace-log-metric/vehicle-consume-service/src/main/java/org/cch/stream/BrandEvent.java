package org.cch.stream;

import javax.inject.Inject;

import org.cch.model.Brand;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

public class BrandEvent {
    
    @Inject
    Logger logger;

    @Incoming("brands")
    public void createBrand(Brand brand) {
        logger.infov("New brand: {0}", brand.toString());
    }
}
