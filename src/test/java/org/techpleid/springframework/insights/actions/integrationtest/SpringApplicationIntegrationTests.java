package org.techpleid.springframework.insights.actions.integrationtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

/**
 * Boot class for integration test
 */
@SpringBootApplication(scanBasePackages = "org.techpleid.springframework.insights")
@Slf4j
public class SpringApplicationIntegrationTests {

    private static final String ACTIVE_PROFILES = "spring.profiles.active";

    public static void main(final String[] args) {
        SpringApplication.run(SpringApplicationIntegrationTests.class, args);
        final String activeProfile = System.getProperty(ACTIVE_PROFILES);
        log.info("Spring Boot test booted with profiles: {}", activeProfile);
    }
}
