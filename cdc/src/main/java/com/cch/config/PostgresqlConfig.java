package com.cch.config;

import java.util.List;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "pg")
public interface PostgresqlConfig {
    
    Database database();

    public interface Database {
        String hostname();
        String port();
        String user();
        String password();
        String name();
        String schema();
        List<String> tables();
    }
}
