package org.techpleid.springframework.insights.actions.matcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techpleid.springframework.insights.actions.ActionObject;
import org.techpleid.springframework.insights.config.ActionConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AbsoluteMatcherTest {

    @Mock
    private ActionConfiguration configuration;
    @Mock
    private ActionObject actionObject;

    @InjectMocks
    private AbsoluteMatcher absoluteMatcher;


    @BeforeEach
    void setUp() {
        absoluteMatcher = new AbsoluteMatcher();
    }

    @Test
    void givenAbsoluteConfiguration_whenTargetClassAndMethodMatchesConfiguration_thenReturnTrue() {

        // given
        Map<String, Map<String, ActionConfiguration.Config>> searchableAbsoluteClassConfig = new HashMap<>();
        Map<String, ActionConfiguration.Config> configByMethod = new HashMap<>();
        String className = "org.techpleaid.test.simpleclass";
        String methodName = "simpleMethod";

        // configuration
        ActionConfiguration.Config config = new ActionConfiguration.Config();
        config.setClassName(className);
        config.setMethodName(methodName);
        config.setLogInputParameters(true);
        config.setLogReturnType(true);

        configByMethod.put(methodName, config);
        searchableAbsoluteClassConfig.put(className, configByMethod);

        Mockito.when(configuration.getSearchableAbsoluteClassConfig()).thenReturn(searchableAbsoluteClassConfig);
        Mockito.when(actionObject.getMethod()).thenReturn(methodName);
        Mockito.when(actionObject.getClazz()).thenReturn(className);

        // when
        boolean match = absoluteMatcher.match(configuration, actionObject);

        //then
        assertTrue(match);
    }

    @Test
    void givenAbsoluteConfiguration_whenTargetClassDoesNotMatchesConfiguration_thenReturnFalse() {

        // given
        Map<String, Map<String, ActionConfiguration.Config>> searchableAbsoluteClassConfig = new HashMap<>();
        Map<String, ActionConfiguration.Config> configByMethod = new HashMap<>();
        String className = "org.techpleaid.test.simpleclass";
        String methodName = "simpleMethod";

        // configuration
        ActionConfiguration.Config config = new ActionConfiguration.Config();
        config.setClassName(className);
        config.setMethodName(methodName);
        config.setLogInputParameters(true);
        config.setLogReturnType(true);

        configByMethod.put(methodName, config);
        searchableAbsoluteClassConfig.put(className, configByMethod);

        Mockito.when(configuration.getSearchableAbsoluteClassConfig()).thenReturn(searchableAbsoluteClassConfig);
        Mockito.when(actionObject.getMethod()).thenReturn(methodName);
        Mockito.when(actionObject.getClazz()).thenReturn("SomeOtherClass");

        // when
        boolean match = absoluteMatcher.match(configuration, actionObject);

        //then
        assertFalse(match);
    }

    @Test
    void givenAbsoluteConfiguration_whenTargetMethodDoesNotMatchesConfiguration_thenReturnFalse() {

        // given
        Map<String, Map<String, ActionConfiguration.Config>> searchableAbsoluteClassConfig = new HashMap<>();
        Map<String, ActionConfiguration.Config> configByMethod = new HashMap<>();
        String className = "org.techpleaid.test.simpleclass";
        String methodName = "simpleMethod";

        // configuration
        ActionConfiguration.Config config = new ActionConfiguration.Config();
        config.setClassName(className);
        config.setMethodName(methodName);
        config.setLogInputParameters(true);
        config.setLogReturnType(true);

        configByMethod.put(methodName, config);
        searchableAbsoluteClassConfig.put(className, configByMethod);

        Mockito.when(configuration.getSearchableAbsoluteClassConfig()).thenReturn(searchableAbsoluteClassConfig);
        Mockito.when(actionObject.getMethod()).thenReturn("someothermethod");
        Mockito.when(actionObject.getClazz()).thenReturn(className);

        // when
        boolean match = absoluteMatcher.match(configuration, actionObject);

        //then
        assertFalse(match);
    }
}