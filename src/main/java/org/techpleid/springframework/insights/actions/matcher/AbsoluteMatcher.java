package org.techpleid.springframework.insights.actions.matcher;

import lombok.extern.slf4j.Slf4j;
import org.techpleid.springframework.insights.actions.ActionObject;
import org.techpleid.springframework.insights.config.ActionConfiguration;

import java.util.Map;

@Slf4j
public class AbsoluteMatcher {

    public boolean match(ActionConfiguration configuration, ActionObject targetActionObject) {

        boolean isMatched = false;
        Map<String, Map<String, ActionConfiguration.Config>> searchableAbsoluteClassConfig = configuration.getSearchableAbsoluteClassConfig();

        String targetClass = targetActionObject.getClazz();
        log.trace("Evaluation condition for class {} and method {}", targetClass,
                targetActionObject.getMethod());

        // if complete class name is present in the configuration
        if (searchableAbsoluteClassConfig.containsKey(targetClass)) {

            log.debug("Target class found in configuration {}", targetClass);
            Map<String, ActionConfiguration.Config> stringConfigMap =
                    searchableAbsoluteClassConfig.get(targetClass);

            if (stringConfigMap.containsKey(targetActionObject.getMethod())) {

                isMatched = true;
            } else {
                log.debug("Configuration form method {} in class {} not found",
                        targetClass, targetActionObject.getMethod());
                isMatched = false;
            }
        }
        return isMatched;
    }
}
