package org.techpleid.springframework.insights.actions.matcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.techpleid.springframework.insights.actions.ActionObject;
import org.techpleid.springframework.insights.config.ActionConfiguration;
import org.techpleid.springframework.insights.config.ResolvedConfigurationStore;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class ActionTargetMatcher {

    private ResolvedConfigurationStore resolvedConfigurationStore;

    public boolean match(ActionObject targetActionObject) {

        boolean isMatched = false;

        String targetClass = targetActionObject.getClazz();
        log.trace("Evaluation condition for class {} and method {}", targetClass,
                targetActionObject.getMethod());

        Map<String, Map<String, ActionConfiguration.Config>> targetedConfiguration =
                resolvedConfigurationStore.getTargetedConfiguration();

        // if complete class name is present in the configuration
        if (targetedConfiguration.containsKey(targetClass)) {

            log.debug("Target class found in configuration {}", targetClass);
            Map<String, ActionConfiguration.Config> stringConfigMap =
                    targetedConfiguration.get(targetClass);

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
