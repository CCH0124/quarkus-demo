package org.cch.configuration;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "producer")
public interface MqttDeviceMessageProducerConfig {

    Generate generate();

    interface Generate {
        @WithDefault(value = "1")
        int duration();
    }
}
