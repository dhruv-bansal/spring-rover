package org.techpleid.springframework.insights.actions.integrationtest.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.techpleid.springframework.insights.actions.integrationtest.SpringApplicationIntegrationTests;
import org.techpleid.springframework.insights.actions.integrationtest.testpackage1.beans.SimpleBeanInput;
import org.techpleid.springframework.insights.actions.integrationtest.testpackage1.beans.TestSimpleBean1;
import org.techpleid.springframework.insights.actions.integrationtest.testpackage1.beans.TestSimpleBean2;
import org.techpleid.springframework.insights.config.ActionConfiguration;
import org.techpleid.springframework.insights.logback.test.TestEncodedMessageCaptureConsoleAppender;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringApplicationIntegrationTests.class})
@TestPropertySource(properties = {"spring.config.location=classpath:application-logging-action-test.yml"})
class LoggingActionTest {

    @Autowired
    private ActionConfiguration actionConfiguration;
    @Autowired
    private TestSimpleBean1 testSimpleBean1;
    @Autowired
    private TestSimpleBean2 testSimpleBean2;

    private Logger springRoverLogger;

    private Appender mockAppender;

    private TestEncodedMessageCaptureConsoleAppender<ILoggingEvent> testEncodedMessageCaptureConsoleAppender;

    @BeforeEach
    public void setup(){

        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(loggerContext);
        patternLayoutEncoder.setPattern("%m%n");
        patternLayoutEncoder.start();

        springRoverLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("spring.rover.logger");
        // appender
        testEncodedMessageCaptureConsoleAppender = new TestEncodedMessageCaptureConsoleAppender<>();
        testEncodedMessageCaptureConsoleAppender.setEncoder(patternLayoutEncoder);
        testEncodedMessageCaptureConsoleAppender.start();

        springRoverLogger.addAppender(testEncodedMessageCaptureConsoleAppender);
    }

    @Test
    void givenLoggingConfigInYml_whenContextUp_thenConfigurationLoaded() {

        //given
        String testMethodName = "incrementCounter";
        ActionConfiguration.Config testConfig = new ActionConfiguration.Config();
        testConfig.setClassName(TestSimpleBean1.class.getName());
        testConfig.setMethodName(testMethodName);
        testConfig.setLogInputParameters(Boolean.TRUE);
        testConfig.setLogReturnType(Boolean.TRUE);

        // then
        Map<String, Map<String, ActionConfiguration.Config>> loggingConfig =
                actionConfiguration.getSearchableAbsoluteClassConfig();
        assert loggingConfig.size() == 2;

        Map<String, ActionConfiguration.Config> stringConfigMap = loggingConfig.get("org.techpleid.springframework.insights.actions.integrationtest.testpackage1.beans.TestSimpleBean1");
        assert stringConfigMap != null;
        assertEquals(testConfig, stringConfigMap.get(testMethodName));
    }

    @Test
    void givenLoggingConfigForFQCN_whenTestSpringBeanCalled_thenLoggerActionShouldLogInputParameter() {

        // when
        testSimpleBean1.incrementCounter(new SimpleBeanInput());

        // then
        final List<String> logsList = testEncodedMessageCaptureConsoleAppender.encodedMessageList;
        assert logsList.size() == 1;
        Assertions.assertThat(logsList.get(0)).startsWith("Logging Parameters");
    }

    @Test
    void giveLoggingConfigForAllClassesInPackage_whenBeanInConfiguredPackageCalled_thenLoggerActionLogResults() {

        // when
        testSimpleBean1.incrementCounter(new SimpleBeanInput());
        //then
        verify(mockAppender).doAppend(argThat(argument -> ((LoggingEvent) argument).getFormattedMessage().startsWith("Logging Parameters")));

        //when
        testSimpleBean2.simpleBehaviour(new SimpleBeanInput());
        //then
        verify(mockAppender).doAppend(argThat(argument -> ((LoggingEvent) argument).getFormattedMessage().startsWith("Logging Parameters")));
    }
}