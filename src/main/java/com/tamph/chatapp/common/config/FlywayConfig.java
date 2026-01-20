package com.tamph.chatapp.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class FlywayConfig {

    /**
     * Custom Flyway migration strategy for development
     * Useful for cleaning and re-migrating during development
     */

    @Bean
    @Profile("dev")
    public FlywayMigrationStrategy cleanAndMigrateStrategy() {
        return flyway -> {
            log.info("🔄 Running Flyway migration in DEV mode...");

            // Uncomment below to clean database on startup (BE CAREFUL!)
            // flyway.clean();
            // log.warn("⚠️ Database cleaned!");

            flyway.migrate();
            log.info("✅ Flyway migration completed successfully!");
        };
    }

    /**
     * Production migration strategy - only migrate, never clean
     */

    @Bean
    @Profile("prod")

    public FlywayMigrationStrategy prodMigrateStrategy() {
        return flyway -> {
            log.info("🚀 Running Flyway migration in PROD mode...");
            flyway.migrate();
            log.info("✅ Flyway migration completed successfully!");
        };
    }
}
