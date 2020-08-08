package org.techpleid.springframework.insights.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.techpleid.springframework.insights.actions.matcher.ActionTargetMatcher;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.rover")
public class ActionConfiguration {

    private List<Config> configs;

    @Autowired
    private ResolvedConfigurationStore resolvedConfigurationStore;


    @Setter
    @Getter
    @EqualsAndHashCode
    public static class Config {

        private String className;
        private String methodName;
        private boolean logInputParameters;
        private boolean logReturnType;
    }

    @PostConstruct
    void populateConfigMap() {

        for (Config config : configs) {
            resolvedConfigurationStore.populateTargetConfiguration(config);
        }
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }
}
