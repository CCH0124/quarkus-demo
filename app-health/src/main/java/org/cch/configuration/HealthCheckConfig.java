package org.cch.configuration;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "health")
public interface HealthCheckConfig {

    Readiness readiness();

    interface Readiness {
        String externalURL();
    }
}
