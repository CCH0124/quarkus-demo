package org.cch.health;

import io.smallrye.health.checks.UrlHealthCheck;
import org.cch.configuration.HealthCheckConfig;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;

@ApplicationScoped
public class ReadinessProbe {
    @Inject
    HealthCheckConfig healthCheckConfig;

    @Readiness
    HealthCheck checkURL() {
        return new UrlHealthCheck(healthCheckConfig.readiness().externalURL())
                .name("ExternalURL health check").requestMethod(HttpMethod.GET).statusCode(200);
    }
}

