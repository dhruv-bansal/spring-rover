package org.techpleid.springframework.insights.config;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResolvedConfigurationStore {

    // class -> (method, config)
    private Map<String, Map<String, ActionConfiguration.Config>> targetedConfiguration =
            new HashMap<String, Map<String, ActionConfiguration.Config>>();

    public Map<String, Map<String, ActionConfiguration.Config>> getTargetedConfiguration() {
        return targetedConfiguration;
    }

    public void populateTargetConfiguration(ActionConfiguration.Config config) {

        String classNamePattern = config.getClassName();
        if (classNamePattern.contains("*")) {

            // populating all the resources that matches the pattern
        } else {
            // populate this absolute resource

            Map<String, ActionConfiguration.Config> configByMethod = null;
            String className = config.getClassName();

            if (null == targetedConfiguration.get(className)) {
                configByMethod = new HashMap<String, ActionConfiguration.Config>();
            }
            configByMethod.put(config.getMethodName(), config);
            targetedConfiguration.put(className, configByMethod);
        }

        // make proper input for PathMatchingResourcePatternResolver
        // TODO:
    }

    protected void populateAllResourceForClassPattern(String classNamePattern) {
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver =
                new PathMatchingResourcePatternResolver();

        Resource resources = pathMatchingResourcePatternResolver.getResource(classNamePattern);
        for (Resource resource : resources) {

            if (resource.isReadable()) {
                try {

                } catch (Throwable ex) {
                    // throws RuntimeException();
                }
            }
        }
    }

}
