package org.cch.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "mqtt")
public interface MqttConfig {
    Connect connect();
    interface Connect {
        String clientKeystore();
        String clientKeypass();
        String trustKeystore();
        String trustKeypass();
        String brokerUrl();
        String topic();
    }

}
