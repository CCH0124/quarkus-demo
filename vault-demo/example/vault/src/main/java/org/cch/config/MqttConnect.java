package org.cch.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "mqtt")
public interface MqttConnect {
    String broker();
    String username();
    String password();
}
