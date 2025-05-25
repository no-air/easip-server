package com.noair.easip.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flywayBean() {
        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .load();
        flyway.repair();
        flyway.migrate();
        return flyway;
    }
} 